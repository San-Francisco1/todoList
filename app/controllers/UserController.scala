package controllers

import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

import actions.AuthRefiner
import models.{Notification, User}
import play.api.libs.json.{JsError, Json}
import play.api.mvc.InjectedController
import services.{NotificationService, UserService}

class UserController @Inject()(
  auth: AuthRefiner,
  notificationService: NotificationService,
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

  def editNotification = auth.async(parse.json) { request =>
    notificationService.findByUserId(request.user.id).flatMap { notification =>
      request.body.validate[Notification](Notification.notificationReads(notification))
        .fold(
          errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
          notify => notificationService.edit(notify).map(_ => Ok)
        )
    }
  }
}
