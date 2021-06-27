package services

import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

import models.telegram.TelegramMsgAnswer
import play.api.Configuration
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import utils.Loggable

class TelegramApiService @Inject()(
  ws: WSClient,
  configuration: Configuration
)(implicit ec: ExecutionContext) extends Loggable {

  private val HOST = configuration.get[String]("telegram.uri")
  private val TOKEN = configuration.get[String]("telegram.token")
  private val url = s"$HOST/bot$TOKEN"

  private def sendPostRequest(command: String, body: Map[String, String], headers: (String, String)): Future[Unit] = {
    val constUrl = s"$url/$command"
    ws.url(constUrl)
      .addHttpHeaders(headers)
      .post(Json.toJson(body))
      .map { response =>
        response.json.validate(TelegramMsgAnswer.reads)
          .map(_ => log.info(s"Telegram bot send msg [url = $constUrl ] successfully SEND"))
          .getOrElse(log.error(s"Error: ${response.json}"))
      }
      .recover { err =>
        log.error(s"Push to [url = $constUrl] NOT SEND, error: ${err.getMessage}")
      }
  }

  def sendMsg(chatId: String, textMsg: String, url: String = "https://ryazanov-todolist.herokuapp.com/"): Future[Unit] = {
    val command = "sendMessage"
    val text = s"$textMsg\n$url"

    val headers = ("Content-Type", "application/json")
    sendPostRequest(
      command,
      Map(
        "chat_id" -> chatId,
        "text" -> text,
        "parse_mode" -> "html"
      ),
      headers
    )
  }
}
