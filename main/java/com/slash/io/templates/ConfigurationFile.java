package com.slash.io.templates;

import java.util.HashMap;

public abstract class ConfigurationFile extends SaveFile
{
	public HashMap<String,String> map = new HashMap<String,String>();
	
	@Override
	public void save()
	{
		for(String key : map.keySet())
		{
			list.add(key);
			list.add(map.get(key));
		}
		super.save();
	}
	
	@Override
	public void load()
	{
		super.load();
		map = new HashMap<String,String>();
		
		for(int i = 0; i < list.size(); i = i + 2)
		{
			map.put(list.get(i), list.get(i+1));
		}
	}
}