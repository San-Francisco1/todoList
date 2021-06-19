package actions

import models.{User, UserSession}
import play.api.mvc.{Request, WrappedRequest}

class AuthenticatedRequest[A](
  val request: Request[A],
  val user: User,
  val userSession: UserSession
) extends WrappedRequest[A](request)

