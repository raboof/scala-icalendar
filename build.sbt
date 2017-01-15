scalacOptions := Seq("-feature", "-deprecation")

scalaVersion := "2.12.1"

organization := "net.bzzt"

scalafmtConfig in ThisBuild := Some(file(".scalafmt"))

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
