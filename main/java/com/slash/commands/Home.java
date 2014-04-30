package com.slash.commands;

import java.util.Iterator;
import java.util.List;

import com.slash.chats.templates.ChatText;
import com.slash.commands.templates.Command;
import com.slash.elements.Location;
import com.slash.elements.Player;
import com.slash.tools.Server;
import com.slash.tools.Teleport;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class Home extends Command
{	
	@Override
	public String getName()
	{
		return "home";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "send player to his/her home.";
	}
	
	@Override
	public void processPlayer(Player sender, String[] args)
	{
		sender.profile.load();
		Location home = sender.profile.home;
		
		Teleport.Warp(sender, home);
		sender.sendChatMessage("Welcome back," + sender);
		
	}

	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
		console.addChatMessage(new ChatText("go back to your void!"));
		
	}

	@Override
	public IChatComponent getFancyUsage()
	{
		return null;
	}	
}
