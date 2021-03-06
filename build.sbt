lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "TodoList",
    organization := "com.example",
    version := "0.0.1",
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
      guice,
      ws,
      Dependencies.cats,
      Dependencies.nscala,
      Dependencies.playSlick,
      Dependencies.playEvolutions,
      Dependencies.playJsonJoda,
      Dependencies.slick,
      Dependencies.slickJoda,
      Dependencies.postgres
    ),
    scalacOptions ~= (_.filterNot(Set(
      "-Xlint:adapted-args",
      "-Wvalue-discard",
      "-Xlint:infer-any"
    )) ++ Seq(
      "-Wconf:src=target/.*:silent",
      "-Ywarn-macros:after"
    )),
  )
