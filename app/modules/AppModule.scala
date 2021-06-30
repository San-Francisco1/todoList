package modules

import actors.NotificationActor
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

class AppModule extends AbstractModule with AkkaGuiceSupport {
  override def configure(): Unit = {
    bindActor[NotificationActor](name = "notification-actor")
  }
}
