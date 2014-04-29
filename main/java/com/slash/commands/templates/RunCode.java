package com.slash.commands.templates;

import com.slash.elements.Location;

public class RunCode
{
	public String action;
	public Object[] args;
	
	
	public RunCode(String action, Object[] args)
	{
		this.action = action;
		this.args = args;
	}
	
	public void run()
	{
		if(action.equals("warp"))
		{
			Location loc = (Location) args[0];
			
		}
	}
}
