package com.slash.commands;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import com.slash.chats.templates.ChatText;
import com.slash.commands.templates.Command;
import com.slash.commands.templates.RunCode;
import com.slash.elements.Player;

/**
 * To enable clickEvent to run some code.
 * @author fihgu
 */
public class Run extends Command
{

	public static HashMap<Double,RunCode> map = new HashMap<Double,RunCode>();
	
	@Override
	public String getName()
	{
		return "run";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "this is an useless command. :)";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		if(args.length <= 0)
		{
			sender.sendChatMessage(new ChatText("fihgu hates running for nothing."));
			return;
		}
		try
		{
			Double id = Double.parseDouble(args[0]);
			
			if(!map.containsKey(id))
			{
				sender.sendChatMessage(new ChatText("fihgu hates running for everything."));
				return;
			}
			RunCode code = map.get(id);
			code.run(sender);
		}
		catch(NumberFormatException e)
		{
			sender.sendChatMessage(new ChatText("Error! keep messing with me, I shall burn your cpu!"));
			return;
		}
		
		
		
	}
	
	public static Double getUniqueCodeID()
	{
		Double temp = 0.5;
		
		while(map.keySet().contains(temp))
			temp = Math.random();
		
		return temp;
	}

	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
		
	}
	
}
