package services

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

import dao.PriorityDAO
import models.Priority

@Singleton
class PriorityService @Inject()(priorityDAO: PriorityDAO) {
  def findAll: Future[Seq[Priority]] = priorityDAO.findAll
}
