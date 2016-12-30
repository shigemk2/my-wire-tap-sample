name := "my-wire-tap-sample"

organization := "com.example"

version := "1.0"

scalaVersion := "2.12.1"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.16",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
