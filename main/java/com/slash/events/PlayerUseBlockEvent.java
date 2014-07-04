package com.slash.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.slash.elements.Location;

public class PlayerUseBlockEvent extends PlayerEvent
{
	public Location blockLocation = null;
	public PlayerUseBlockEvent(EntityPlayer player, Location blockLocation) 
	{
		super(player);
		this.blockLocation = blockLocation;
	}
}
