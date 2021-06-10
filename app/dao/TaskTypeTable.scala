package dao

import models.TaskType
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag
import org.joda.time.DateTime
import com.github.tototoshi.slick.PostgresJodaSupport._

class TaskTypeTable(tag: Tag) extends Table[TaskType](tag, "task_type") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def title = column[String]("title")

  def userId = column[Long]("user_Id")

  def parentId = column[Option[Long]]("parent_id")

  def created = column[DateTime]("created")

  def updated = column[DateTime]("updated")

  def deleted = column[Option[DateTime]]("deleted")

  override def * =
    (id, title, userId, parentId, created, updated, deleted) <> ((TaskType.apply _). tupled, TaskType.unapply)
}
