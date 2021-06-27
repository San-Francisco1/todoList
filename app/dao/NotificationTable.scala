package dao

import models.Notification
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag
import org.joda.time.DateTime
import com.github.tototoshi.slick.PostgresJodaSupport._

class NotificationTable(tag: Tag) extends Table[Notification](tag, "notification") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def userId = column[Long]("user_id")

  def telegram = column[Option[String]]("telegram")

  def beforeMinutes = column[Int]("before_minutes")

  def created = column[DateTime]("created")

  def updated = column[DateTime]("updated")

  override def * = (
    id, userId, telegram, beforeMinutes, created, updated) <> ((Notification.apply _). tupled, Notification.unapply)
}
