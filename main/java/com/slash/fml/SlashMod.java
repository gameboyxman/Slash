package com.slash.fml;


import com.google.common.eventbus.Subscribe;
import com.slash.commands.*;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;


@Mod(modid = SlashMod.MODID, version = SlashMod.VERSION)
public class SlashMod
{
	public static final String	MODID	= "Slash";
	public static final String	VERSION	= "1.0.0";

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

	}

	@EventHandler
	public void onServerStarted(FMLServerStartingEvent e)
	{
		registerCommands(e);
	}

	private void registerCommands(FMLServerStartingEvent e)
	{
		new Home().register(e);
		new Test().register(e);
		new Link().register(e);
	}
}