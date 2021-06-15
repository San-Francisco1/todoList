package controllers

import cats.data.OptionT
import cats.implicits._
import models.UserSession
import play.api.libs.json.JsValue
import play.api.mvc.{Action, Cookie, InjectedController}
import services.{UserService, UserSessionService}
import views.html.auth.singIn

import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AuthController @Inject()(
  userService: UserService,
  userSessionService: UserSessionService
)
  (singInView: singIn)(
  implicit ec: ExecutionContext
) extends InjectedController {
  def getSingInView = Action {
    Ok(singInView())
  }

  def singIn: Action[JsValue] = Action(parse.json).async { request =>
    lazy val error = "Некорректный email или пароль"

    (for {
      email <- OptionT.fromOption[Future]((request.body \ "email").asOpt[String])
      password <- OptionT.fromOption[Future]((request.body \ "password").asOpt[String])
      user <- OptionT(userService.findByEmailAndPass(email, calcPassword(password)))
      userSession = UserSession(sessionId = UUID.randomUUID().toString, userId = user.id, isActive = true)
      _ <- OptionT.liftF(userSessionService.insert(userSession))
    } yield {
      val cookie = Cookie("sessionId", userSession.sessionId)
      Ok.withCookies(cookie)
    }).getOrElse(BadRequest(error))
  }

  def calcPassword(password: String): String = {
    val msgDigest = MessageDigest.getInstance("MD5")
    val MD5Hash = msgDigest.digest(password.getBytes()).map(0xFF & _).map { "%02x".format(_) }.foldLeft("") {_ + _}
    MD5Hash
  }
}
