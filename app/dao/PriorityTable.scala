package dao

import models.Priority
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class PriorityTable(tag: Tag) extends Table[Priority](tag, "priority") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def title = column[String]("title")

  def color = column[String]("color")

  override def * =
    (id, title, color) <> ((Priority.apply _).tupled, Priority.unapply)
}
