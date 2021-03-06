package dao

import models.User
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag
import org.joda.time.DateTime
import com.github.tototoshi.slick.PostgresJodaSupport._

class UserTable(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def firstName = column[String]("first_name")

  def lastName = column[String]("last_name")

  def email = column[String]("email")

  def password = column[String]("password")

  def phone = column[String]("phone")

  def created = column[DateTime]("created")

  def updated = column[DateTime]("updated")

  def deleted = column[Option[DateTime]]("deleted")

  override def * =
    (id, firstName, lastName, email, password, phone, created, updated, deleted) <> ((User.apply _). tupled, User.unapply)
}
