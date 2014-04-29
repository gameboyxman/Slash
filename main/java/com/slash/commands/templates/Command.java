package com.slash.commands.templates;

import com.slash.elements.Player;
import com.slash.tools.Server;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public abstract class Command extends CommandBase
{	
	public abstract String getName();
	public abstract String getUsage(ICommandSender sender);	
	public abstract void processPlayer(Player sender, String[] args);
	public abstract void processConsole(ICommandSender console, String[] args);
	
	public void register(FMLServerStartingEvent e)
	{
		e.registerServerCommand(this);
	}
	
	@Override
	public String getCommandName()
	{
		//TODO: custom command names.
		return this.getName();
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) 
	{
		EntityPlayerMP player = Server.getplayer(sender.getCommandSenderName());
		if(player == null)
			processConsole(sender,args);
		else
			processPlayer(new Player(player),args);
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/" + this.getCommandName() + ": " + this.getUsage(sender);
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		return true;	
	}
}