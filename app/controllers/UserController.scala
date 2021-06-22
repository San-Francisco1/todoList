package controllers

import actions.AuthRefiner
import models.{Notification, User}
import play.api.libs.json.{JsError, Json}
import play.api.mvc.InjectedController
import services.{NotificationService, UserService}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

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

/*  def editNotification = auth.async(parse.json) { request =>
    for {
      notification <- notificationService.findByUserId(request.user.id)
    } yield {
      val notificationResult = request.body.validate[Notification](Notification.notificationReads(notification))
      notificationResult.fold(
        errors => {
          Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
        },
        notify => {
          notificationService.edit(notify).map( _ =>
            Redirect(routes.TodoListController.getIndexView)
          )
        }
      )
    }
  }*/
}
