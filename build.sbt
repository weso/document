lazy val scala212 = "2.12.11"
lazy val scala213 = "2.13.1"
lazy val scalaJs  = "0.6.31"
lazy val supportedScalaVersions = List(scala212, scala213, scalaJs)

lazy val scalacticVersion        = "3.1.1"
lazy val scalaTestVersion        = "3.1.1"

lazy val scalacheckVersion       = "1.14.0"

lazy val scalactic         = "org.scalactic"              %% "scalactic"           % scalacticVersion
lazy val scalacheck        = "org.scalacheck"             %% "scalacheck"          % scalacheckVersion
lazy val scalaTest         = "org.scalatest"              %% "scalatest"           % scalaTestVersion


def priorTo2_13(scalaVersion: String): Boolean =
  CrossVersion.partialVersion(scalaVersion) match {
    case Some((2, minor)) if minor < 13 => true
    case _                              => false
  }

lazy val document = project
  .in(file("."))
  .enablePlugins(AsciidoctorPlugin,SiteScaladocPlugin,GhpagesPlugin)
  .disablePlugins(RevolverPlugin)
  .settings(commonSettings, publishSettings, ghPagesSettings)
  .settings(
    crossScalaVersions := supportedScalaVersions,
    unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject,
    libraryDependencies ++= Seq(),
    ThisBuild / turbo := true,
    cancelable in Global      := true,
    fork                      := true, 
    coverageHighlighting := true, 
  //  coverageEnabled := { 
  //   println(s"CoverageEnabled: ${coverageEnabled.value}, <2.13?: ${priorTo2_13(scalaVersion.value)}")
  //   coverageEnabled.value && priorTo2_13(scalaVersion.value) 
  //  }
  )


/* ********************************************************
 ******************** Grouped Settings ********************
 **********************************************************/


lazy val sharedDependencies = Seq(
  libraryDependencies ++= Seq(
    scalactic,
    scalaTest 
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
  scalaVersion := scala213,
  // format: off
  scalacOptions ++= Seq(
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.  "-encoding", "UTF-8",
    "-language:_",
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xlint",
    "-Yrangepos",
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    "-Xfatal-warnings",
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
  )
  // format: on
)

lazy val wixSettings = Seq(
  wixProductId        := "39b564d5-d381-4282-ada9-87244c76e14b",
  wixProductUpgradeId := "6a710435-9af4-4adb-a597-98d3dd0bade1"
)

lazy val ghPagesSettings = Seq(
  git.remoteRepo := "git@github.com:weso/document.git"
)

lazy val commonSettings = compilationSettings ++ sharedDependencies ++ Seq(
  organization := "es.weso",
  resolvers ++= Seq(
    Resolver.bintrayRepo("labra", "maven"),
    Resolver.bintrayRepo("weso", "weso-releases"),
    Resolver.sonatypeRepo("snapshots")
  ), 
  
)

lazy val publishSettings = Seq(
  maintainer      := "Jose Emilio Labra Gayo <labra@uniovi.es>",
  homepage        := Some(url("https://github.com/weso/document")),
  licenses        := Seq("MIT" -> url("http://opensource.org/licenses/MIT")),
  scmInfo         := Some(ScmInfo(url("https://github.com/weso/document"), "scm:git:git@github.com:weso/document.git")),
  autoAPIMappings := true,
  apiURL          := Some(url("http://weso.github.io/utils/latest/api/")),
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),                     
  scalacOptions in doc ++= Seq(
    "-diagrams-debug",
    "-target:jvm-1.8",
    "-doc-source-url",
    scmInfo.value.get.browseUrl + "/tree/master€{FILE_PATH}.scala",
    "-sourcepath",
    baseDirectory.in(LocalRootProject).value.getAbsolutePath,
    "-diagrams",
  ),
  publishMavenStyle              := true,
  bintrayRepository in bintray   := "weso-releases",
  bintrayOrganization in bintray := Some("weso")
)
