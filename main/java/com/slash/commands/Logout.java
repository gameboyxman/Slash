package com.slash.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;

import com.slash.commands.templates.Command;
import com.slash.elements.Player;
import com.slash.tools.Server;

public class Logout extends Command
{

	@Override
	public String getName() 
	{
		return "logout";
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "logout from this computer.";
	}

	@Override
	public void processPlayer(Player sender, String[] args) 
	{
		sender.profile.load();
		sender.profile.ip = null;
		sender.profile.save();
		sender.entityPlayerMP.playerNetServerHandler.kickPlayerFromServer("Cya~!");
	}

	@Override
	public void processConsole(ICommandSender console, String[] args) 
	{
		
	}

	@Override
	public IChatComponent getFancyUsage() 
	{
		return null;
	}
	
}
