package com.slash.io.templates;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class SaveFile
{
	public boolean readable;
	public ArrayList<String> list = new ArrayList<String>();
	
	public abstract String getName();
	public abstract String getDir();
	
	@SuppressWarnings("resource")
	public void save()
	{
		try
		{
			File file = new File(this.getDir() + this.getName());
			if(!file.exists())
				file.createNewFile();
			
			PrintWriter pw = new PrintWriter(file);
			
			for(String line : list)
			{
				pw.write(line);
			}
			
			pw.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void load()
	{
		list = new ArrayList<String>();
		Scanner scan = new Scanner(this.getDir() + this.getName());
		if(scan.hasNext())
		{
			list.add(scan.nextLine());
		}
	}
}
