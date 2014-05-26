package com.slash.asm.templates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import com.slash.asm.ClassTransformer;
import com.slash.commands.Test;

public class MethodInfo
{
	public String name = null;
	public String desc = null;
	
	public MethodInfo(String name, String desc)
	{
		this.name = name;
		this.desc = desc;
	}
	
	public String getobfuscatedName()
	{
		try
		{
			URL path = MethodInfo.class.getClassLoader().getResource("Map/methods.csv");
			BufferedReader in = new BufferedReader(new InputStreamReader(path.openStream()));
			
			while(in.ready())
			{
				String[] part = in.readLine().split(",");
				if(part.length > 2)
				{
					if(part[1].equalsIgnoreCase(name))
						return part[0];
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public String getobfuscatedDesc()
	{
		try
		{
			String temp = "";
			
			for(int i = 0; i < desc.length(); i++)
			{				
				if(desc.charAt(i) == 'L')
				{
					String className = desc.substring(i+1);
					int offset = className.indexOf(";");
					if(offset>=0)
					{
						className = className.substring(0,offset);
						i+=offset+1;
						
						//get the obfuscated class name
						temp+=ClassTransformer.getobfuscatedClassName(className);
					}
				}
				else
				{
					temp+=desc.charAt(i);
					continue;
				}
			}
			
			return temp;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
