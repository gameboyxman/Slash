package com.slash.fml;


import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import com.slash.chats.styles.GroupText;
import com.slash.chats.templates.ChatText;
import com.slash.commands.Login;
import com.slash.elements.Area;
import com.slash.elements.Location;
import com.slash.elements.Player;
import com.slash.events.PlayerLoggingInEvent;
import com.slash.group.Group;
import com.slash.tools.Graphics;
import com.slash.tools.McColor;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.MouseInputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;


public class SlashEventHandler
{
	public static HashMap<Player,Area> selectionMap = new HashMap<Player,Area>();
	
	@SubscribeEvent
	/**
	 * default group settings.
	 */
	public void onPlayerLoggedIn(PlayerLoggedInEvent e)
	{
		Player player = new Player(e.player.getCommandSenderName());

		/**
		 * //if player isn't in a group
		 */
		if (Group.getGroups(player).size() <= 0)
		{
			Group defaultGroup = Group.getDefaultGroup();
			defaultGroup.addPlayer(player).save();
			player.sendChatMessage(new ChatText(McColor.darkGrey + "You have been put into ").appendSibling(new GroupText(defaultGroup.name)
					.appendSibling(new ChatText(McColor.darkGrey + " group by default.").setChatStyle(new ChatStyle()))));
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggingIn(PlayerLoggingInEvent e)
	{
		EntityPlayerMP entityPlayerMP = e.entityPlayerMP;
		Player player = new Player(entityPlayerMP);
		player.profile.load();
		
		if(player.profile.ip == null || entityPlayerMP.getPlayerIP() == null || !entityPlayerMP.getPlayerIP().equals(player.profile.ip))
		{
			e.setCanceled(true);
			if(!Login.playerWaitingToLogin.contains(player))
				Login.playerWaitingToLogin.add(player);
		}
	}
	
	/**
	 * selecting area
	 */
	@SubscribeEvent
	public void playerInteractBlockEvent(PlayerInteractEvent e)
	{
		
		if(e.action == Action.LEFT_CLICK_BLOCK)
		{
			//test
			//e.entityPlayer.addChatMessage(new ChatText(e.entityPlayer.getCommandSenderName() + "x: " + e.x + ", y: " + e.z + ", height: " + e.y));			
			Player player = new Player(e.entityPlayer);
			
			System.out.println(selectionMap.toString());
			if(selectionMap.containsKey(player))
			{
				Location blockLocation = new Location(e.x,e.z,e.y,e.entityPlayer.dimension);
				Area temp = selectionMap.get(player);
				ChatText reply = new ChatText();
				reply.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA));
				if(temp != null)
				{
					temp.addVertex(blockLocation);
					reply.appendText("vertex" + temp.vertices.size() + " has been set to : ");
					reply.appendSibling(blockLocation.toFancyString());
					if(temp.defined)
					{
						reply.appendText(", Area has been defined.");
						this.selectionMap.remove(player);
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
}
