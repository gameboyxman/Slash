package com.slash.tools;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import com.slash.elements.Location;
import com.slash.elements.Player;

//TODO: teleport restrictions: tp delay, attack/move break tp.

public class Teleport 
{
	public static void Warp(Player player, Location loc)
	{
		if(player.getLocation().dimension != loc.dimension)
		player.entityPlayerMP.travelToDimension(loc.dimension);
		
		player.entityPlayerMP.setPositionAndUpdate(loc.x, loc.height, loc.y);
	}
}