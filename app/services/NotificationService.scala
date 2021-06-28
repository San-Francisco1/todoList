package services

import dao.NotificationDAO
import models.Notification

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import cats.data._
import org.joda.time.DateTime


@Singleton
class NotificationService @Inject()(notificationDAO: NotificationDAO)(implicit ec: ExecutionContext) {

  def findByUserId(userId: Long): Future[Notification] =
    OptionT(notificationDAO.findByUserId(userId)).getOrElse(Notification(0, userId, None, 15, DateTime.now(), DateTime.now()))

  def findBySetUserIds(userIds: Set[Long]) = notificationDAO.findBySetUserIds(userIds)

  def edit(notification: Notification): Future[Int] = notificationDAO.edit(notification)
}
