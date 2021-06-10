package services

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

import dao.TaskTypeDAO
import models.TaskType

@Singleton
class TaskTypeService @Inject()(taskDAO: TaskTypeDAO) {
  def findAll: Future[Seq[TaskType]] = taskDAO.findAll
}
