package com.slash.asm;

import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformer implements IClassTransformer
{

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		byte[] byteCode = basicClass;
		//System.out.println("test!");
		return byteCode;
	}
}
