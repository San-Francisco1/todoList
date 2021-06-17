package controllers

import javax.inject.Inject

import actions.AuthRefiner
import models.User
import play.api.mvc.InjectedController
import services.UserService
import views.html.index

class TodoListController @Inject()(
  auth: AuthRefiner,
  userService: UserService
)(
  indexView: index
) extends InjectedController {
  def getIndexView = auth { request =>
    Ok(indexView(request.user))
  }

  def addUser = Action {
    userService.insert(User(0L, "Test", "Test", "test@mail.ru", "d8578edf8458ce06fbc5bb76a58c5ca4", "89001001010", None, deleted = None))

    Ok
  }
}
