import sbt._

object Dependencies {
  object Versions {
    val playSlickV = "5.0.0"
  }

  val nscala = "com.github.nscala-time" %% "nscala-time" % "2.28.0"
  val playSlick = "com.typesafe.play" %% "play-slick" % "5.0.0"
  val playEvolutions = "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
  val postgres = "org.postgresql" % "postgresql" % "9.4-1206-jdbc42"
  val slick = "com.typesafe.slick" %% "slick" % "3.3.3"
  val slickJoda = "com.github.tototoshi" %% "slick-joda-mapper" % "2.5.0"
}
