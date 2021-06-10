package dao

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

import models.TaskType
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

@Singleton
class TaskTypeDAO @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit val ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  val Table = TableQuery[TaskTypeTable]

  def findAll: Future[Seq[TaskType]] = db.run(Table.result)
}
