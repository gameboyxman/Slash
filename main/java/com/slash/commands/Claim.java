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

public class Claim extends Command
{

	@Override
	public String getName() 
	{
		return "claim";
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "[name] claim your selected area and give it a name.";
	}

	@Override
	public void processPlayer(Player sender, String[] args) 
	{
		if(args.length == 1)
		{
			ComplexArea tempArea = SlashEventHandler.selectionMap.get(sender);
			
			if(tempArea == null)
			{
				IChatComponent reply = new ChatText("Please select an area with /select first!").setChatStyle(new WarningStyle());
				sender.sendChatMessage(reply);
				return;
			}
			
			String name = args[0];
			ClaimedArea area = new ClaimedArea(tempArea);
			area.owner = sender.name;
			area.name = name;
			
			if(Protection.instance.contains(name))
			{
				IChatComponent reply = new ChatText("A claimed area with that name already exists.").setChatStyle(new WarningStyle());
				sender.sendChatMessage(reply);
				return;
			}
			
			for(ClaimedArea temp : Protection.instance.areas)
			{
				if(temp.isOverlapping(area))
				{
					IChatComponent reply = new ChatText("Your selection of area is overlapping with ").appendSibling(temp.toFancyString()).setChatStyle(new WarningStyle());
					sender.sendChatMessage(reply);
					return;
				}
			}
			
			Protection.instance.areas.add(area);
			IChatComponent reply = new ChatText("You have claimed " + area.name + ".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));
			sender.sendChatMessage(reply);
			Protection.instance.save();
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
		return new Tooltip("detial","other player can not edit the blocks within your claimed area.");
	}

}
