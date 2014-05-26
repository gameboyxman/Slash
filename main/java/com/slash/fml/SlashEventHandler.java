package com.slash.fml;


import net.minecraft.util.ChatStyle;
import com.slash.chats.styles.GroupText;
import com.slash.chats.templates.ChatText;
import com.slash.elements.Player;
import com.slash.events.PlayerLoggingInEvent;
import com.slash.group.Group;
import com.slash.tools.McColor;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.MouseInputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;


public class SlashEventHandler
{
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
		e.setCanceled(true);
	}
}
