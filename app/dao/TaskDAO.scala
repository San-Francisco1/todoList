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

  def findAll(userId: Long): Future[Seq[Task]] = db.run(Table.filter(_.userId === userId).result)

  def insert(task: Task): Future[Int] = db.run(Table += task)

  def remove(id: Long): Future[Int] = db.run(Table.filter(_.id === id).delete)

  def findExpired(userId: Long): Future[Seq[Task]] = db.run(
    Table.filter { task =>
      !task.isCompleted &&
        task.userId === userId &&
          task.dueDate < DateTime.now()
    }.result
  )

  def findUpcoming(userId: Long): Future[Seq[Task]] = db.run(
    Table.filter { task =>
      !task.isCompleted &&
        task.userId === userId &&
          task.dueDate >= DateTime.now().withTimeAtStartOfDay().plusDays(2)
    }.result
  )

  def findToday(userId: Long): Future[Seq[Task]] = db.run(
    Table.filter { task =>
      !task.isCompleted &&
        task.userId === userId &&
          (task.dueDate between (DateTime.now(), DateTime.now().withTimeAtStartOfDay().plusDays(1).minusSeconds(1)))
    }.result
  )

  def findTomorrow(userId: Long): Future[Seq[Task]] = {
    val now = DateTime.now().withTimeAtStartOfDay()

    db.run(
      Table.filter { task =>
        !task.isCompleted &&
          task.userId === userId &&
            (task.dueDate between (now.plusDays(1), now.plusDays(2).minusSeconds(1)))
      }.result
    )
  }

  def setIsCompleted(id: Long): Future[Int] = db.run(
    Table.filter(_.id === id).map(_.isCompleted).update(true)
  )

  def setIsNotified(id: Long): Future[Int] = db.run(
    Table.filter(_.id === id).map(_.isNotified).update(true)
  )

  def findCompleted(userId: Long): Future[Seq[Task]] = db.run(
    Table.filter { task =>
      task.userId === userId &&
        task.isCompleted
    }.result
  )

  def findByPriority(userId: Long, priorityId: Long): Future[Seq[Task]] = db.run(
    Table.filter { task =>
      task.userId === userId &&
        !task.isCompleted &&
          task.dueDate > DateTime.now &&
            task.priorityId === priorityId
    }.result
  )

  def findForNotification: Future[Seq[Task]] = db.run(
    Table.filter { task => !task.isNotified
    }.result
  )


}
