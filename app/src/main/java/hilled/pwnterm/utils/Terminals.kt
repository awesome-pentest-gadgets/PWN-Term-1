package hilled.pwnterm.utils

import android.content.Context
import android.app.Activity
import hilled.pwnterm.backend.TerminalSession
import hilled.pwnterm.component.ComponentManager
import hilled.pwnterm.component.config.NeoPreference
import hilled.pwnterm.component.font.FontComponent
import hilled.pwnterm.component.session.SessionComponent
import hilled.pwnterm.component.session.ShellParameter
import hilled.pwnterm.component.session.XParameter
import hilled.pwnterm.component.session.XSession
import hilled.pwnterm.frontend.session.view.TerminalView
import hilled.pwnterm.frontend.session.view.TerminalViewClient
import hilled.pwnterm.frontend.session.view.extrakey.ExtraKeysView

/**
 * @author kiva
 */
object Terminals {
  fun setupTerminalView(terminalView: TerminalView?, terminalViewClient: TerminalViewClient? = null) {
    terminalView?.textSize = NeoPreference.getFontSize();

    val fontComponent = ComponentManager.getComponent<FontComponent>()
    fontComponent.applyFont(terminalView, null, fontComponent.getCurrentFont())

    if (terminalViewClient != null) {
      terminalView?.setTerminalViewClient(terminalViewClient)
    }
  }

  fun setupExtraKeysView(extraKeysView: ExtraKeysView?) {
    val fontComponent = ComponentManager.getComponent<FontComponent>()
    val font = fontComponent.getCurrentFont()
    fontComponent.applyFont(null, extraKeysView, font)
  }

  fun createSession(context: Context, parameter: ShellParameter): TerminalSession {
    val sessionComponent = ComponentManager.getComponent<SessionComponent>()
    return sessionComponent.createSession(context, parameter)
  }

  fun createSession(activity: Activity, parameter: XParameter): XSession {
    val sessionComponent = ComponentManager.getComponent<SessionComponent>()
    return sessionComponent.createSession(activity, parameter)
  }

  fun escapeString(s: String?): String {
    if (s == null) {
      return ""
    }

    val builder = StringBuilder()
    val specialChars = "\"\\$`!"
    builder.append('"')
    val length = s.length
    for (i in 0 until length) {
      val c = s[i]
      if (specialChars.indexOf(c) >= 0) {
        builder.append('\\')
      }
      builder.append(c)
    }
    builder.append('"')
    return builder.toString()
  }
}
