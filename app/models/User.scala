package models

import org.joda.time.DateTime

case class User(
  id: Long,
  firstName: String,
  lastName: String,
  email: String,
  password: String,
  phone: String,
  telegram: Option[String],
  created: DateTime = DateTime.now(),
  updated: DateTime,
  deleted: Option[DateTime]
)
