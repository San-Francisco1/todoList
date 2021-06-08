package controllers

import javax.inject._

import scala.concurrent.ExecutionContext

import play.api.mvc._
import services.PriorityService

@Singleton
class HomeController @Inject()(priorityService: PriorityService)(
  implicit ec: ExecutionContext
) extends InjectedController {

  def getCountPriority() = Action.async {
    priorityService.findAll.map { priorities =>
      Ok("Count priority: " + priorities.length)
    }
  }
}
