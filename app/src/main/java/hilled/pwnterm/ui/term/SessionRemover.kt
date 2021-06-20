package hilled.pwnterm.ui.term

import hilled.pwnterm.backend.TerminalSession
import hilled.pwnterm.component.session.XSession
import hilled.pwnterm.services.NeoTermService

/**
 * @author kiva
 */
object SessionRemover {
  fun removeSession(termService: NeoTermService?, tab: TermTab) {
    tab.termData.termSession?.finishIfRunning()
    removeFinishedSession(termService, tab.termData.termSession)
    tab.cleanup()
  }

  fun removeXSession(termService: NeoTermService?, tab: XSessionTab?) {
    removeFinishedSession(termService, tab?.session)
  }

  private fun removeFinishedSession(termService: NeoTermService?, finishedSession: TerminalSession?) {
    if (termService == null || finishedSession == null) {
      return
    }

    termService.removeTermSession(finishedSession)
  }

  private fun removeFinishedSession(termService: NeoTermService?, finishedSession: XSession?) {
    if (termService == null || finishedSession == null) {
      return
    }

    termService.removeXSession(finishedSession)
  }
}
