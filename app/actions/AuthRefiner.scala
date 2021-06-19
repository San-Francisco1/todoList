package actions

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

import cats.data.OptionT
import cats.implicits._
import controllers.routes
import models.Auth
import play.api.mvc._
import services.{UserService, UserSessionService}

@Singleton
class AuthRefiner @Inject()(
  val parser: BodyParsers.Default,
  userSessionService: UserSessionService,
  userService: UserService
)(
  implicit val executionContext: ExecutionContext
) extends ActionBuilder[AuthenticatedRequest, AnyContent]
  with ActionRefiner[Request, AuthenticatedRequest] {

  override protected def refine[A](request: Request[A]): Future[Either[Result, AuthenticatedRequest[A]]] = {
    (for {
      cookie <- OptionT.fromOption[Future](request.cookies.find(_.name == Auth.COOKIE_NAME))
      userSession <- OptionT(userSessionService.findBySessionId(cookie.value))
      user <- OptionT(userService.findById(userSession.userId))
    } yield new AuthenticatedRequest(request, user, userSession))
      .toRight(Results.Redirect(routes.AuthController.getSignInView))
      .value
  }
}
