package dao

import models.UserSession
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag
import org.joda.time.DateTime
import com.github.tototoshi.slick.PostgresJodaSupport._

class UserSessionTable(tag: Tag) extends Table[UserSession](tag, "user_session") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def sessionId = column[String]("session_id")

  def userId = column[Long]("user_id")

  def isActive = column[Boolean]("is_active")

  def created = column[DateTime]("created")

  override def * =
    (id, sessionId, userId, isActive, created) <> ((UserSession.apply _). tupled, UserSession.unapply)
}
