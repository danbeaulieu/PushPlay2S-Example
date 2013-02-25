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

  val pushplay2sDeps = Seq(
    "com.typesafe" %% "play-plugins-redis" % "2.1-1-RC2"
  )
  
  val pushplay2s = play.Project(
    appName + "-pushplay2s", appVersion, pushplay2sDeps, path = file("modules/PushPlay2S"))


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  ).dependsOn(pushplay2s).aggregate(pushplay2s)


}
