package com.slash.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import com.slash.chats.templates.ChatText;
import com.slash.commands.templates.Command;
import com.slash.elements.Player;
import com.slash.fml.SlashMod;

public class Slash extends Command
{

	@Override
	public String getName()
	{
		return "slash";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "show you all the commands that slash offers, more detialed usage can be found here..";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		sender.sendChatMessage(new ChatText("Slash Mod(" + SlashMod.VERSION + ") by fihgu").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
		sender.sendChatMessage("=============================================");
		for(Command command : list)
		{
			ChatText usage = new ChatText(command.getCommandUsage(null) + " ");
			IChatComponent fancyUsage = command.getFancyUsage();
			if(fancyUsage != null)
				usage.appendSibling(fancyUsage);
			sender.sendChatMessage(usage);
		}
		sender.sendChatMessage("=============================================");
	}

	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public IChatComponent getFancyUsage()
	{
		return null;
	}

}
