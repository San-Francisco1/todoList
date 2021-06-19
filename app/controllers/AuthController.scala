package controllers

import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

import cats.data.OptionT
import cats.implicits._
import actions.AuthRefiner
import models.{Auth, UserSession}
import play.api.libs.json.JsValue
import play.api.mvc.{Action, AnyContent, Cookie, InjectedController}
import services.{UserService, UserSessionService}
import views.html.auth.signIn

class AuthController @Inject()(
  userService: UserService,
  auth: AuthRefiner,
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
      _ <- OptionT.liftF(userSessionService.insert(userSession))
    } yield {
      val cookie = Cookie(Auth.COOKIE_NAME, userSession.sessionId)
      Ok.withCookies(cookie)
    }).getOrElse(BadRequest(error))
  }

  def logout: Action[AnyContent] = auth.async { request =>
    OptionT.liftF(userSessionService.setInactive(request.userSession.sessionId)).map { _ =>
      Redirect(routes.AuthController.getSignInView)
    }.getOrElse(BadRequest)
  }

  private def calcPassword(password: String): String = {
    val msgDigest = MessageDigest.getInstance("MD5")
    val MD5Hash = msgDigest.digest(password.getBytes()).map(0xFF & _).map { "%02x".format(_) }.foldLeft("") {_ + _}
    MD5Hash
  }
}
