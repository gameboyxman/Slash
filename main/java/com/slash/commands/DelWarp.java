package com.slash.commands;

import java.util.ArrayList;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.Tooltip;
import com.slash.commands.templates.Command;
import com.slash.elements.Location;
import com.slash.elements.Player;
import com.slash.elements.WarpPoint;
import com.slash.io.WarpPointsFile;
import com.slash.tools.McColor;

public class DelWarp extends Command
{

	@Override
	public String getName()
	{
		return "delwarp";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "delelte a given warpPoint.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		if(args.length == 1)
		{
			WarpPointsFile file = new WarpPointsFile(sender.name);
			file.load();
			if(file.map.containsKey(args[0]))
			{
				sender.sendChatMessage(new ChatText("Deleted warp point: ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GRAY)).appendSibling(new WarpPoint(args[0],new Location(file.map.get(args[0]))).toFancyString()));
				file.map.remove(args[0]);
				file.save();
			}
			else
				sender.sendChatMessage(McColor.darkRed + "slash can't find that warp point.");
		}
		else
		{
			sender.sendChatMessage(McColor.darkRed + "Wrong arguments!");
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
		return new Tooltip("detial","/delwarp [name]\ndelete that warp point.");
	}

}
