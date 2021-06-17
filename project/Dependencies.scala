import sbt._

object Dependencies {
  object Versions {
    val playSlickV = "5.0.0"
    val catsV      = "2.3.1"
  }

  import Versions._

  val cats           = "org.typelevel"          %% "cats-core"             % catsV
  val nscala         = "com.github.nscala-time" %% "nscala-time"           % "2.28.0"
  val playSlick      = "com.typesafe.play"      %% "play-slick"            % playSlickV
  val playEvolutions = "com.typesafe.play"      %% "play-slick-evolutions" % playSlickV
  val playJsonJoda   = "com.typesafe.play"      %% "play-json-joda"        % "2.9.2"
  val postgres       = "org.postgresql"          % "postgresql"            % "9.4-1206-jdbc42"
  val slick          = "com.typesafe.slick"     %% "slick"                 % "3.3.3"
  val slickJoda      = "com.github.tototoshi"   %% "slick-joda-mapper"     % "2.5.0"
}
