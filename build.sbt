ThisBuild / organization := "com.github.aborg0"
ThisBuild / scalaVersion := "3.3.7"
ThisBuild / versionScheme := Some("early-semver")

// GitHub Packages publishing
ThisBuild / publishTo := Some(
  "GitHub Packages" at s"https://maven.pkg.github.com/aborg0/copilot-sdk-java-zio-wrapper"
)
ThisBuild / credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  "aborg0",
  sys.env.getOrElse("GITHUB_TOKEN", "")
)

val zioVersion = "2.1.25"

lazy val zioGithubCopilotWrapper = (project in file("."))
  .settings(
    name := "copilot-sdk-java-zio-wrapper",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "com.github" % "copilot-sdk-java" % "0.3.0-java.2",
      "dev.zio" %% "zio-test" % zioVersion % Test,
      "dev.zio" %% "zio-test-sbt" % zioVersion % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )

lazy val docs = project
  .in(file("mdoc"))
  .settings(
    mdocIn := file("docs"),
    mdocOut := file("docs-out"),
    mdocVariables := Map("VERSION" -> version.value)
  )
  .dependsOn(zioGithubCopilotWrapper)
  .enablePlugins(MdocPlugin)
