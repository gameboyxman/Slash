package com.slash.io.templates;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public abstract class SaveFile
{
	public boolean				readable;
	public ArrayList<String>	list	= new ArrayList<String>();

	public abstract String getName();

	public abstract String getDir();

	@SuppressWarnings("resource")
	public void save()
	{
		try
		{
			File dir = new File(this.getDir());

			if (!dir.exists())
				dir.mkdirs();

			File file = new File(this.getDir() + this.getName());
			if (!file.exists())
				file.createNewFile();

			PrintWriter pw = new PrintWriter(file);

			for (String line : list)
			{
				pw.println(line);
			}

			pw.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void load()
	{
		list = new ArrayList<String>();
		File saveFile = new File(this.getDir() + this.getName());
		if (saveFile.exists())
		{
			Scanner scan;
			try
			{
				scan = new Scanner(saveFile);
				while(scan.hasNext())
				{
					list.add(scan.nextLine());
				}
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}

		}
	}
}
