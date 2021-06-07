package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()() extends InjectedController {

  def hello(name:String) = Action { request =>
    Ok("Hello " + name)
  }
}
