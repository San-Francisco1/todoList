package actors

import javax.inject.Inject

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}

import akka.actor.Actor
import cats.implicits.toFoldableOps
import services.{TaskService, TelegramApiService}
import utils.Loggable
import views.html.telegram.telegramMsg

case object SendNotification

class NotificationActor @Inject()(
  taskService: TaskService,
  telegramApiService: TelegramApiService
)(
  telegramMsgView: telegramMsg
)(implicit ec: ExecutionContext) extends Actor with Loggable {
  override def preStart(): Unit = {
    log.info("NotificationActor started")
    super.preStart()
  }

  override def postStop(): Unit = {
    log.info("NotificationActor stopped")
    super.postStop()
  }

  context.system.scheduler.scheduleWithFixedDelay(
    10.seconds,
    30.seconds,
    self,
    SendNotification
  )

  private def sendNotifications(): Future[Unit] = {
    taskService.getTasksForNotification.flatMap {
      _.traverse_ { case (telegramChannelId, tasks) =>
        tasks.traverse_ { task =>
          val msg = telegramMsgView(task).body

          for {
            _ <- telegramApiService.sendMsg(telegramChannelId, msg)
            _ <- taskService.setIsNotified(task.id)
          } yield ()
        }
      }
    }
  }

  override def receive: Receive = {
    case SendNotification =>
      log.info("Sending notifications")
      sendNotifications()
  }
}
