package com.jbuelow.mc.tasercraft.pool;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TaseEvent {

  private final LivingHurtEvent hurtEvent;

  public TaseEvent(LivingHurtEvent hurtEvent) {
    this.hurtEvent = hurtEvent;
  }

  public LivingHurtEvent getHurtEvent() {
    return hurtEvent;
  }
}
