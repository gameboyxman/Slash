package com.slash.elements;

import com.mojang.authlib.GameProfile;
import com.slash.tools.Server;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldServer;

public class Player
{
	public EntityPlayerMP entityPlayerMP;
	public String name;
	public Player(EntityPlayerMP entityPlayerMP)
	{
		this.entityPlayerMP = entityPlayerMP;
		name = entityPlayerMP.getCommandSenderName();
	}
	public Player(ICommandSender sender)
	{
		name = sender.getCommandSenderName();
		entityPlayerMP = Server.getplayer(sender.getCommandSenderName());
	}
	
	public void sendChatMessage(IChatComponent chat)
	{
		this.entityPlayerMP.addChatMessage(chat);
	}
	
	public Location getLocation()
	{
		return new Location(this.entityPlayerMP.posX, this.entityPlayerMP.posZ, this.entityPlayerMP.posY, this.entityPlayerMP.worldObj.provider.dimensionId);
	}
}
