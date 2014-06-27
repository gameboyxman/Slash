package com.slash.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;
import com.slash.chats.templates.Tooltip;
import com.slash.commands.templates.Command;
import com.slash.elements.Area;
import com.slash.elements.CubicArea;
import com.slash.elements.Player;
import com.slash.fml.SlashEventHandler;
import com.slash.tools.Graphics;

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
		return " [Select mod]";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		Area area = null;
		area = new CubicArea();
		
		
		sender.sendChatMessage("please left click to select two blocks");
		
		SlashEventHandler.selectionMap.put(sender, area);
		
		
		Graphics.selectedAreas.list.add(area);
	}

	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
		
	}

	@Override
	public IChatComponent getFancyUsage()
	{
		return new Tooltip("detial","/select [mode]\nmode can be cube\nsphere and more shapes are coming.");
	}
	
}
