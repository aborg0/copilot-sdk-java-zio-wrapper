# Getting Started

`copilot-sdk-java-zio-wrapper` wraps the [GitHub Copilot SDK for Java](https://github.com/github/copilot-sdk-java) with idiomatic ZIO types.

## Dependency

Add to your `build.sbt`:

```scala
resolvers += "GitHub Packages aborg0" at "https://maven.pkg.github.com/aborg0/copilot-sdk-java-zio-wrapper"

libraryDependencies += "com.github.aborg0" %% "copilot-sdk-java-zio-wrapper" % "@VERSION@"
```

## Requirements

- JDK 17+
- GitHub Copilot CLI 1.0.17+ on `PATH`

## Example

Start a Copilot session, register an event handler, send a prompt, and wait for completion:

```scala mdoc:compile-only
import com.github.aborg0.copilot.zio.*
import com.github.copilot.sdk.generated.AssistantMessageEvent
import zio.*

object MyApp extends ZIOAppDefault {
  override def run =
    CopilotZioClient.scoped().flatMap { client =>
      for {
        session <- client.createSession(
          CopilotSessionConfig.default.copy(model = Some("claude-sonnet-4.5"))
        )
        _ <- session.onEvent(classOf[AssistantMessageEvent]) { msg =>
          println(msg.getData.content())
        }
        _ <- session.sendAndWait(CopilotMessage("What is 2 + 2?"))
        _ <- Console.printLine("Session completed.")
      } yield ()
    }
}
```

## API

| Type | Description |
|------|-------------|
| `CopilotZioClient` | Manages the lifecycle of a `CopilotClient` as a scoped ZIO resource |
| `CopilotZioSession` | Wraps a `CopilotSession` with `Task`-based send and ZIO event registration |
| `CopilotSessionConfig` | Scala-idiomatic session configuration (model, permission handler) |
| `CopilotMessage` | Simple prompt wrapper that converts to `MessageOptions` |
