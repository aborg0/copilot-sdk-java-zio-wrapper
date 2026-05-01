package com.github.aborg0.copilot.zio

import com.github.copilot.sdk.CopilotSession
import com.github.copilot.sdk.generated.SessionEvent
import com.github.copilot.sdk.json.MessageOptions
import java.util.function.Consumer
import zio.Task
import zio.UIO
import zio.ZIO

trait CopilotZioSession {
  def sendAndWait(message: CopilotMessage): Task[Unit]

  def sendAndWait(messageOptions: MessageOptions): Task[Unit]

  def onEvent[E <: SessionEvent](eventClass: Class[E])(handler: E => Unit): UIO[Unit]
}

object CopilotZioSession {
  private[zio] def live(underlying: CopilotSession): CopilotZioSession =
    new CopilotZioSession {
      override def sendAndWait(message: CopilotMessage): Task[Unit] =
        sendAndWait(message.toJava)

      override def sendAndWait(messageOptions: MessageOptions): Task[Unit] =
        CopilotInterop
          .fromCompletableFuture(underlying.sendAndWait(messageOptions))
          .unit

      override def onEvent[E <: SessionEvent](eventClass: Class[E])(handler: E => Unit): UIO[Unit] =
        ZIO.succeed(
          underlying.on(
            eventClass,
            new Consumer[E] {
              override def accept(event: E): Unit = handler(event)
            }
          )
        )
          .unit
    }
}
