package models

import org.joda.time.DateTime

case class Notification(
  id: Long,
  userId: Long,
  telegram: Option[String],
  beforeMinutes: Int,
  created: DateTime,
  updated: DateTime
)
