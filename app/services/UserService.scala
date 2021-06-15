package services

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

import dao.UserDAO
import models.User

@Singleton
class UserService @Inject()(userDAO: UserDAO) {
  def findAll: Future[Seq[User]] = userDAO.findAll

  def findById(Id: Long): Future[Option[User]] = userDAO.findById(Id)

  def findByEmailAndPass(email: String, password: String): Future[Option[User]] = userDAO.findByEmailAndPass(email, password)
}
