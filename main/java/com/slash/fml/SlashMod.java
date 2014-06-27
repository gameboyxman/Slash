package com.slash.fml;


import java.util.Arrays;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.slash.commands.*;
import com.slash.group.Group;
import com.slash.io.Language;
import com.slash.tools.Graphics;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;


public class SlashMod extends DummyModContainer
{
	public static SlashMod	instance;

	public SlashMod()
	{
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "Slash";
		meta.name = "Slash";
		meta.version = "b3";
		meta.credits = "fihgu";
		meta.authorList = Arrays.asList("fihgu");
		meta.description = "this mod helps you gain better control over your Vanilla/Lan server.";
		meta.url = "https://github.com/fihgus/slash";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
		instance = this;
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}

	@Subscribe
	public void onServerStarted(FMLServerStartingEvent e)
	{
		System.out.println("Slash is starting...");
		registerCommands(e);
		Language.instance.load();
		Group.init();
		SlashEventHandler slashEventHandler = new SlashEventHandler();
		FMLCommonHandler.instance().bus().register(slashEventHandler);
		MinecraftForge.EVENT_BUS.register(slashEventHandler);
		MinecraftForge.EVENT_BUS.register(new Graphics());
		System.out.println("Slash is ready.");
	}

	@Subscribe
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
		new SetSpawn().register(e);
		new Login().register(e);
		new Regiester().register(e);
		new Select().register(e);
	}

	@Subscribe
	public void modConstruction(FMLConstructionEvent e)
	{

	}

	@Subscribe
	public void preInit(FMLPreInitializationEvent e)
	{

	}

	@Subscribe
	public void init(FMLInitializationEvent e)
	{

	}

	@Subscribe
	public void postInit(FMLPostInitializationEvent e)
	{

	}
}