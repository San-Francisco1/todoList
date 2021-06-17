package services

import dao.UserSessionDAO
import models.UserSession

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class UserSessionService @Inject()(userSessionDAO: UserSessionDAO) {

  def insert(userSession: UserSession): Future[UserSession] = userSessionDAO.insert(userSession)

  def findBySessionId(sessionId: String): Future[Option[UserSession]] = userSessionDAO.findBySessionId(sessionId)

  def setInactive(sessionId: String): Future[Int] = userSessionDAO.setInactive(sessionId)
}
