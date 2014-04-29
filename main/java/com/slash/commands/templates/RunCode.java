package com.slash.commands.templates;

import com.slash.elements.Location;
import com.slash.elements.Player;
import com.slash.tools.Teleport;

public class RunCode
{
	public String action;
	public Object[] args;
	
	
	public RunCode(String action, Object[] args)
	{
		this.action = action;
		this.args = args;
	}
	
	public void run(Player sender)
	{
		if(action.equals("warp"))
		{
			Location loc = (Location) args[0];
			Teleport.Warp(sender, loc);
		}
	}
}
