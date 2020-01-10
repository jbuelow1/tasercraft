package com.jbuelow.mc.tasercraft.pool;

import com.jbuelow.mc.tasercraft.hardware.ArduinoTaser;
import com.jbuelow.mc.tasercraft.hardware.Taser;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class TaserThreadPool extends Thread {

  private final Taser taser;

  private final ExecutorService executorService = Executors.newFixedThreadPool(1);

  private final BlockingQueue<TaseEvent> queue = new LinkedBlockingDeque<>();

  public TaserThreadPool(ArduinoTaser at) {
    taser = at;
  }

  public void queue(TaseEvent te) {
    try {
      queue.offer(te, 500, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Could not add to queue.", e);
    }
  }

  @Override
  public void run() {
    while (!this.isInterrupted()) {
      TaseEvent e;
      try {
        e = queue.take();
      } catch (InterruptedException ex) {
        continue;
      }

      for (int i = 0; i < e.getHurtEvent().getAmount(); i++) {
        try {
          taser.tase(e.getHurtEvent().getAmount());
        } catch (IOException ex) {
          System.out.println("Failed to deliver a shock.");
        }
      }
    }
  }
}
