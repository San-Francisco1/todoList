package models

import org.joda.time.DateTime
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class User(
  id: Long,
  firstName: String,
  lastName: String,
  email: String,
  password: String,
  phone: String,
  telegram: Option[String],
  created: DateTime = DateTime.now(),
  updated: DateTime = DateTime.now(),
  deleted: Option[DateTime]
)

object User extends JodaReads {
  implicit def userReads(user: User): Reads[User] = {
    (
      Reads.pure(user.id) and
        (__ \ "firstname").read[String] and
        (__ \ "lastname").read[String] and
        Reads.pure(user.email) and
        Reads.pure(user.password) and
        (__ \ "phone").read[String] and
        (__ \ "telegram").readNullable[String] and
        Reads.pure(user.created) and
        Reads.pure(DateTime.now) and
        Reads.pure(None)
      )(User.apply _)
  }
}
