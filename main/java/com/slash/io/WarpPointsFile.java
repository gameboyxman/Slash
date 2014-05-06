package com.slash.io;

import java.util.ArrayList;
import com.slash.elements.Location;
import com.slash.elements.WarpPoint;
import com.slash.io.templates.ConfigurationFile;

public class WarpPointsFile extends ConfigurationFile
{
	public String playerName = "null";
	
	public WarpPointsFile(String playerName)
	{
		this.playerName = playerName;
	}
	
	/**
	 * changes made to this arraylist will not effect the save file.
	 * @return
	 */
	public ArrayList<WarpPoint> getAllWarpPoints()
	{
		ArrayList<WarpPoint> temp = new ArrayList<WarpPoint>();
		this.load();
		
		for(String key : map.keySet())
		{
			WarpPoint wp = new WarpPoint(key,new Location(map.get(key)));
			temp.add(wp);
		}
		
		return temp;
	}
	
	@Override
	public String getName()
	{
		return "warpPoints.txt";
	}

	@Override
	public String getDir()
	{
		return "./slash/players/" + this.playerName + "/";
	}
}
