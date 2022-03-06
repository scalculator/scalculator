import Dependencies._

ThisBuild / scalaVersion     := "2.13.7"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "io.github.scalculator"
ThisBuild / organizationName := "scalculator"

lazy val root = (project in file("."))
  .settings(
    name := "scalculator",
    libraryDependencies += scalaTest % Test,
    assembly / assemblyJarName := "scalculator.jar"
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
