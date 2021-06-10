package services

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

import dao.UserDAO
import models.User

@Singleton
class UserService @Inject()(userDAO: UserDAO) {
  def findAll: Future[Seq[User]] = userDAO.findAll
}
