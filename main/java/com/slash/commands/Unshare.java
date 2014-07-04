package com.slash.commands;

import java.util.ArrayList;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import com.slash.chats.styles.WarningStyle;
import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.Tooltip;
import com.slash.commands.templates.Command;
import com.slash.elements.Area;
import com.slash.elements.ClaimedArea;
import com.slash.elements.ComplexArea;
import com.slash.elements.CuboidArea;
import com.slash.elements.Player;
import com.slash.fml.SlashEventHandler;
import com.slash.fml.SlashMod;
import com.slash.packet.client.SelectionBoxPacket;
import com.slash.tools.Protection;

public class Unshare extends Command
{

	@Override
	public String getName()
	{
		return "unshare";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return " [AreaName] <PlayerName> You can stop sharing an area with anyone or a named player.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		if(args.length == 1)
		{
			String areaName = args[0];
			
			if(!Protection.instance.contains(areaName))
			{
				IChatComponent reply = new ChatText("Can't find area named: " + areaName + ".").setChatStyle(new WarningStyle());
				sender.sendChatMessage(reply);
				return;
			}
			
			ClaimedArea area = Protection.instance.get(areaName);
			
			area.share.clear();
			
			Protection.instance.save();
			
			IChatComponent reply = new ChatText("you have unshared " + areaName+ ".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));
			sender.sendChatMessage(reply);
		}
		else if(args.length == 2)
		{
			String areaName = args[0];
			String playerName = args[1];
			
			if(!Protection.instance.contains(areaName))
			{
				IChatComponent reply = new ChatText("Can't find area named: " + areaName + ".").setChatStyle(new WarningStyle());
				sender.sendChatMessage(reply);
				return;
			}
			
			ClaimedArea area = Protection.instance.get(areaName);
			
			area.share.remove(playerName);
			
			Protection.instance.save();
			
			IChatComponent reply = new ChatText("you have unshared " + areaName + " with " + playerName + ".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));
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
