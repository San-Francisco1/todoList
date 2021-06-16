package dao

import models.Task
import org.joda.time.DateTime
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import com.github.tototoshi.slick.PostgresJodaSupport._
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TaskDAO @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit val ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  val Table = TableQuery[TaskTable]

  def findAll: Future[Seq[Task]] = db.run(Table.result)

  def insert(task: Task): Future[Int] = db.run(Table += task)

  def remove(id: Long): Future[Int] = db.run(Table.filter(_.id === id).delete)

  def findByDueDate(dueDate: DateTime): Future[Seq[Task]] = db.run(Table.filter(_.dueDate === dueDate).result)

  def findExpired: Future[Seq[Task]] = db.run(Table.filter(_.dueDate <= DateTime.now()).result)

  def findUpcoming: Future[Seq[Task]] = db.run(Table.filter(_.dueDate >= DateTime.now().withTimeAtStartOfDay().plusDays(2)).result)

  def findToday: Future[Seq[Task]] = db.run(Table.filter(task =>
    task.dueDate >= DateTime.now().withTimeAtStartOfDay() && task.dueDate <= DateTime.now().withTimeAtStartOfDay().plusDays(1)).result)

  def finsTomorrow: Future[Seq[Task]] = db.run(Table.filter(task =>
    task.dueDate >= DateTime.now().withTimeAtStartOfDay().plusDays(1) && task.dueDate <= DateTime.now().withTimeAtStartOfDay().plusDays(2)).result)
}
