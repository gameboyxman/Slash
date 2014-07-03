package com.slash.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import com.slash.chats.templates.ChatText;
import com.slash.commands.templates.Command;
import com.slash.elements.Player;
import com.slash.fml.SlashEventHandler;
import com.slash.fml.SlashMod;
import com.slash.packet.client.SelectionBoxPacket;
import com.slash.tools.Graphics;

public class Deselect extends Command
{

	@Override
	public String getName()
	{
		return "deselect";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "cancel selected areas.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		ChatText reply = new ChatText("You have cleared your area selection.");
		reply.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GRAY));
		
		sender.sendChatMessage(reply);
		SlashEventHandler.selectionMap.remove(sender);
		SlashMod.channel.sendTo(new SelectionBoxPacket(null), sender.entityPlayerMP);
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
