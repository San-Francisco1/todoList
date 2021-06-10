package dao

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

import models.Task
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

@Singleton
class TaskDAO @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit val ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  val Table = TableQuery[TaskTable]

  def findAll: Future[Seq[Task]] = db.run(Table.result)
}
