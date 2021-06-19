package models

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, _}

case class Task(
  id: Long,
  title: String,
  description: Option[String],
  userId: Long,
  priorityId: Long,
  taskTypeId: Option[Long],
  isCompleted: Boolean,
  dueDate: DateTime,
  created: DateTime = DateTime.now(),
  updated: DateTime = DateTime.now(),
  deleted: Option[DateTime]
)

object Task extends JodaReads {
  def creatTaskReads(userId: Long): Reads[Task] = {
    (
      Reads.pure(0L) and
        (__ \ "title").read[String] and
        (__ \ "description").readNullable[String] and
        Reads.pure(userId) and
        (__ \ "priority_id").read[Long] and
        Reads.pure(None) and
        Reads.pure(false) and
        (__ \ "due_date").read[DateTime] and
        Reads.pure(DateTime.now) and
        Reads.pure(DateTime.now) and
        Reads.pure(None)
    )(Task.apply _)
  }
}
