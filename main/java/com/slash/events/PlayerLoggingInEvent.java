package com.slash.events;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class PlayerLoggingInEvent extends PlayerEvent
{
	public EntityPlayerMP entityPlayerMP = null;
	public PlayerLoggingInEvent(EntityPlayerMP player)
	{
		super(player);
		this.entityPlayerMP = player;
	}
}
