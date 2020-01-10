package com.jbuelow.mc.tasercraft.hardware;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.NotYetConnectedException;
import java.util.Objects;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import purejavacomm.CommPort;
import purejavacomm.CommPortIdentifier;
import purejavacomm.SerialPort;

public class ArduinoTaser implements Taser {

  CommPort taserDevice;
  OutputStreamWriter osw;
  InputStreamReader isr;

  private final String comPort;

  public ArduinoTaser(String comPort) {
    this.comPort = comPort;
  }

  @Override
  public void connect() {
    System.out.println("Connecting taser...");
    try {
      if (Objects.nonNull(isr)) {
        isr.close();
      }
      if (Objects.nonNull(osw)) {
        osw.close();
      }
      if (Objects.nonNull(taserDevice)) {
        taserDevice.close();
      }

      taserDevice = CommPortIdentifier
          .getPortIdentifier(comPort).open(this.getClass().getName(), 2000);
      SerialPort serialPort = ( SerialPort )taserDevice;
      serialPort.setSerialPortParams( 115200,
          SerialPort.DATABITS_8,
          SerialPort.STOPBITS_1,
          SerialPort.PARITY_NONE );
      osw = new OutputStreamWriter(serialPort.getOutputStream());
      isr = new InputStreamReader(serialPort.getInputStream());
      osw.write('t');
      osw.flush();
      char[] cbuf = new char[11];
      isr.read(cbuf);
      if (!String.valueOf(cbuf).equals("TASERCRAFT")) {
        throw new IOException();
      }
      System.out.println("Taser is connected and ready.");
    } catch (Exception e) {
      osw = null;
      System.out.println("Could not connect to taser.");
    }
  }

  @Override
  public void tase(double power) throws IOException {
    for (int i = 0; i < 10; i++) {
      System.out.println("REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
      System.out.println("TASER TASER TASER TASER TASER TASER TASER TASER TASER TASER TASER TASER TASER");
    }
    System.out.println("REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
    if (osw == null) {
      connect();
    }
    try {
      osw.write('t');
      osw.write((int)power);
      osw.flush();
    } catch (NullPointerException e) {
      System.out.println("Taser not connected.");
    }
  }
}
