package com.slash.io.templates;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ConfigurationFile extends SaveFile
{
	public HashMap<String,String> map = new HashMap<String,String>();
	
	public String get(String key, String defaultValue)
	{
		if(map.containsKey(key))
			return map.get(key);
		else
		{
			map.put(key, defaultValue);
			return defaultValue;
		}
	}
	
	@Override
	public void save()
	{
		list.clear();
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
