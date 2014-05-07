package com.slash.io;

import com.slash.io.templates.SaveFile;

public class GroupFile extends SaveFile
{
	String groupName = "";
	
	public GroupFile(String name)
	{
		groupName = name;
	}
	
	@Override
	public String getName()
	{
		return "Players.txt";
	}

	@Override
	public String getDir()
	{
		return "./slash/group/" + groupName + "/";
	}
	
}
