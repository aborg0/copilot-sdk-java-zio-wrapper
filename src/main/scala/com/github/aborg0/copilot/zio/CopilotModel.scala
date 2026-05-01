package com.github.aborg0.copilot.zio

import com.github.copilot.sdk.json.MessageOptions
import com.github.copilot.sdk.json.PermissionHandler
import com.github.copilot.sdk.json.SessionConfig

final case class CopilotSessionConfig(
  model: Option[String] = None,
  permissionHandler: PermissionHandler = PermissionHandler.APPROVE_ALL
) {
  def toJava: SessionConfig = {
    val base = new SessionConfig().setOnPermissionRequest(permissionHandler)
    model.fold(base)(base.setModel)
  }
}

object CopilotSessionConfig {
  val default: CopilotSessionConfig = CopilotSessionConfig()
}

final case class CopilotMessage(prompt: String) {
  def toJava: MessageOptions = new MessageOptions().setPrompt(prompt)
}
