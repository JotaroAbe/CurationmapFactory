
name := "CurationmapFactory"
version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "us.feliscat" % "feliscatuszerolibraries_2.12" % "0.0.1"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.0"
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.1"
  libraryDependencies ++= Seq(
  "org.mongodb.morphia" % "morphia" % "1.3.2",
  "org.mongodb" % "mongo-java-driver" % "3.7.1",
  "com.google.apis" % "google-api-services-customsearch" % "v1-rev73-1.25.0"
)
trapExit := false
