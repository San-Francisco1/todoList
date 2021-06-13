package controllers

import javax.inject.Inject

import play.api.mvc.{Cookie, InjectedController}
import views.html.auth._

class AuthController @Inject()(singInView: singIn) extends InjectedController {
  def getSingInView = Action {
    Ok(singInView())
  }

  def singIn = Action(parse.json) { request =>
    (for {
      email <- (request.body \ "email").asOpt[String]
      password <- (request.body \ "password").asOpt[String]
    } yield {

      NoContent.withCookies(Cookie("sessionId", ""))
    }).getOrElse(BadRequest)
  }
}
