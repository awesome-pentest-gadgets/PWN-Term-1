package hilled.pwnterm;

import hilled.pwnterm.xorg.NeoXorgViewClient;

/**
 * @author kiva
 */

public class NeoXorgSettings {
  public static void init(NeoXorgViewClient client) {
    Settings.Load(client);
  }
}
