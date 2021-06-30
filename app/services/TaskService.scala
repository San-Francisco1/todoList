package services

import dao.TaskDAO
import models.Task
import org.joda.time.DateTime

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TaskService @Inject()(taskDAO: TaskDAO,
  notificationService: NotificationService
)(implicit ec: ExecutionContext) {
  def findAll(userId: Long): Future[Seq[Task]] = taskDAO.findAll(userId)

  def insert(task: Task): Future[Int] = taskDAO.insert(task)

  def remove(id: Long): Future[Int] = taskDAO.remove(id)

  def findExpired(userId: Long): Future[Seq[Task]] = taskDAO.findExpired(userId)

  def findUpcoming(userId: Long): Future[Seq[Task]] = taskDAO.findUpcoming(userId)

  def findToday(userId: Long): Future[Seq[Task]] = taskDAO.findToday(userId)

  def findTomorrow(userId: Long): Future[Seq[Task]] = taskDAO.findTomorrow(userId)

  def setIsCompleted(id: Long): Future[Int] = taskDAO.setIsCompleted(id)

  def setIsNotified(id: Long): Future[Int] = taskDAO.setIsNotified(id)

  def findCompleted(userId: Long): Future[Seq[Task]] = taskDAO.findCompleted(userId)

  def findByPriority(userId: Long, priorityId: Long): Future[Seq[Task]] = taskDAO.findByPriority(userId, priorityId)

  def findForNotification: Future[Seq[Task]] = taskDAO.findForNotification

  def getTasksForNotification = {
    for{
      tasks <- findForNotification
      userIds = tasks.map(_.userId).toSet
      notifications <- notificationService.findBySetUserIds(userIds)
    } yield {
      notifications.flatMap { notification =>
        notification.telegram.map {
          _ ->
            tasks
              .filter(_.userId == notification.userId)
              .filter(_.dueDate.minusMinutes(notification.beforeMinutes).isBefore(DateTime.now))
        }
      }
    }
  }
}
