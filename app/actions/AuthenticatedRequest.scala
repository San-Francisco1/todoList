package actions

import models.{Notification, User, UserSession}
import play.api.mvc.{Request, WrappedRequest}

class AuthenticatedRequest[A](
  val request: Request[A],
  val user: User,
  val userSession: UserSession,
  val notification: Notification
) extends WrappedRequest[A](request)

