package com.slash.elements;


import java.io.Serializable;

import com.mojang.authlib.GameProfile;
import com.slash.chats.templates.ChatText;
import com.slash.io.ProfileFile;
import com.slash.io.WarpPointsFile;
import com.slash.io.templates.SaveFile;
import com.slash.tools.Server;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldServer;


public class Player
{
	public EntityPlayerMP	entityPlayerMP;
	public String			name;
	public ProfileFile			profile;

	public Player(EntityPlayerMP entityPlayerMP)
	{
		this.entityPlayerMP = entityPlayerMP;
		name = entityPlayerMP.getCommandSenderName();
		setProfile();
	}

	public Player(ICommandSender sender)
	{
		name = sender.getCommandSenderName();
		entityPlayerMP = Server.getplayer(sender.getCommandSenderName());
		setProfile();
	}
	
	public Player(EntityClientPlayerMP player)
	{
		name = player.getCommandSenderName();
	}

	public Player(String playerName)
	{
		name = playerName;
		entityPlayerMP = Server.getplayer(playerName);
		setProfile();
	}

	private void setProfile()
	{
		profile = new ProfileFile(this.name);
	}

	public void sendChatMessage(IChatComponent chat)
	{
		this.entityPlayerMP.addChatMessage(chat);
	}

	public Location getLocation()
	{
		return new Location(this.entityPlayerMP.posX, this.entityPlayerMP.posZ, this.entityPlayerMP.posY, this.entityPlayerMP.worldObj.provider.dimensionId);
	}

	public void sendChatMessage(String chat)
	{
		this.sendChatMessage(new ChatText(chat));
	}
	
	public String toString()
	{
		return name;
	}
	
	public WarpPointsFile getWarpPointSaveFile()
	{
		WarpPointsFile file = new WarpPointsFile(this.name);
		file.load();
		return file;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Player)
		{
			Player temp = (Player) o;
			
			if(name.equals(temp.name))
				return true;
			else
				return false;
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
}
