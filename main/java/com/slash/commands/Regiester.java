package com.slash.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;
import com.slash.chats.templates.Tooltip;
import com.slash.commands.templates.Command;
import com.slash.elements.Player;
import com.slash.tools.McColor;

public class Regiester extends Command
{

	@Override
	public String getName()
	{
		return "register";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return " [password] [re-enter password] register your character to this server.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		if(args.length == 2)
		{
			if(args[0].equals(args[1]))
			{
				sender.profile.load();
				if(sender.profile.password == null)
				{
					sender.profile.password = args[0];
					sender.profile.save();
					
					sender.sendChatMessage(McColor.green + "Welcome to minecraft, " + sender.name + ".");
					Login.loginPlayer(sender.entityPlayerMP);
				}
				else
				{
					sender.sendChatMessage(McColor.darkRed + "This user name have already been registered!");
				}
			}
			else
			{
				sender.sendChatMessage(McColor.darkRed + "two passwords aren't matching :(");
			}
		}
		else
		{
			sender.sendChatMessage(McColor.darkRed + "Argument mismatch.");
		}
	}

	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
		
	}

	@Override
	public IChatComponent getFancyUsage()
	{
		return new Tooltip("Change password","to change someone's password,\nSimply go to their pofile.txt file,\nand remove the line with password.");
	}

}
