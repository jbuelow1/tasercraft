package com.jbuelow.mc.tasercraft.hardware;

import java.io.IOException;

public interface Taser {

  void connect() throws Exception;
  void tase(double power) throws IOException;

}
