package models

import org.joda.time.DateTime

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