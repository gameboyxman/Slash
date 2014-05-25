package com.slash.commands;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Scanner;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import com.slash.asm.ClassTransformer;
import com.slash.chats.styles.WarningStyle;
import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.ItemTooltip;
import com.slash.commands.templates.Command;
import com.slash.elements.Player;
import com.slash.group.Group;
import com.slash.tools.McColor;


public class Test extends Command
{
	
	@Override
	public String getName()
	{
		return "test";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "this is the test command.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		try
		{
		Player player = new Player(sender.name);
		///////////////////Ultimate high blast-proof defensive slashes.///////////////////////
		//High alert, test field: explosion may occur		
			
		sender.sendChatMessage(new ChatText(ClassTransformer.getobfuscatedClassName("net.minecraft.server.management.ServerConfigurationManager")));
		
		///////////////////////////////////////////////////////////////////////////////////////
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
