package controllers

import actions.AuthRefiner
import models.{Task, User}
import play.api.mvc.{Action, AnyContent, InjectedController}
import services.{PriorityService, TaskService, UserService}
import views.html.todolist._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TodoListController @Inject()(
  auth: AuthRefiner,
  userService: UserService,
  taskService: TaskService,
  priorityService: PriorityService
)(
  today: today,
  tomorrow: tomorrow,
  upcoming: upcoming,
  expired: expired
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

      Ok(today(request.user, result))
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

      Ok(tomorrow(request.user, result))
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

      Ok(expired(request.user, result))
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

      Ok(upcoming(request.user, result))
    }
  }

  def addUser = Action {
    userService.insert(
      User(0L, "Test", "Test", "test@mail.ru", "d8578edf8458ce06fbc5bb76a58c5ca4", "89001001010", None, deleted = None)
    )

    Ok
  }

  def addTask() = auth.async(parse.json) { request =>
    request
      .body
      .validate[Task](Task.creatTaskReads(request.user.id))
      .map { task =>
        taskService.insert(task).map(_ => Redirect(routes.TodoListController.getIndexView))
      }
      .recoverTotal { errors =>
        Future.successful(BadRequest(errors.toString))
     }
  }

  def deleteTask(id: Long): Action[AnyContent] = auth.async {
      taskService.remove(id).map { _ =>
        Redirect(routes.TodoListController.getIndexView)
      }
  }
}
