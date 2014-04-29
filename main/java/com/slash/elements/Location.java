package com.slash.elements;

import java.text.NumberFormat;

import com.slash.chats.styles.LocationStyle;
import com.slash.chats.templates.ChatText;
import com.slash.tools.McColor;

public class Location 
{
	public int dimension;
	public double x,y,height;
	
	public Location(Player player)
	{
		Location temp = player.getLocation();
		init(temp.x,temp.y,temp.height,temp.dimension);
	}
	public Location(double x, double y, double height, int dimension)
	{
		init(x,y,height,dimension);
	}
	public Location(double x, double y, double height)
	{
		init(x,y,height,0);
	}
	
	public void init(double x, double y, double height, int dimension)
	{
		java.text.DecimalFormat nf = new java.text.DecimalFormat("#.00");
		
		this.x = Double.parseDouble(nf.format(x));
		this.y = Double.parseDouble(nf.format(y));
		this.height = Double.parseDouble(nf.format(height));
		this.dimension = dimension;
	}	
	
	@Override
	public String toString()
	{
		return x + "," + y + "," + height + "," + dimension;
	}
	
	public Location(String info)
	{
		String[] split = info.split(",");
		this.x = Double.parseDouble(split[0]);
		this.y = Double.parseDouble(split[1]);
		this.height = Double.parseDouble(split[2]);
		this.dimension = Integer.parseInt(split[3]);
	}
	
	public ChatText toFancyString()
	{
		ChatText temp = new ChatText("[(" + x + "," + y + "," + height + ")" + dimension + "]");
		temp.setChatStyle(new LocationStyle(this));
		return temp;
	}
}
