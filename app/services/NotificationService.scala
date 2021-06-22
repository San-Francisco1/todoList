package services

import dao.NotificationDAO
import models.Notification
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class NotificationService @Inject() (notificationDAO: NotificationDAO) {

  def findAll(userId: Long): Future[Seq[Notification]] = notificationDAO.findAll(userId)

}
