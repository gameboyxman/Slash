package com.slash.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;
import com.slash.commands.templates.Command;
import com.slash.elements.Location;
import com.slash.elements.Player;
import com.slash.tools.McColor;
import com.slash.tools.Teleport;

public class Top extends Command
{

	@Override
	public String getName()
	{
		return "top";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "teleport you to the very top block at your location.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		Location loc = new Location(sender);
		loc.height = loc.getHighestBlock() + 1;
		Teleport.Warp(sender, loc);
		sender.sendChatMessage(McColor.yellow + "You have been teleported.");
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
