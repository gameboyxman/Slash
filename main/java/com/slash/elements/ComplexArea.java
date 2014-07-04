package com.slash.elements;

import java.io.Serializable;
import java.util.ArrayList;

public class ComplexArea implements Serializable
{
	public String name = "";
	public ArrayList<Area> list = new ArrayList<Area>();
	
	public ComplexArea()
	{
		
	}
	
	public ComplexArea(String name)
	{
		this.name = name;
	}
	
	public boolean isOverlapping(Area area)
	{
		for(Area temp : list)
		{
			if(area.isOverlapping(temp))
				return true;
		}
		
		return false;
	}
	
	public boolean isOverlapping(ClaimedArea area)
	{
		for(Area temp : list)
		{
			if(area.isOverlapping(temp))
				return true;
		}
		
		return false;
	}
	
	public boolean isInside(Location location)
	{
		for(Area temp : list)
		{
			if(temp.isInside(location))
				return true;
		}
		return false;
	}
}
