package com.slash.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.ItemTooltip;
import com.slash.chats.templates.Tooltip;
import com.slash.commands.templates.Command;
import com.slash.elements.Player;
import com.slash.tools.Server;

public class Link extends Command
{
	@Override
	public String getName()
	{
		return "link";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "show everyone a link of the item you held.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		ChatText temp = null;
		
		temp = new ChatText(sender.name + " is showing off ");
		IChatComponent item = ItemTooltip.getItemTooltip(sender.entityPlayerMP.getCurrentEquippedItem());
		
		if("aeiouAEIOU".indexOf(item.getUnformattedText().charAt(1)) < 0)
		{
			temp.appendText("a ");
		}
		else
		{
			temp.appendText("an ");
		}
		
		temp.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));		
		temp.appendSibling(item);
		
		for(String name :Server.getServer().getConfigurationManager().getAllUsernames())
			Server.getplayer(name).addChatMessage(temp);
	}

	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
		
	}

	@Override
	public IChatComponent getFancyUsage()
	{
		return new Tooltip("detial","hold an item in your hand,\nthen use the command to show\neveryone a link of this item.");
	}
}
