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
import com.slash.elements.ClaimedArea;
import com.slash.elements.Player;
import com.slash.tools.Protection;
import com.slash.tools.Protection.Access;

public class Share extends Command
{

	@Override
	public String getName() 
	{
		return "share";
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "[Area] [player] <Access> share an area that you own to a player.";
	}

	@Override
	public void processPlayer(Player sender, String[] args) 
	{
		if(args.length == 2)
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
			
			addAccess(area,playerName,null);
			IChatComponent reply = new ChatText("you have shared " + areaName + " with " + playerName + ".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));
			sender.sendChatMessage(reply);
		}
		else if(args.length == 3)
		{
			String areaName = args[0];
			String playerName = args[1];
			String accessName = args[2];
			
			if(!Protection.instance.contains(areaName))
			{
				IChatComponent reply = new ChatText("Can't find area named: " + areaName + ".").setChatStyle(new WarningStyle());
				sender.sendChatMessage(reply);
				return;
			}
			
			ClaimedArea area = Protection.instance.get(areaName);
			
			Access access = null;
			
			if(accessName.equalsIgnoreCase("edit_block"))
				access = Access.Edit_Block;
			if(accessName.equalsIgnoreCase("enter"))
				access = Access.Enter;
			if(accessName.equalsIgnoreCase("open_chest"))
				access = Access.Open_Chest;
			if(accessName.equalsIgnoreCase("pvp"))
				access = Access.PVP;
			if(accessName.equalsIgnoreCase("use_gate"))
				access = Access.Use_Gate;
			if(accessName.equalsIgnoreCase("use_switch"))
				access = Access.Use_Switch;
			
			if(access == null)
			{
				IChatComponent reply = new ChatText("No such access was defined. ").setChatStyle(new WarningStyle());
				
				reply.appendSibling(new Tooltip("Access list","edit_block, open_chest,\nuse_gate, use_switch.\ncoming soon: pvp, enter"));
				
				sender.sendChatMessage(reply);
				return;
			}
			
			addAccess(area,playerName,access);
			IChatComponent reply = new ChatText("you have shared " + areaName + " with " + playerName + ".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));
			sender.sendChatMessage(reply);
		}
		else
		{
			IChatComponent reply = new ChatText("Argument Mismatch!").setChatStyle(new WarningStyle());
			sender.sendChatMessage(reply);
			return;
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
	
	public static void addAccess(ClaimedArea area, String playerName, Access access)
	{
		
		if(access != null)
		{
			if(!area.share.containsKey(playerName))
			{
				area.share.put(playerName, new ArrayList<Access>());
			}
			
			if(!area.share.get(playerName).contains(access))
			{
				area.share.get(playerName).add(access);
				Protection.instance.save();
			}
		}
		else
		{
			addAccess(area,playerName,Access.Edit_Block);
			addAccess(area,playerName,Access.Enter);
			addAccess(area,playerName,Access.Open_Chest);
			addAccess(area,playerName,Access.Use_Gate);
			addAccess(area,playerName,Access.Use_Switch);
			addAccess(area,playerName,Access.PVP);
		}
	}
	
}
