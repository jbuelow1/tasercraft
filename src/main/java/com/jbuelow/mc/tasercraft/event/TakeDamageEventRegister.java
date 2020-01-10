package com.jbuelow.mc.tasercraft.event;

import com.jbuelow.mc.tasercraft.hardware.ArduinoTaser;
import com.jbuelow.mc.tasercraft.pool.TaseEvent;
import com.jbuelow.mc.tasercraft.pool.TaserThreadPool;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class TakeDamageEventRegister {

  private final TaserThreadPool pool;

  public TakeDamageEventRegister(ArduinoTaser at) {
    pool = new TaserThreadPool(at);
    pool.start();
  }

  @SubscribeEvent
  public void damageEvent(LivingHurtEvent livingHurtEvent) {
    //System.out.println("LivingHurtEvent received.");
    if (Objects.equals(livingHurtEvent.getEntity(), Minecraft.getMinecraft().player)) {
      System.out.println("Player has taken damage.");
      livingHurtEvent.getEntity().sendMessage(new TextComponentString("REEEEEEEEEE TASER TASER TASER!!!!!!!").setStyle(new Style().setBold(true).setColor(
          TextFormatting.DARK_RED)));
      pool.queue(new TaseEvent(livingHurtEvent));
    }
  }

}
