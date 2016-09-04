scalacOptions := Seq("-feature", "-deprecation")

scalaVersion := "2.11.8"

scalafmtConfig in ThisBuild := Some(file(".scalafmt"))

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
