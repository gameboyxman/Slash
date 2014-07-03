package com.slash.elements;

import java.io.Serializable;
import java.text.NumberFormat;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.world.World;

import com.slash.chats.styles.LocationStyle;
import com.slash.chats.templates.ChatText;
import com.slash.tools.McColor;
import com.slash.tools.Server;

public class Location implements Serializable
{
	public int dimension;
	public double x,y,height;
	
	public Location(Player player)
	{
		Location temp = player.getLocation();
		init(temp.x,temp.y,temp.height,temp.dimension);
	}
	
	public Location(EntityPlayer player)
	{
		init(player.posX, player.posZ, player.posY,player.dimension);
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
	
	/**
	 * you may rebuild this location object with this string as argument.
	 */
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
	
	/**
	 * click on this chatCompound will teleport this player to this location.
	 * @return
	 */
	public ChatText toFancyString()
	{
		ChatText temp = new ChatText("[(" + x + ", " + y + ", " + height + ") " + dimension + "]");
		temp.setChatStyle(new LocationStyle(this));
		return temp;
	}
	
	public int getHighestBlock()
	{
		
		World world = Server.getWorld(dimension);
		int highest = world.getHeight();
		
		while(world.getBlock((int)this.x, highest, (int)this.y).getMaterial() == Material.air)
			highest--;
		
		return highest;
	}
}
