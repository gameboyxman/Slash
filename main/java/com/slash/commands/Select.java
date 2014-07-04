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
import com.slash.tools.Server;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class Select extends Command
{

	@Override
	public String getName()
	{
		return "select";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return " <name> You can select a claimed area or a cuboid area with this command.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		if(args.length == 1)
		{
			IChatComponent reply = null;
			
			if(this.selectArea(sender, args[0]))
			{
				reply = new ChatText("You have selected " + args[0] + ".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));
			}
			else
			{
				reply = new ChatText("Cannot find that area.").setChatStyle(new WarningStyle());
				String areaList = "Claimed Areas: \n";			
				int i = 0;
				for(ClaimedArea area : Protection.instance.areas)
				{
					i++;
					areaList += area.name + " ";
					if(i == 4)
					{
						areaList+="\n";
						i=0;
					}
				}
				
				reply.appendSibling(new Tooltip("Claimed Areas",areaList));
			}
			sender.sendChatMessage(reply);
		}
		else if(args.length == 0)
		{
			Area area = null;
			area = new CuboidArea();
			
			ComplexArea areas = SlashEventHandler.selectionMap.get(sender);
			
			if(areas == null)
				areas = new ComplexArea();
			
			if(areas.list.size() <= 0 || areas.list.get(areas.list.size()-1).defined)
			{
				sender.sendChatMessage("please left click to select two blocks");
				areas.list.add(area);
				SlashEventHandler.selectionMap.put(sender, areas);
			}
			else
			{
				sender.sendChatMessage(new ChatText("You haven't finish your last selection yet.").setChatStyle(new WarningStyle()));
			}
		}
		else
		{
			IChatComponent reply = new ChatText("Argument Mismatch!").setChatStyle(new WarningStyle());
			sender.sendChatMessage(reply);
		}
	}
	/**
	 *used to select a claimed area, return true if the area is found.
	 */
	public static boolean selectArea(Player player,String name)
	{
		ArrayList<ClaimedArea> areas = Protection.instance.areas;
		
		for(ClaimedArea area : areas)
		{
			if(area.name.equals(name))
			{
				SlashEventHandler.selectionMap.put(player, area);
				SlashMod.channel.sendTo(new SelectionBoxPacket(null), player.entityPlayerMP);
				for(Area temp : area.list)
				{
					SlashMod.channel.sendTo(new SelectionBoxPacket(temp), player.entityPlayerMP);
				}
				return true;
			}
		}
		
		return false;
	}
	
	
	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
		
	}

	@Override
	public IChatComponent getFancyUsage()
	{
		return new Tooltip("detial","/select <name> when name isn't given,\nyou will enter cuboid selction mode.");
	}
	
}
