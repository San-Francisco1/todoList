package models

import org.joda.time.DateTime

case class TaskType(
  id: Long,
  title: String,
  userId: Long,
  parentId: Option[Long],
  created: DateTime = DateTime.now(),
  updated: DateTime = DateTime.now(),
  deleted: Option[DateTime]
)
