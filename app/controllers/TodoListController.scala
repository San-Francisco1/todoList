package controllers

import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

import cats.data.OptionT
import actions.AuthRefiner
import models.{Task, User}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, InjectedController}
import services.{PriorityService, TaskService, UserService}
import views.html.todolist._

class TodoListController @Inject()(
  auth: AuthRefiner,
  userService: UserService,
  taskService: TaskService,
  priorityService: PriorityService
)(
  today: today,
  tomorrow: tomorrow,
  upcoming: upcoming,
  expired: expired,
  completed: completed,
  priority: priority
)(implicit ec: ExecutionContext) extends InjectedController {

  def getIndexView = auth.async { request =>
    for {
      tasks <- taskService.findToday(request.user.id)
      priorities <- priorityService.findAll
    } yield {
      val result = (for {
        task <- tasks
        priority <- priorities.find(_.id == task.priorityId)
      } yield task -> priority)
        .sortBy(_._1.dueDate.getMillis)

      Ok(today(request.user, request.notification, result))
    }
  }

  def getTomorrowView = auth.async { request =>
    for {
      tasks <- taskService.findTomorrow(request.user.id)
      priorities <- priorityService.findAll
    } yield {
      val result = (for {
        task <- tasks
        priority <- priorities.find(_.id == task.priorityId)
      } yield task -> priority)
        .sortBy(_._1.dueDate.getMillis)

      Ok(tomorrow(request.user, request.notification, result))
    }
  }

  def getExpiredView = auth.async { request =>
    for {
      tasks <- taskService.findExpired(request.user.id)
      priorities <- priorityService.findAll
    } yield {
      val result = (for {
        task <- tasks
        priority <- priorities.find(_.id == task.priorityId)
      } yield task -> priority)
        .sortBy(-_._1.dueDate.getMillis)

      Ok(expired(request.user, request.notification, result))
    }
  }

  def getCompletedView = auth.async { request =>
    for {
      tasks <- taskService.findCompleted(request.user.id)
      priorities <- priorityService.findAll
    } yield {
      val result = (for {
        task <- tasks
        priority <- priorities.find(_.id == task.priorityId)
      } yield task -> priority)
        .sortBy(-_._1.dueDate.getMillis)

      Ok(completed(request.user, request.notification, result))
    }
  }

  def getUpcomingView = auth.async { request =>
    for {
      tasks <- taskService.findUpcoming(request.user.id)
      priorities <- priorityService.findAll
    } yield {
      val result = (for {
        task <- tasks
        priority <- priorities.find(_.id == task.priorityId)
      } yield task -> priority)
        .sortBy(-_._1.dueDate.getMillis)

      Ok(upcoming(request.user, request.notification, result))
    }
  }

  def getByPriorityIdView(id: Long) = auth.async { request =>
    (for {
      dbPriority <- OptionT(priorityService.findById(id))
      tasks <- OptionT.liftF(taskService.findByPriority(request.user.id, dbPriority.id))
    } yield {
      val result = tasks.sortBy(-_.dueDate.getMillis)

      Ok(priority(request.user, request.notification, dbPriority, result))
    }).getOrElse(BadRequest)
  }

  def addUser = Action {
    userService.insert(
      User(0L, "Test", "Test", "test@mail.ru", "d8578edf8458ce06fbc5bb76a58c5ca4", "89001001010", deleted = None)
    )

    Ok
  }

  def addTask() = auth.async(parse.json) { request =>
    request
      .body
      .validate[Task](Task.createTaskReads(request.user.id))
      .map { task =>
        taskService.insert(task).map(_ => Redirect(routes.TodoListController.getIndexView))
      }
      .recoverTotal { errors =>
        Future.successful(BadRequest(errors.toString))
     }
  }

  def deleteTask(id: Long): Action[AnyContent] = auth.async {
      taskService.remove(id).map { _ =>
        Ok
      }
  }

  def completeTask(id: Long) = auth.async {
    taskService.setIsCompleted(id).map { _ =>
      Ok
    }
  }

  def getCountTask = auth.async { request =>
    for {
      taskToday <- taskService.findToday(request.user.id)
      taskTomorrow <- taskService.findTomorrow(request.user.id)
      taskUpcoming <- taskService.findUpcoming(request.user.id)
      taskCompleted <- taskService.findCompleted(request.user.id)
      taskExpired <- taskService.findExpired(request.user.id)
    } yield {
      Ok(
        Json.obj(
          "today"-> taskToday.length,
          "tomorrow" -> taskTomorrow.length,
          "upcoming" -> taskUpcoming.length,
          "completed" -> taskCompleted.length,
          "expired" -> taskExpired.length
        )
      )
    }
  }


}
