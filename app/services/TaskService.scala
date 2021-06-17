package services

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import dao.TaskDAO
import models.Task
import org.joda.time.DateTime

@Singleton
class TaskService @Inject()(taskDAO: TaskDAO) {
  def findAll: Future[Seq[Task]] = taskDAO.findAll

  def insert(task: Task): Future[Int] = taskDAO.insert(task)

  def remove(id: Long): Future[Int] = taskDAO.remove(id)

  def findByDueDate(dueDate: DateTime): Future[Seq[Task]] = taskDAO.findByDueDate(dueDate)

  def findExpired: Future[Seq[Task]] = taskDAO.findExpired

  def findUpcoming: Future[Seq[Task]] = taskDAO.findUpcoming

  def findToday: Future[Seq[Task]] = taskDAO.findToday

  def findTomorrow: Future[Seq[Task]] = taskDAO.findTomorrow
}
