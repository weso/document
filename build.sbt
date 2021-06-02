lazy val scala212 = "2.12.13"
lazy val scala213 = "2.13.6"
lazy val scalaJs  = "0.6.31"
lazy val scala3   = "3.0.0-RC2"

lazy val supportedScalaVersions = List(
  scala212,
  scala213,
//  scala3
  )

val Java11 = "adopt@1.11"

ThisBuild / crossScalaVersions := supportedScalaVersions
// ThisBuild / scalaVersion := crossScalaVersions.value.last

// ThisBuild / githubWorkflowJavaVersions := Seq(Java11)
// ThisBuild / githubWorkflowScalaVersions := (ThisBuild / crossScalaVersions).value.tail

// ThisBuild / githubWorkflowPublishTargetBranches := Seq(RefPredicate.Equals(Ref.Branch("master")), RefPredicate.StartsWith(Ref.Tag("v")))
ThisBuild / githubWorkflowBuild := Seq(
  WorkflowStep
    .Use(UseRef.Public("ruby", "setup-ruby", "v1"), params = Map("ruby-version" -> "2.7"), name = Some("Set up Ruby")),
  WorkflowStep.Run(
    List("gem install sass", "gem install jekyll -v 4.0.0"),
    name = Some("Install Jekyll")
  ),
  WorkflowStep.Sbt(
    List(
      "clean",
      "coverage"
    ),
    id = None,
    name = Some("Test")
  ),
  WorkflowStep.Sbt(
    List("coverageReport"),
    id = None,
    name = Some("Coverage")
  ),
  WorkflowStep.Use(
    UseRef.Public(
      "codecov",
      "codecov-action",
      "v1"
    )
  )
)
ThisBuild / versionScheme := Some("early-semver")

lazy val munitVersion = "0.7.23"

lazy val munit        = "org.scalameta" %% "munit" % munitVersion % Test

def priorTo2_13(scalaVersion: String): Boolean =
  CrossVersion.partialVersion(scalaVersion) match {
    case Some((2, minor)) if minor < 13 => true
    case _                              => false
  }

lazy val document = project
  .in(file("."))
  .enablePlugins(AsciidoctorPlugin,SiteScaladocPlugin,GhpagesPlugin)
  // .disablePlugins(RevolverPlugin)
  .settings(commonSettings, ghPagesSettings)
  .settings(
    ThisBuild / turbo    := true,
    cancelable in Global := true,
    fork                 := true,
    coverageHighlighting := true,
    testFrameworks += new TestFramework("munit.Framework"),
    // githubOwner := "weso",
    // githubRepository := "document"
  )


/* ********************************************************
 ******************** Grouped Settings ********************
 **********************************************************/


lazy val sharedDependencies = Seq(
  libraryDependencies ++= Seq(
    munit
  )
)

val compilerOptions = Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-unchecked",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfuture",
  "-Yno-predef",
  "-Ywarn-unused-import"
)

lazy val compilationSettings = Seq(
  // scalaVersion := scala3,
  // format: off
  scalacOptions ++= Seq(
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.  "-encoding", "UTF-8",
    "-language:_",
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xfatal-warnings"
  )
  // format: on
)

lazy val ghPagesSettings = Seq(
  git.remoteRepo := "git@github.com:weso/document.git"
)

lazy val commonSettings = compilationSettings ++ sharedDependencies ++ Seq(
  organization := "es.weso",
  resolvers ++= Seq(
    // Resolver.githubPackages("weso")
  ),

)

//lazy val publishSettings = Seq(
//  sonatypeProfileName := ("es.weso"),
//  publishMavenStyle   := true,
//  homepage            := Some(url("https://github.com/weso/document")),
//  licenses            := Seq("MIT" -> url("http://opensource.org/licenses/MIT")),
//  scmInfo             := Some(ScmInfo(url("https://github.com/weso/document"), "scm:git:git@github.com:weso/document.git")),
//  autoAPIMappings     := true,
//  apiURL              := Some(url("http://weso.github.io/utils/latest/api/")),
//  publishMavenStyle   := true,
//  sonatypeRepository  := "https://s01.oss.sonatype.org/service/local"
//)

/**********************************************************
 ******************** Sonatype Settings *******************
 **********************************************************/

sonatypeProfileName := ("es.weso")
publishMavenStyle   := true
homepage            := Some(url("https://github.com/weso/document"))
licenses            := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))
scmInfo             := Some(ScmInfo(url("https://github.com/weso/document"), "scm:git:git@github.com:weso/document.git"))
autoAPIMappings     := true
apiURL              := Some(url("http://weso.github.io/utils/latest/api/"))
publishMavenStyle   := true
publishTo in ThisBuild := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
