lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "TodoList",
    organization := "com.example",
    version := "0.0.1",
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
      guice,
      Dependencies.nscala,
      Dependencies.playSlick,
      Dependencies.playEvolutions,
      Dependencies.slick,
      Dependencies.slickJoda,
      Dependencies.postgres
    ),
    scalacOptions ++= Seq("-feature", "-deprecation", "-Xfatal-warnings")
  )
