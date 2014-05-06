package com.slash.commands;

import java.util.ArrayList;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;
import com.slash.chats.styles.WarningStyle;
import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.Tooltip;
import com.slash.commands.templates.Command;
import com.slash.elements.Location;
import com.slash.elements.Player;
import com.slash.elements.WarpPoint;
import com.slash.io.WarpPointsFile;
import com.slash.tools.McColor;
import com.slash.tools.Teleport;

public class Warp extends Command
{

	@Override
	public String getName()
	{
		return "warp";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "teleports you to another player or a warp point.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		try
		{
			Location targetLocation = new Location(sender);
			ChatText reply = new ChatText();
			if(args.length == 0)
			{
				//TODO: make /warp muti-page, enter /warp again to show next page.
				ArrayList<WarpPoint> list = new WarpPointsFile(sender.name).getAllWarpPoints();
				
				sender.profile.load();
				Location home = sender.profile.home;
				
				if(list.size() > 0 || home != null)
					sender.sendChatMessage(McColor.yellow + "Click to warp:");
				else
				{
					sender.sendChatMessage(McColor.grey + "You have no warp point.");
					return;
				}
					
				if(home != null)
					reply.appendSibling(new WarpPoint("Home",home).toFancyString());
				
				for(WarpPoint wp : list)
				{
					reply.appendText(" ");
					reply.appendSibling(wp.toFancyString());
				}
				
				sender.sendChatMessage(reply);
				return;
			}
			else if(args.length == 1)
			{
				WarpPointsFile file = new WarpPointsFile(sender.name);
				
				if(file.map.containsKey(args[0]))
				{
					targetLocation = new Location(file.map.get(args[0]));
				}
				else
				{
					reply.setChatStyle(new WarningStyle());
					reply.appendText("Slash can't find that warp point.");
					sender.sendChatMessage(reply);
					return;
				}
			}
			else if(args.length == 2)
			{
				double x = Double.parseDouble(args[0]);
				double y = Double.parseDouble(args[1]);
				
				targetLocation.x = x;
				targetLocation.y = y;
				targetLocation.height = targetLocation.getHighestBlock();
			}
			else if(args.length == 3)
			{
				double x = Double.parseDouble(args[0]);
				double y = Double.parseDouble(args[1]);
				double height = Double.parseDouble(args[2]);
				
				targetLocation.x = x;
				targetLocation.y = y;
				targetLocation.height = height;
			}
			else if(args.length == 4)
			{
				double x = Double.parseDouble(args[0]);
				double y = Double.parseDouble(args[1]);
				double height = Double.parseDouble(args[2]);
				int dimension = Integer.parseInt(args[3]);
				
				targetLocation.x = x;
				targetLocation.y = y;
				targetLocation.height = height;
				targetLocation.dimension = dimension;
			}
			else
			{
				sender.sendChatMessage(McColor.darkRed + "Wrong arguments!");
				return;
			}
			
			reply.appendText(McColor.yellow + "you have been teleported.");
			Teleport.Warp(sender, targetLocation);
			sender.sendChatMessage(reply);
		}
		catch(NumberFormatException e)
		{
			sender.sendChatMessage(McColor.darkRed + "opps, did you enter something wrong for the coordinate?");
		}
	}

	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
		
	}

	@Override
	public IChatComponent getFancyUsage()
	{
		return new Tooltip("detial","Usage:\n" +
				"/warp\n" +
				"show a list of warp points that you may use.\n\n" +
				"/warp <name>\n" +
				"warp you to a warp point or player with that name\n\n" +
				"/warp <x> <y> [height] [dimension]\n" +
				"warp you to that location.\n" +
				"if height isn't given, the highest ground is used.\n" +
				"if dimension isn't given, your current dimension is used.");
	}

}
