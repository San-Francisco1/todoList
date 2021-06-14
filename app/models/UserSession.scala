package models

import org.joda.time.DateTime

case class UserSession(
  id: Long = 0,
  sessionId: String,
  userId: Long,
  isActive: Boolean,
  created: DateTime
)
