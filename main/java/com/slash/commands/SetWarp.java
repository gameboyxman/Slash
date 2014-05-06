package com.slash.commands;

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

public class SetWarp extends Command
{

	@Override
	public String getName()
	{
		return "setwarp";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "Save the player's location for later use.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		if(args.length == 1)
		{
			Location loc = sender.getLocation();
			WarpPointsFile file = sender.getWarpPointSaveFile();
			if(!file.map.containsKey(args[0]))
			{
				file.map.put(args[0], loc.toString());
				file.save();
				sender.sendChatMessage(new ChatText("Warp point ").appendSibling(new WarpPoint(args[0],loc).toFancyString()).appendSibling(new ChatText(" has been set.")));
			}
			else
				sender.sendChatMessage(McColor.darkRed + "Already have a warp point named " + args[0] + ".");
		}
		else
		{
			sender.sendChatMessage(new ChatText("Argument Mismatch!").setChatStyle(new WarningStyle()));
			sender.sendChatMessage(new ChatText(this.getCommandUsage(null) + " ").appendSibling(getFancyUsage()));
		}
	}

	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
		
	}

	@Override
	public IChatComponent getFancyUsage()
	{
		return new Tooltip("detial","/setwarp [Warp point name]\nwill save your current location with the given name\nuse /warp [Warp point name] to come back here.");
	}

}
