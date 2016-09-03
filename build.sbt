scalacOptions := Seq("-feature", "-deprecation")

scalafmtConfig in ThisBuild := Some(file(".scalafmt"))

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
