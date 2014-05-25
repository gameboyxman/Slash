package com.slash.asm;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import com.slash.asm.templates.MethodPatch;
import com.slash.commands.Test;
import net.minecraft.launchwrapper.IClassTransformer;


public class ClassTransformer implements IClassTransformer
{
	public static ArrayList<MethodPatch>	list	= new ArrayList<MethodPatch>();
	static
	{

	}

	public static String getobfuscatedClassName(String deobfuscatedName)
	{
		try
		{
			URL path = Test.class.getClassLoader().getResource("Map/joined.srg");
			BufferedReader in = new BufferedReader(new InputStreamReader(path.openStream()));
			
			String name = deobfuscatedName.replace(".", "/");
			while(in.ready())
			{
				String[] part = in.readLine().split(" ");
				if(part.length > 2 && part[0].equalsIgnoreCase("CL:"))
				{
					if(part[2].equalsIgnoreCase(name))
						return part[1];
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		byte[] byteCode = basicClass;
		return byteCode;
	}

}
