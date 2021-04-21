scalacOptions := Seq("-feature", "-deprecation")

scalaVersion := "2.13.5"

organization := "net.bzzt"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.8" % "test"

credentials += Credentials("Sonatype Nexus Repository Manager",
        "oss.sonatype.org",
        sys.env.get("SONATYPE_USERNAME").getOrElse("raboof"),
        sys.env.get("SONATYPE_PASSWORD").getOrElse("geheim"))

pgpPassphrase := sys.env.get("PGP_PASSPHRASE").map(_.toArray)
pgpSecretRing := file("project/secring")
pgpPublicRing := file("project/pubring")
