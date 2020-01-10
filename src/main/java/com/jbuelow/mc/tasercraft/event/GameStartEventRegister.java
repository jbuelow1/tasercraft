package com.jbuelow.mc.tasercraft.event;

import com.jbuelow.mc.tasercraft.hardware.ArduinoTaser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class GameStartEventRegister {

  private final ArduinoTaser taser;

  public GameStartEventRegister(ArduinoTaser taser) {
    this.taser = taser;
  }

  @SubscribeEvent
  public void gameStartEvent(ClientConnectedToServerEvent e) {
    System.out.println("Client connected to server. Connecting taser...");
    new Thread(taser::connect).start();
  }

}
