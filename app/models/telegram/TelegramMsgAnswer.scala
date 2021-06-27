package models.telegram

import play.api.libs.json._

case class TelegramMsgAnswer(
  ok: Boolean,
  result: MsgAnswer
)

trait TelegramMsgAnswerJson {
  implicit val config: JsonConfiguration = JsonConfiguration(JsonNaming.SnakeCase)
  implicit val reads: Reads[TelegramMsgAnswer] = Json.reads[TelegramMsgAnswer]
}

object TelegramMsgAnswer extends TelegramMsgAnswerJson

case class MsgAnswer(
  messageId: Int,
  date: Int,
  text: String
)

trait MsgAnswerJson {
  implicit val config: JsonConfiguration = JsonConfiguration(JsonNaming.SnakeCase)
  implicit val reads: Reads[MsgAnswer] = Json.reads[MsgAnswer]
}

object MsgAnswer extends MsgAnswerJson
