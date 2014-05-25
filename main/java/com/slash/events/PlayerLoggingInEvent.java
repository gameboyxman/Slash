package com.slash.events;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class PlayerLoggingInEvent extends PlayerEvent
{
	public PlayerLoggingInEvent(EntityPlayer player)
	{
		super(player);
	}
}
