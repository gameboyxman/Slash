package com.slash.tools;

import com.slash.elements.Player;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventBus;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class Server 
{
	public static MinecraftServer getServer()
	{		
		MinecraftServer server = MinecraftServer.getServer();
		return server;
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
		MinecraftServer server = getServer();
		ServerConfigurationManager scm = server.getConfigurationManager();
		
		return scm.getPlayerForUsername(name);
	}
	
	public static void sendchat(ICommandSender sender, String chat)
	{
		sender.addChatMessage(new ChatComponentText(chat));
	}
	
	public static void sendchat(Player sender, String chat)
	{
		sender.entityPlayerMP.addChatMessage(new ChatComponentText(chat));
	}
	
	public static EventBus getBus()
	{
		return FMLCommonHandler.instance().bus();
	}
	
	public static boolean isOp(String playerName)
	{
		return getServer().getConfigurationManager().getOps().contains(playerName.toLowerCase());
	}
}
