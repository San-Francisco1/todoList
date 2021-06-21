package services

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

import dao.PriorityDAO
import models.Priority

@Singleton
class PriorityService @Inject()(priorityDAO: PriorityDAO) {
  def findById(id: Long): Future[Option[Priority]] = priorityDAO.findById(id)

  def findAll: Future[Seq[Priority]] = priorityDAO.findAll
}
