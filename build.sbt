
name := "CurationmapFactory"
version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "us.feliscat" % "feliscatuszerolibraries_2.12" % "0.0.1"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.0"
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.1"

// https://mvnrepository.com/artifact/org.jsoup/jsoup
libraryDependencies += "org.jsoup" % "jsoup" % "1.12.1"


  libraryDependencies ++= Seq(
    "dev.morphia.morphia" % "core" % "1.5.8",
    "org.mongodb" % "mongo-java-driver" % "3.12.0",
  "com.google.apis" % "google-api-services-customsearch" % "v1-rev73-1.25.0"
)
// https://mvnrepository.com/artifact/org.apache.lucene/lucene-spellchecker
libraryDependencies += "org.apache.lucene" % "lucene-spellchecker" % "3.6.2"

trapExit := false
