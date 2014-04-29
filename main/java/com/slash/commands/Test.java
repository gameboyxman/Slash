package com.slash.commands;


import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import com.slash.chats.styles.WarningStyle;
import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.ItemTooltip;
import com.slash.commands.templates.Command;
import com.slash.elements.Player;
import com.slash.tools.McColor;


public class Test extends Command
{
	
	@Override
	public String getName()
	{
		return "test";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "this is the test command.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		sender.entityPlayerMP.addChatMessage(sender.getLocation().toFancyString());
	}

	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
		// TODO Auto-generated method stub
	}	
}
