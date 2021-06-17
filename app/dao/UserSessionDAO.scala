package dao

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import models.UserSession
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

@Singleton
class UserSessionDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(
  implicit val ec: ExecutionContext
) extends HasDatabaseConfigProvider[JdbcProfile] {

  val Table = TableQuery[UserSessionTable]

  def insert(userSession: UserSession): Future[UserSession] = {
    db.run(Table.returning(Table.map(_.id)).into((userSession, id) => userSession.copy(id = id)) += userSession)
  }

  def findBySessionId(sessionId: String): Future[Option[UserSession]] = {
    db.run(Table.filter(userSession => userSession.sessionId === sessionId && userSession.isActive).result.headOption)
  }

  def setInactive(sessionId: String): Future[Int] = db.run(Table.filter(_.sessionId === sessionId).map(_.isActive).update((false)))
}