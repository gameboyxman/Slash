package com.slash.tools;

import com.slash.elements.Player;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class Server 
{
	public static MinecraftServer getServer()
	{
		return MinecraftServer.getServer();
	}
	
	public static ICommandManager getCommandManager()
	{
		return getServer().getCommandManager();
	}
	
	public static World getWorld(int id)
	{
		return getServer().worldServerForDimension(id);
	}
	
	public static EntityPlayerMP getplayer(String name)
	{
		return getServer().getConfigurationManager().getPlayerForUsername(name);
	}
	
	public static void sendchat(ICommandSender sender, String chat)
	{
		sender.addChatMessage(new ChatComponentText(chat));
	}
	
	public static void sendchat(Player sender, String chat)
	{
		sender.entityPlayerMP.addChatMessage(new ChatComponentText(chat));
	}
}
