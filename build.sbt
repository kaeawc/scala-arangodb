

organization := "com.arangodb"

name := "scala-arangodb"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.10.3"

resolvers ++= Seq(
  "SonaType" at "https://oss.sonatype.org/content/groups/public/org/scalatest/",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "2.3.3",
  "org.mockito" % "mockito-all" % "1.9.5",
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.2",
  "com.typesafe.play" % "play-json_2.10" % "2.2.2",
  "com.ning" % "async-http-client" % "1.8.3"
)

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:postfixOps",
  "-language:implicitConversions"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

licenses := Seq("BSD-style" -> url("http://www.opensource.org/licenses/bsd-license.php"))

homepage := Some(url("http://kaeawc.github.io/"))
