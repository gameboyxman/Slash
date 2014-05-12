package com.slash.io;

import com.slash.io.templates.ConfigurationFile;

public class GroupSettingFile extends ConfigurationFile
{
	public static GroupSettingFile instance = new GroupSettingFile();
	static
	{
		instance.load();
	}
	
	public static String getDefaultGroup()
	{
		return instance.get("Default group", "players");
	}
	
	@Override
	public String getName()
	{
		return "setting.txt";
	}

	@Override
	public String getDir()
	{
		return "./slash/group/";
	}
}
