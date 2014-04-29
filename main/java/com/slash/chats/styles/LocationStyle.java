package com.slash.chats.styles;

import com.slash.chats.templates.ChatText;
import com.slash.elements.Location;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class LocationStyle extends ChatStyle
{
	public LocationStyle(Location loc)
	{
		init();
		HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatText("click to warp"));
		ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/say this is a test!" + loc.toString());
		this.setChatHoverEvent(hover);
		this.setChatClickEvent(click);
	}
	
	public void init()
	{
		this.setBold(true);
		this.setColor(EnumChatFormatting.AQUA);
	}
}
