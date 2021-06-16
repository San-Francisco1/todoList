package dao

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

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
}
