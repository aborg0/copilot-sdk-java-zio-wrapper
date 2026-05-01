package com.github.aborg0.copilot.zio

import zio.Task
import zio.ZIO

import java.util.concurrent.CompletableFuture

private[zio] object CopilotInterop {
  def fromCompletableFuture[A](thunk: => CompletableFuture[A]): Task[A] =
    ZIO.attempt(thunk).flatMap(ZIO.fromCompletionStage(_))
}
