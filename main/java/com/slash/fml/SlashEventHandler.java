package com.slash.fml;


import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;

import com.slash.chats.styles.ClaimedAreaStyle;
import com.slash.chats.styles.WarningStyle;
import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.GroupText;
import com.slash.chats.templates.Tooltip;
import com.slash.commands.Login;
import com.slash.elements.Area;
import com.slash.elements.ClaimedArea;
import com.slash.elements.ComplexArea;
import com.slash.elements.Location;
import com.slash.elements.Player;
import com.slash.events.PlayerLoggingInEvent;
import com.slash.events.PlayerPlaceBlockEvent;
import com.slash.events.PlayerUseBlockEvent;
import com.slash.group.Group;
import com.slash.packet.client.SelectionBoxPacket;
import com.slash.tools.Graphics;
import com.slash.tools.McColor;
import com.slash.tools.Protection;
import com.slash.tools.Protection.Access;
import com.slash.tools.Server;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.MouseInputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;


public class SlashEventHandler
{
	public static HashMap<Player,ComplexArea> selectionMap = new HashMap<Player,ComplexArea>();
	
	/**
	 * default group settings.
	 */
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent e)
	{
		Player player = new Player(e.player.getCommandSenderName());	
		//deselect
		SlashMod.channel.sendTo(new SelectionBoxPacket(null), player.entityPlayerMP);

		/**
		 *if player isn't in a group
		 */
		if (Group.getGroups(player).size() <= 0)
		{
			Group defaultGroup = Group.getDefaultGroup();
			defaultGroup.addPlayer(player).save();
			player.sendChatMessage(new ChatText(McColor.darkGrey + "You have been put into ").appendSibling(new GroupText(defaultGroup.name)
					.appendSibling(new ChatText(McColor.darkGrey + " group by default.").setChatStyle(new ChatStyle()))));
		}
	}
	
	/**
	 * Login lock up
	 */
	@SubscribeEvent
	public void onPlayerLoggingIn(PlayerLoggingInEvent e)
	{
		EntityPlayerMP entityPlayerMP = e.entityPlayerMP;
		Player player = new Player(entityPlayerMP);
		player.profile.load();
		
		if(player.profile.ip == null || entityPlayerMP.getPlayerIP() == null || !entityPlayerMP.getPlayerIP().equals(player.profile.ip))
		{
			e.setCanceled(true);
			player.sendChatMessage(new ChatText("Please reigster or login. ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new Tooltip("help","Commands:\n/register [password] [repeat password]\n/login [password]")));
			if(!Login.playerWaitingToLogin.contains(player))
				Login.playerWaitingToLogin.add(player);
		}
	}
	
	/**
	 * selecting area
	 */
	@SubscribeEvent
	public void onPlayerInteractBlockEvent(PlayerInteractEvent e)
	{
		
		if(e.action == Action.LEFT_CLICK_BLOCK)
		{
			//test
			//e.entityPlayer.addChatMessage(new ChatText(e.entityPlayer.getCommandSenderName() + "x: " + e.x + ", y: " + e.z + ", height: " + e.y));			
			Player player = new Player(e.entityPlayer);
			
			//System.out.println(selectionMap.toString());
			if(selectionMap.containsKey(player) && !selectionMap.get(player).list.get(selectionMap.get(player).list.size() - 1).defined)
			{
				e.setCanceled(true);
				Location blockLocation = new Location(e.x,e.z,e.y,e.entityPlayer.dimension);
				ComplexArea areas = selectionMap.get(player);
				Area temp = areas.list.get(areas.list.size()-1);
				ChatText reply = new ChatText();
				reply.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA));
				if(temp != null)
				{
					temp.addVertex(blockLocation);
					reply.appendText("vertex" + temp.vertices.size() + " has been set to : ");
					reply.appendSibling(blockLocation.toFancyString());
					if(temp.defined)
					{
						
						SlashMod.channel.sendTo(new SelectionBoxPacket(temp), player.entityPlayerMP);
						reply.appendText(", Area has been defined.");
					}
					player.sendChatMessage(reply);
				}
				else
				{
					System.err.println("nullpointer during area selection.");
				}
			}
		}
	}
	
	/**
	 *Use Block access check
	 */
	@SubscribeEvent
	public void onPlayerOpenChest(PlayerUseBlockEvent e)
	{
		Location location = e.blockLocation;
		Block block = Server.getWorld(location.dimension).getBlock((int)location.x, (int)location.height, (int)location.y);
		
		for(ClaimedArea area : Protection.instance.areas)
		{
			if(area.isInside(location))
			{
				if(block instanceof BlockContainer && !area.checkAcess(new Player(e.entityPlayer), Access.Open_Chest))
				{
					IChatComponent warning = new ChatText("You don't have access to open chests in ").setChatStyle(new WarningStyle());
					warning.appendSibling(new ChatText("[" + area.name + "]").setChatStyle(new ClaimedAreaStyle(area)));
					e.entityPlayer.addChatComponentMessage(warning);
					e.setCanceled(true);
				}
				
				if((block instanceof BlockFenceGate || block instanceof BlockDoor || block instanceof BlockTrapDoor) && !area.checkAcess(new Player(e.entityPlayer), Access.Use_Gate))	
				{
					IChatComponent warning = new ChatText("You don't have access to open doors in ").setChatStyle(new WarningStyle());
					warning.appendSibling(new ChatText("[" + area.name + "]").setChatStyle(new ClaimedAreaStyle(area)));
					e.entityPlayer.addChatComponentMessage(warning);
					e.setCanceled(true);
				}
				
				if((block instanceof BlockButton|| block instanceof BlockLever) && !area.checkAcess(new Player(e.entityPlayer), Access.Use_Switch))
				{
					IChatComponent warning = new ChatText("You don't have access to use switches in ").setChatStyle(new WarningStyle());
					warning.appendSibling(new ChatText("[" + area.name + "]").setChatStyle(new ClaimedAreaStyle(area)));
					e.entityPlayer.addChatComponentMessage(warning);
					e.setCanceled(true);
				}
			}
		}
	}
	
	/**
	 *Use Block access check
	 */
	@SubscribeEvent
	public void onPlayerPlaceBlock(PlayerPlaceBlockEvent e)
	{
		Location location = e.blockLocation;
		Block block = Server.getWorld(location.dimension).getBlock((int)location.x, (int)location.height, (int)location.y);
		
		for(ClaimedArea area : Protection.instance.areas)
		{
			if(area.isInside(location) && !area.checkAcess(new Player(e.entityPlayer), Access.Edit_Block))
			{	
				IChatComponent warning = new ChatText("You don't have access to place block in ").setChatStyle(new WarningStyle());
				warning.appendSibling(new ChatText("[" + area.name + "]").setChatStyle(new ClaimedAreaStyle(area)));
				e.entityPlayer.addChatComponentMessage(warning);
				e.setCanceled(true);
			}
		}
	}
	
	
	/**
	 *Dig Block access check
	 */
	@SubscribeEvent
	public void onPlayerDigBlock(PlayerInteractEvent e)
	{
		if(e.action == Action.LEFT_CLICK_BLOCK)
		{
			Location location = new Location(e.x,e.z,e.y,e.entityPlayer.dimension);
			
			for(ClaimedArea area : Protection.instance.areas)
			{
				if(area.isInside(location) && !area.checkAcess(new Player(e.entityPlayer), Access.Edit_Block))
				{	
					IChatComponent warning = new ChatText("You don't have access to break block in ").setChatStyle(new WarningStyle());
					warning.appendSibling(new ChatText("[" + area.name + "]").setChatStyle(new ClaimedAreaStyle(area)));
					e.entityPlayer.addChatComponentMessage(warning);
					e.setCanceled(true);
				}
			}
		}
	}
	
	
	
}
