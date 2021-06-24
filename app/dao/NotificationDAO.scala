package dao

import models.Notification
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class NotificationDAO @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit val ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  val Table = TableQuery[NotificationTable]

  def findByUserId(userId: Long): Future[Option[Notification]] = db.run(Table.filter(_.userId === userId).result.headOption)

  def edit(editNotification: Notification): Future[Int] = db.run(Table.insertOrUpdate(editNotification))
}