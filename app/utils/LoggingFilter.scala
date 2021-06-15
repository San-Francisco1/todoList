package utils

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

import akka.stream.Materializer
import org.slf4j.MarkerFactory
import play.api.DefaultMarkerContext
import play.api.http.MimeTypes
import play.api.mvc._

@Singleton
class LoggingFilter @Inject() (implicit
  ec: ExecutionContext,
  mat: Materializer
) extends EssentialFilter
  with Loggable {

  implicit private val markerContext: DefaultMarkerContext = new DefaultMarkerContext(MarkerFactory.getMarker("REQUEST"))

  override def apply(next: EssentialAction) = EssentialAction { request =>
    val startTime = System.currentTimeMillis

    log.info(s"[START] ${request.method} ${request.uri}")

    next(request).map { result =>
      val endTime = System.currentTimeMillis
      val requestTime = endTime - startTime

      val message = s"[END] ${request.method} ${request.uri} took ${requestTime}ms and returned ${result.header.status}"

      resultToResultWithLogging(result, message)

      result
    }
  }

  private def resultToResultWithLogging(
    result: Result, logMessagePart: String
  ): Future[Result] = parseResponseBody(result).map { _ =>
    log.info(s"$logMessagePart")

    result
  }

  private def parseResponseBody(result: Result): Future[String] = result.body.contentType match {
    case Some(cType) if isLoggableResponse(cType.trim) =>
      result match {
        case Result(_, content, _, _, _) =>
          content.consumeData.map(_.decodeString("UTF-8"))
        case _ =>
          Future.successful("no trivial result")
      }
    case Some(cType) =>
      Future.successful(s"Content type $cType")
    case None =>
      Future.successful("No content type")
  }

  private def isLoggableResponse(contentType: String) =
    contentType.contains(MimeTypes.JSON) || contentType.contains(MimeTypes.TEXT) || contentType.contains(MimeTypes.XML)

}
