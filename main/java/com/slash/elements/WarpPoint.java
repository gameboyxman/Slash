package com.slash.elements;

import net.minecraft.event.HoverEvent;
import com.slash.chats.styles.LocationStyle;
import com.slash.chats.templates.ChatText;
import com.slash.tools.McColor;

public class WarpPoint
{
	String name = "null";
	Location location = null;
	
	public WarpPoint(String name,Location location)
	{
		this.name = name;
		this.location = location;
	}
	
	public ChatText toFancyString()
	{
		ChatText temp = new ChatText("[" + name + "]");
		temp.setChatStyle(new LocationStyle(this.location).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatText(McColor.aqua + "[(" + location.x + "," + location.y + "," + location.height + ")" + location.dimension + "]"))));
		return temp;
	}
}
