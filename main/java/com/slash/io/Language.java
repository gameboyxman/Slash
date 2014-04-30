package com.slash.io;

import com.slash.io.templates.ConfigurationFile;

public class Language extends ConfigurationFile
{
	static public Language instance = new Language();
	
	@Override
	public String getName()
	{
		return "Language.txt";
	}

	@Override
	public String getDir()
	{
		return "./slash/";
	}
	
	public static String Translate(String line)
	{
		if(!instance.map.containsKey(line))
			instance.map.put(line, line);
		return instance.map.get(line);
	}
}
