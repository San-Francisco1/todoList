package controllers

import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

import cats.data.OptionT
import cats.implicits._
import models.UserSession
import play.api.libs.json.JsValue
import play.api.mvc.{Action, Cookie, InjectedController}
import services.UserService
import views.html.auth.singIn

class AuthController @Inject()(
  userService: UserService
//  userSessionService: UserSessionService
)(singInView: singIn)(
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
      user <- OptionT(userService.findByEmail(email))
    } yield {
      val hashedPassword = calcPassword(password)

      if (hashedPassword == user.password) {
        val sessionId: String = ???
//        val session = UserSession()
        //сохранить в бд

        "localhost:9000/tasks"
        "localhost:9000/user"
        "localhost:9000/news"

//        userSessionService.insert(session).map { _ =>
//            NoContent.withCookies(Cookie("sessionId", sessionId))
//        }
      } else {
        Future.successful(BadRequest(error))
      }
    }).getOrElse(BadRequest(error))
  }

  def calcPassword(password: String): String = {
    //посчитать hash. Можно

    ???
  }
}
