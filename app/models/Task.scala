package models

import org.joda.time.DateTime
import play.api.libs.json.Reads
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Task(
  id: Long,
  title: String,
  description: Option[String],
  priorityId: Long,
  taskTypeId: Option[Long],
  dueDate: DateTime,
  created: DateTime = DateTime.now(),
  updated: DateTime = DateTime.now(),
  deleted: Option[DateTime]
)

object Task extends JodaReads {
  implicit val taskReads: Reads[Task] = {
    (
      Reads.pure(0L) and
        (__ \ "title").read[String] and
        (__ \ "description").readNullable[String] and
        (__ \ "priority_id").read[Long] and
        (__ \ "task_type_id").readNullable[Long] and
        (__ \ "due_date").read[DateTime] and
        Reads.pure(DateTime.now) and
        Reads.pure(DateTime.now) and
        Reads.pure(None)
      )(Task.apply _)
  }
}
