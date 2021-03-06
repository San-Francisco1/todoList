package dao

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import com.github.tototoshi.slick.PostgresJodaSupport._

@Singleton
class UserDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(
  implicit val ec: ExecutionContext
) extends HasDatabaseConfigProvider[JdbcProfile] {

  val Table = TableQuery[UserTable]

  def findAll: Future[Seq[User]] = db.run(Table.result)

  def findById(id: Long): Future[Option[User]] = {
    val query = Table.filter(_.id === id)
    db.run(query.result.headOption)
  }

  def findByEmailAndPass(email: String, password: String): Future[Option[User]] = {
    db.run(Table.filter(user => user.email === email && user.password === password).result.headOption)
  }

  def insert(user: User): Future[Int] = db.run(Table += user)

  def edit(updatedUser: User): Future[Int] = {
    db.run(Table.filter(_.id === updatedUser.id)
      .map(user => (user.firstName, user.lastName, user.phone, user.updated))
      .update((updatedUser.firstName, updatedUser.lastName, updatedUser.phone, updatedUser.updated)))
  }
}
