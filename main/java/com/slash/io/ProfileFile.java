package com.slash.io;

import com.slash.elements.Location;
import com.slash.io.templates.ConfigurationFile;

public class ProfileFile extends ConfigurationFile
{
	public String playerName = "";
	
	public Location home = null;
	
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
		
		//re-init
		home = null;
		
		//home
		String homeInfo = map.get("home");
		if(homeInfo != null)
			home = new Location(homeInfo);
	}
	
	@Override
	public void save()
	{
		map.clear();
		map.put("home", home.toString());
		
		super.save();
	}
}
