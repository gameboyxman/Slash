package com.slash.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import com.slash.chats.styles.WarningStyle;
import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.Tooltip;
import com.slash.commands.templates.Command;
import com.slash.elements.ClaimedArea;
import com.slash.elements.ComplexArea;
import com.slash.elements.Player;
import com.slash.fml.SlashEventHandler;
import com.slash.tools.Protection;

public class Disclaim extends Command
{

	@Override
	public String getName() 
	{
		return "disclaim";
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "[name] disclaim an area.";
	}

	@Override
	public void processPlayer(Player sender, String[] args) 
	{
		if(args.length == 1)
		{
			
			String name = args[0];
			
			if(!Protection.instance.contains(name))
			{
				IChatComponent reply = new ChatText("Cannot find " + args[0] + ".").setChatStyle(new WarningStyle());
				sender.sendChatMessage(reply);
				return;
			}
			
			ClaimedArea area = Protection.instance.get(args[0]);
			
			if(!area.owner.equals(sender.name))
			{
				IChatComponent reply = new ChatText("You are not the owner of " + args[0] + ".").setChatStyle(new WarningStyle());
				sender.sendChatMessage(reply);
				return;
			}
			
			Protection.instance.remove(args[0]);
			Protection.instance.save();
			IChatComponent reply = new ChatText("You have disclaimed " + args[0] + ".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));
			sender.sendChatMessage(reply);
		}
		else
		{
			IChatComponent reply = new ChatText("Argument Mismatch!").setChatStyle(new WarningStyle());
			sender.sendChatMessage(reply);
		}
		
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
