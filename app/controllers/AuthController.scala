package controllers

import cats.data.OptionT
import cats.implicits._
import models.{Auth, Task, UserSession}
import org.joda.time.DateTime
import play.api.libs.json.JsValue
import play.api.mvc.{Action, Cookie, InjectedController}
import services.{TaskService, UserService, UserSessionService}
import views.html.auth.signIn

import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AuthController @Inject()(
  userService: UserService,
  taskService: TaskService,
  userSessionService: UserSessionService
)(
  signInView: signIn
)(
  implicit ec: ExecutionContext
) extends InjectedController {
  def getSignInView = Action {
    Ok(signInView())
  }

  def signIn: Action[JsValue] = Action(parse.json).async { request =>
    lazy val error = "Некорректный email или пароль"

    (for {
      email <- OptionT.fromOption[Future]((request.body \ "email").asOpt[String])
      password <- OptionT.fromOption[Future]((request.body \ "password").asOpt[String])
      user <- OptionT(userService.findByEmailAndPass(email, calcPassword(password)))
      userSession = UserSession(sessionId = UUID.randomUUID().toString, userId = user.id, isActive = true)
      t4 = Task(0, title = "qwerty3", description = null, priorityId = 3, taskTypeId = null, dueDate = DateTime.now(), deleted = null)
      _ <- OptionT.liftF(userSessionService.insert(userSession))
      _ <- OptionT.liftF(taskService.insert(t4))
    } yield {
      val cookie = Cookie(Auth.COOKIE_NAME, userSession.sessionId)
//      val t1 = Task(1, title = "qwerty1", description = null, priorityId = 1, taskTypeId = null, dueDate = DateTime.now().plusHours(1), deleted = null)
//      val t2 = Task(2, title = "qwerty2", description = null, priorityId = 2, taskTypeId = null, dueDate = DateTime.now().plusHours(10), deleted = null)
//      val t3 = Task(3, title = "qwerty3", description = null, priorityId = 3, taskTypeId = null, dueDate = DateTime.now(), deleted = null)
//      val t4 = Task(4, title = "qwerty4", description = null, priorityId = 4, taskTypeId = null, dueDate = DateTime.now().plusHours(48), deleted = null)
//      taskService.insert(t1)
//      taskService.insert(t2)
//      taskService.insert(t3)
//      taskService.insert(t4)
      println("--------------HELLO--------------")
      Ok.withCookies(cookie)
    }).getOrElse(BadRequest(error))
  }

  private def calcPassword(password: String): String = {
    val msgDigest = MessageDigest.getInstance("MD5")
    val MD5Hash = msgDigest.digest(password.getBytes()).map(0xFF & _).map { "%02x".format(_) }.foldLeft("") {_ + _}
    MD5Hash
  }
}
