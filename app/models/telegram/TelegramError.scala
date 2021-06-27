package models.telegram

import play.api.libs.json._

case class TelegramError(
  ok: Boolean,
  errorCode: Int,
  description: String
)

trait TelegramErrorJson {
  implicit val config: JsonConfiguration = JsonConfiguration(JsonNaming.SnakeCase)
  implicit val reads: Reads[TelegramError] = Json.reads[TelegramError]
}

object TelegramError extends TelegramErrorJson
