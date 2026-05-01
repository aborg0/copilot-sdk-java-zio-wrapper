package com.github.aborg0.copilot.zio

import com.github.copilot.sdk.CopilotClient
import com.github.copilot.sdk.json.CopilotClientOptions
import com.github.copilot.sdk.json.SessionConfig
import zio.Scope
import zio.Task
import zio.UIO
import zio.ZIO
import zio.ZLayer

trait CopilotZioClient {
  def createSession(config: CopilotSessionConfig): Task[CopilotZioSession]

  def createSession(config: SessionConfig): Task[CopilotZioSession]
}

object CopilotZioClient {
  def scoped(options: Option[CopilotClientOptions] = None): ZIO[Scope, Throwable, CopilotZioClient] =
    ZIO.acquireRelease(start(options))(stop)
      .map(live)

  val layer: ZLayer[Any, Throwable, CopilotZioClient] =
    ZLayer.scoped(scoped())

  private def start(options: Option[CopilotClientOptions]): Task[CopilotClient] =
    for {
      client <- ZIO.attempt(options.fold(new CopilotClient())(new CopilotClient(_)))
      _ <- CopilotInterop.fromCompletableFuture(client.start()).unit
    } yield client

  private def stop(client: CopilotClient): UIO[Unit] =
    ZIO.attempt(client.close()).orDie

  private def live(underlying: CopilotClient): CopilotZioClient =
    new CopilotZioClient {
      override def createSession(config: CopilotSessionConfig): Task[CopilotZioSession] =
        createSession(config.toJava)

      override def createSession(config: SessionConfig): Task[CopilotZioSession] =
        CopilotInterop
          .fromCompletableFuture(underlying.createSession(config))
          .map(CopilotZioSession.live)
    }
}
