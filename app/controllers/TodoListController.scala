package controllers

import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

import actions.AuthRefiner
import models.{Task, User}
import play.api.mvc.InjectedController
import services.{PriorityService, TaskService, UserService}
import views.html.todolist.today

class TodoListController @Inject()(
  auth: AuthRefiner,
  userService: UserService,
  taskService: TaskService,
  priorityService: PriorityService
)(
  today: today
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
        .sortBy(-_._1.dueDate.getMillis)

      Ok(today(request.user, result))
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

  def deleteTask(id: Long) = ???
}
