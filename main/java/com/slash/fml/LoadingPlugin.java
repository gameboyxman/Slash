package com.slash.fml;

import java.util.Map;
import com.slash.asm.ClassTransformer;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class LoadingPlugin implements IFMLLoadingPlugin
{

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[]{ClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass()
	{
		return SlashMod.class.getName();
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		//nty
	}

	@Override
	public String getAccessTransformerClass()
	{
		//nty
		return null;
	}

}
