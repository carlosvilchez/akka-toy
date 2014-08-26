name := "akkatoy"

version := "0.1-SNAPSHOT"

organization := "com.akkatoy"

scalaVersion := "2.10.4"

resolvers ++=
Seq("repo" at "http://repo.typesafe.com/typesafe/releases/",
"Spray Repository"    at "http://repo.spray.io",
"Spray Nightlies"     at "http://nightlies.spray.io/")

libraryDependencies ++= {
  val akkaVersion       = "2.3.5"
  val sprayVersion      = "1.1-20130123"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-kernel" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-contrib" % akkaVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.0.10",
    "com.typesafe.akka" %%  "akka-testkit" % akkaVersion % "test",
    "org.scalatest"     %% "scalatest" % "1.9.1" % "test"
  )
}

