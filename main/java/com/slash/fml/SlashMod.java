package com.slash.fml;


import com.google.common.eventbus.Subscribe;
import com.slash.commands.*;
import com.slash.group.Group;
import com.slash.io.Language;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;


@Mod(modid = SlashMod.MODID, version = SlashMod.VERSION)
public class SlashMod
{
	public static final String	MODID	= "Slash";
	public static final String	VERSION	= "b2";

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

	}

	@EventHandler
	public void onServerStarted(FMLServerStartingEvent e)
	{
		registerCommands(e);
		Language.instance.load();
		Group.init();
		FMLCommonHandler.instance().bus().register(new SlashEventHandler());
	}
	
	@EventHandler
	public void onServerStopped(FMLServerStoppingEvent e)
	{
		Language.instance.save();
	}

	private void registerCommands(FMLServerStartingEvent e)
	{
		new Slash().register(e);
		new Home().register(e);
		new Test().register(e);
		new Link().register(e);
		new Run().register(e);
		new SetHome().register(e);
		new SetWarp().register(e);
		new Warp().register(e);
		new DelWarp().register(e);
		new Top().register(e);
	}
}