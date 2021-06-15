package actions

import models.User
import play.api.mvc.{Request, WrappedRequest}

class AuthenticatedRequest[A](
  val request: Request[A],
  val user: User
) extends WrappedRequest[A](request)

