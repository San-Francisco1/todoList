package services

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

import dao.TaskDAO
import models.Task

@Singleton
class TaskService @Inject()(taskDAO: TaskDAO) {
  def findAll: Future[Seq[Task]] = taskDAO.findAll
}
