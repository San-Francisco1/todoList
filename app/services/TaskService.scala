package services

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import dao.TaskDAO
import models.Task
import org.joda.time.DateTime

@Singleton
class TaskService @Inject()(taskDAO: TaskDAO) {
  def findAll(userId: Long): Future[Seq[Task]] = taskDAO.findAll(userId)

  def insert(task: Task): Future[Int] = taskDAO.insert(task)

  def remove(id: Long): Future[Int] = taskDAO.remove(id)

  def findByDueDate(userId: Long, dueDate: DateTime): Future[Seq[Task]] = taskDAO.findByDueDate(userId, dueDate)

  def findExpired(userId: Long): Future[Seq[Task]] = taskDAO.findExpired(userId)

  def findUpcoming(userId: Long): Future[Seq[Task]] = taskDAO.findUpcoming(userId)

  def findToday(userId: Long): Future[Seq[Task]] = taskDAO.findToday(userId)

  def findTomorrow(userId: Long): Future[Seq[Task]] = taskDAO.findTomorrow(userId)

  def setIsCompleted(id: Long): Future[Int] = taskDAO.setIsCompleted(id)

  def findCompleted(userId: Long): Future[Seq[Task]] = taskDAO.findCompleted(userId)

  def findByPriority(userId: Long, priorityId: Long): Future[Seq[Task]] = taskDAO.findByPriority(userId, priorityId)
}
