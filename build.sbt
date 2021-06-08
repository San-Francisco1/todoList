lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "TodoList",
    organization := "com.example",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
      guice,
      "com.github.nscala-time" %% "nscala-time" % "2.28.0",
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
      "com.typesafe.slick" %% "slick" % "3.3.3",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "org.postgresql" % "postgresql" % "9.4-1206-jdbc42" //org.postgresql.ds.PGSimpleDataSource dependency
    ),
    scalacOptions ++= Seq("-feature", "-deprecation", "-Xfatal-warnings")
  )
