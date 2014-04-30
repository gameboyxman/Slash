package com.slash.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;
import com.slash.chats.templates.ChatText;
import com.slash.commands.templates.Command;
import com.slash.elements.Location;
import com.slash.elements.Player;

public class SetHome extends Command
{
	
	@Override
	public String getName()
	{
		return "sethome";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "set players home at their current location.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		sender.profile.load();
		Location loc = sender.getLocation();
		sender.profile.home = loc;
		sender.profile.save();
		sender.sendChatMessage(new ChatText("Your home has been set to ").appendSibling(loc.toFancyString()));
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
