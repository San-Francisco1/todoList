lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "TodoList",
    organization := "com.example",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
      guice,
      "com.github.nscala-time" %% "nscala-time" % "2.28.0"
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
