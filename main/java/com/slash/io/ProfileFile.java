package com.slash.io;

import com.slash.elements.Location;
import com.slash.io.templates.ConfigurationFile;

public class ProfileFile extends ConfigurationFile
{
	public String playerName = "";
	
	public String mask = "your eyes lied.";
	
	
	public Location home = null;
	public String password = null;
	public String ip = null;
	
	
	public ProfileFile(String playerName)
	{
		this.playerName = playerName;
	}
	
	@Override
	public String getName()
	{
		return "profile.txt";
	}

	@Override
	public String getDir()
	{
		return "./slash/players/" + playerName + "/";
	}
	
	@Override
	public void load()
	{
		super.load();
		
		//home
		home = null;
		String homeInfo = map.get("home");
		if(homeInfo != null)
			home = new Location(homeInfo);
		
		//password
		String maskedPassword = map.get("password");
		if(maskedPassword != null)
		password = removeMask(maskedPassword);
		
		ip = map.get("ip");
	}
	
	@Override
	public void save()
	{
		map.clear();
		
		if(home != null)
			map.put("home", home.toString());
		if(password != null)
			map.put("password", applyMask(password));
		if(ip != null)
			map.put("ip", ip);
		
		super.save();
	}
	
	public String applyMask(String line)
	{
		String temp = "";
		
		for(int i = 0; i < line.length(); i++)
		{
			int i2 = i%mask.length();
			
			char c1 = line.charAt(i);
			char c2 = mask.charAt(i2);
			
			int v = (((c1 + c2) - 64 ) % 95) + 32;
			
			temp += (char)(v);
		}
		
		return temp;
	}
	
	public String removeMask(String line)
	{
		String temp = "";
		
		for(int i = 0; i < line.length(); i++)
		{
			int i2 = i%mask.length();
			
			char c1 = line.charAt(i);
			char c2 = mask.charAt(i2);
			
			int v = (((c1 - c2) + 95) % 95) + 32;
			
			temp += (char)(v);
		}
		
		return temp;
	}
}
