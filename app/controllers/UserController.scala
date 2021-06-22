package controllers

import actions.AuthRefiner
import models.User
import play.api.libs.json.{JsError, Json}
import play.api.mvc.InjectedController
import services.UserService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserController @Inject()(
  auth: AuthRefiner,
  userService: UserService
)(implicit ec: ExecutionContext) extends InjectedController {

  def editUser = auth.async(parse.json) { request =>
    val editUserResult = request.body.validate[User](User.userReads(request.user))
    editUserResult.fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      user => {
        userService.edit(user).map( _ =>
          Redirect(routes.TodoListController.getIndexView)
        )
      }
    )
  }
}
