package com.slash.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.slash.elements.ClaimedArea;
import com.sun.corba.se.impl.orbutil.ObjectWriter;

public class Protection implements Serializable
{
	public static final long serialVersionUID = 19930302L;
	
	public  static Protection instance = new Protection();
	public ArrayList<ClaimedArea> areas = new ArrayList<ClaimedArea>();
	
	public Protection(){}
	
	public boolean contains(String name)
	{	
		return this.get(name) != null;
	}
	
	public ClaimedArea get(String name)
	{
		for(int i = 0; i < areas.size(); i ++)
		{
			ClaimedArea area = areas.get(i);
			if(area.name.equals(name))
				return area;
		}
		
		return null;
	}
	
	public boolean remove(String name)
	{
		for(int i = 0; i < areas.size(); i ++)
		{
			ClaimedArea area = areas.get(i);
			if(area.name.equals(name))
			{
				areas.remove(i);
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("resource")
	public void load()
	{
		try 
		{
			File file = new File("./slash/protection.save");
			if(file.exists())
			{
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
				Protection.instance = (Protection)in.readObject();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void save()
	{
		try 
		{
			File file = new File("./slash/protection.save");
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(Protection.instance);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public enum Access implements Serializable
	{
		Enter,
		Open_Chest,
		Use_Gate,
		Use_Switch,
		Edit_Block,
		PVP
	}
}
