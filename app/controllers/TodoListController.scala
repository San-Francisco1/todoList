package controllers

import javax.inject.Inject

import scala.concurrent.ExecutionContext

import actions.AuthRefiner
import models.{Task, User}
import play.api.mvc.{Action, InjectedController}
import services.{TaskService, UserService}
import views.html.index

class TodoListController @Inject()(
  auth: AuthRefiner,
  userService: UserService,
  taskService: TaskService
)(
  indexView: index
)(implicit ec: ExecutionContext) extends InjectedController {

  def getIndexView = auth { request =>
    Ok(indexView(request.user))
  }

  def addUser = Action {
    userService.insert(
      User(0L, "Test", "Test", "test@mail.ru", "d8578edf8458ce06fbc5bb76a58c5ca4", "89001001010", None, deleted = None)
    )

    Ok
  }

  def addTask(): Action[Task] = auth.async(parse.json[Task]) { request =>
    val task = request.body

    taskService.insert(task).map { _ =>
      Redirect(routes.TodoListController.getIndexView)
    }
  }

  def deleteTask(id: Long) = ???
}
