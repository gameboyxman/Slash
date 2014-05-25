package com.slash.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;
import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.Tooltip;
import com.slash.commands.templates.Command;
import com.slash.elements.Location;
import com.slash.elements.Player;
import com.slash.tools.McColor;

public class SetSpawn extends Command
{

	@Override
	public String getName()
	{
		return "setspawn";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "set server spawn point at your current location";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		Location location = new Location(sender);
		sender.entityPlayerMP.getServerForPlayer().setSpawnLocation((int)location.x,(int)location.height,(int)location.y);
		sender.sendChatMessage(new ChatText(McColor.grey + "Spawn set."));
	}

	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
	}

	@Override
	public IChatComponent getFancyUsage()
	{
		return new Tooltip("detial", "notice that minecraft spawn player AROUND the spawn point,\nnot at the spawn point.");
	}
	
}
