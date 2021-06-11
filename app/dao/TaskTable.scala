package dao

import models.Task
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag
import org.joda.time.DateTime
import com.github.tototoshi.slick.PostgresJodaSupport._

class TaskTable(tag: Tag) extends Table[Task](tag, "task") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def title = column[String]("title")

  def description = column[Option[String]]("description")

  def priorityId = column[Long]("priority_id")

  def taskTypeId = column[Option[Long]]("task_type_Id")

  def dueDate = column[DateTime]("due_date")

  def created = column[DateTime]("created")

  def updated = column[DateTime]("updated")

  def deleted = column[Option[DateTime]]("deleted")

  override def * =
    (id, title, description, priorityId, taskTypeId, dueDate, created, updated, deleted) <> ((Task.apply _). tupled, Task.unapply)
}