import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "PushPlay2S-Example"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm
  )
  
  val PushPlay2S = play.Project(
    appName + "-pushPlay2S", appVersion, path = file("PushPlay2S"))


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  ).dependsOn(PushPlay2S)

}
