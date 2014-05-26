package com.slash.asm;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import scala.tools.asm.ClassReader;
import scala.tools.asm.ClassWriter;
import scala.tools.asm.tree.ClassNode;
import scala.tools.asm.tree.MethodNode;
import com.slash.asm.containers.FServerConfigurationManager;
import com.slash.asm.templates.MethodInfo;
import com.slash.asm.templates.MethodPatch;
import com.slash.commands.Test;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;


/**
 * this transformer replaces a method in minecraft base class with one from a
 * container class.
 * 
 * @author fihgu
 * 
 */
public class ClassTransformer implements IClassTransformer
{
	
	public final static boolean debug = false;
	
	public static ArrayList<MethodPatch>	list	= new ArrayList<MethodPatch>();
	static
	{
		MethodPatch temp = new MethodPatch();
		temp.className = "net.minecraft.server.management.ServerConfigurationManager";
		temp.methods.add(new MethodInfo("initializeConnectionToPlayer","(Lnet/minecraft/network/NetworkManager;Lnet/minecraft/entity/player/EntityPlayerMP;Lnet/minecraft/network/NetHandlerPlayServer;)V"));
		                                                              //(Lnet/minecraft/network/NetworkManager;Lnet/minecraft/entity/player/EntityPlayerMP;Lnet/minecraft/network/NetHandlerPlayServer;)V
		temp.containerClass = "com.slash.asm.containers.FServerConfigurationManager";
		list.add(temp);
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		
		byte[] byteCode = basicClass;
		
		try
		{
			
			MethodPatch methodPatch = getMethodPatch(name);
			if (methodPatch != null)
			{
				if(debug)
					System.out.println("Slash is editing class: " + name + ".");
				
				// the class that needs to be patched.
				ClassNode targetCn = new ClassNode();
				ClassReader targetCr = new ClassReader(byteCode);
				targetCr.accept(targetCn, 0);
				
				ClassNode containerCn = new ClassNode();
				ClassReader containerCr = new ClassReader(ClassTransformer.class.getResourceAsStream("/" + methodPatch.containerClass.replace(".", "/") + ".class"));
				
				containerCr.accept(containerCn, 0);

				for (MethodInfo methodInfo : methodPatch.methods)
				{
					MethodNode targetMn = getMethod(targetCn,methodInfo);
					MethodNode containerMn = getMethod(containerCn,methodInfo);	
					targetMn.instructions = containerMn.instructions;
					
					if(debug)
					{
						System.out.println("target: " + targetMn.toString());
						System.out.println("container: " + containerMn.toString());
						System.out.println("Slash has edited method: " + methodInfo.name + methodInfo.desc + ".");
					}
				}

				// return the modified bytes
				ClassWriter targetCw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				targetCn.accept(targetCw);
				byteCode = targetCw.toByteArray();
				
				if(debug)
					System.out.println("Slash has edited class: " + name + ".");
			}
		}
		catch (Exception e)
		{
			System.err.println("都怪时臣,minecraft又爆了!");
			e.printStackTrace();
		}
		return byteCode;
	}

	public MethodPatch getMethodPatch(String name)
	{
		for (MethodPatch temp : list)
		{
			if (name.equals(temp.className) || name.equals(getobfuscatedClassName(temp.className)))
				return temp;
		}
		return null;
	}

	public static String getobfuscatedClassName(String name)
	{
		try
		{
			URL path = ClassTransformer.class.getClassLoader().getResource("Map/joined.srg");
			BufferedReader in = new BufferedReader(new InputStreamReader(path.openStream()));

			String tempName = name.replace(".", "/");
			while (in.ready())
			{
				String[] part = in.readLine().split(" ");
				if (part.length > 2 && part[0].equalsIgnoreCase("CL:"))
				{
					if (part[2].equalsIgnoreCase(tempName))
						return part[1];
				}
			}
		}
		catch (IOException e)
		{
			System.err.println("都怪时臣,minecraft又爆了!");
			e.printStackTrace();
		}
		return name;
	}

	public static MethodNode getMethod(ClassNode cn, MethodInfo methodInfo)
	{
		for (MethodNode method : cn.methods)
		{
			boolean found = (methodInfo.name.equals(method.name) || methodInfo.getobfuscatedName().endsWith(method.name)) && (methodInfo.desc.equals(method.desc) || methodInfo.getobfuscatedDesc().equals(method.desc));
			if(debug)
			{
				System.out.println("method.name: " + method.name);
				System.out.println("method.desc: " + method.desc);
				System.out.println("obfuscated name: " + methodInfo.getobfuscatedName());
				System.out.println("obfuscated desc: " + methodInfo.getobfuscatedDesc());
				System.out.println("found: " + found + "\n");
			}
			if(found)
			{
				return method;
			}
		}
		return null;
	}
}
