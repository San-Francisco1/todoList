package models

import org.joda.time.DateTime
import play.api.libs.json.{JodaReads, Reads, __}
import play.api.libs.functional.syntax._

import scala.concurrent.Future

case class Notification(
  id: Long,
  userId: Long,
  telegram: Option[String],
  beforeMinutes: Int,
  created: DateTime,
  updated: DateTime
)

object Notification extends JodaReads {
  def notificationReads(notification: Notification): Reads[Future[Notification]] = {
    (
      Reads.pure(notification.id) and
        Reads.pure(notification.userId) and
        (__ \ "telegram").readNullable[String] and
        (__ \ "before_minutes").read[Int] and
        Reads.pure(notification.created) and
        Reads.pure(DateTime.now)
      )(Notification.apply _)
  }
}