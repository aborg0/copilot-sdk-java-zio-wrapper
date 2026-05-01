# copilot-sdk-java-zio-wrapper

A Scala 3 library that wraps [GitHub Copilot SDK for Java](https://github.com/github/copilot-sdk-java) `0.3.0-java.2` with idiomatic ZIO types.

## What this gives you

- Scoped resource management with `ZIO.acquireRelease`
- Async interop from `CompletableFuture` to `Task`
- Scala-first config models (`CopilotSessionConfig`, `CopilotMessage`)
- Thin wrappers over `CopilotClient` and `CopilotSession`

## Requirements

- JDK 17+
- sbt 1.10+
- GitHub Copilot CLI 1.0.17+ on `PATH`

## Dependency

```scala
resolvers += "GitHub Packages aborg0" at "https://maven.pkg.github.com/aborg0/copilot-sdk-java-zio-wrapper"

libraryDependencies += "com.github.aborg0" %% "copilot-sdk-java-zio-wrapper" % "<version>"
```

See [docs/getting-started.md](docs/getting-started.md) for a usage example.

## Main API

- `com.github.aborg0.copilot.zio.CopilotZioClient`
- `com.github.aborg0.copilot.zio.CopilotZioSession`
- `com.github.aborg0.copilot.zio.CopilotSessionConfig`
- `dev.gaborb.copilot.zio.CopilotMessage`
