package utils

import play.api.Logger

trait Loggable {
  lazy val log: Logger = Logger(getClass.getName)
}
