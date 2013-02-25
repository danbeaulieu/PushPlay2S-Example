# PushPlay2S Example Project

This is an example Play! 2.0 project which shows how you'd add the PushPlay2S project as a subproject to easily add real time messaging to your existing Play! 2.0 application. For more information about PushPlay2S, please check out the repo at https://github.com/danbeaulieu/PushPlay2S

## Requirements

- Play 2.1
- Redis >= 2.4

## Usage

Starting from scratch you'll create a Play! project

<pre>
  $ play new example
  $ cd example
  $ git init
  $ git add .
  $ git commit -a -m 'my new play app'
</pre>

Then you need to add the PushPlay2S as a git submodule in the modules directory (read more about git submodules here
http://git-scm.com/book/en/Git-Tools-Submodules):

<pre>
  $ mkdir modules
  $ cd modules
  $ git submodule add git://github.com/danbeaulieu/PushPlay2S.git PushPlay2S
</pre>

Next you need to add the module to the project (read more about Play! 2.0 subprojects here https://github.com/playframework/Play20/wiki/SBTSubProjects):

project/Build.scala should look something like:

<pre>
  import sbt._
  import Keys._
  import play.Project._

  object ApplicationBuild extends Build {

    val appName         = "Example"
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
</pre>

modify conf/application.conf to include PushPlay2S configuration parameters, something like:
<pre>
  pusher.appId
  pusher.key
  pusher.secret
</pre>

Add a new route to the PushPlay2S project in conf/routes:

<pre>
  GET     /app/:apiKey                controllers.pushplay2s.Application.app(apiKey: String)
</pre>

You'll also need to implement endpoint at /pusher/auth that authorizes users trying to subscribe to private and presence channels. This sample app comes with a dummy authorization endpoint which authorizes all requests.


