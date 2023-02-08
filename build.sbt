import java.nio.file.Files
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.nio.file.Paths
import scala.jdk.CollectionConverters._
import scala.util.Try

lazy val start = TaskKey[Unit]("start")

lazy val root = (project in file("."))
  .configure(jsCompile)
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name                            := "third-thing-landing-page",
    scalaVersion                    := "3.2.2",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.typelevel"                 %% "cats-core"            % "2.9.0",
      "org.typelevel"                %%% "cats-effect"          % "3.4.4",
      "com.raquo"                    %%% "laminar"              % "0.14.2",
      "com.raquo"                    %%% "waypoint"             % "0.5.0",
      "com.github.japgolly.scalacss" %%% "core"                 % "1.0.0",
      "io.monix"                     %%% "newtypes-core"        % "0.2.3",
      "io.monix"                     %%% "newtypes-circe-v0-14" % "0.2.3",
      "io.circe"                     %%% "circe-core"           % "0.14.3",
      "io.circe"                     %%% "circe-generic"        % "0.14.3"
    )
  )

lazy val jsCompile: Project => Project =
  _.settings(start := {
    (Compile / fastLinkJS).value
    Files.list((Compile / resourceDirectory).value.toPath()).toList.asScala.map { source =>
      val destination = (Compile / fastLinkJS / scalaJSLinkerOutputDirectory).value.toPath().resolve(source.getFileName().toString())

      Files.copy(source, destination, REPLACE_EXISTING)
    }

  })
