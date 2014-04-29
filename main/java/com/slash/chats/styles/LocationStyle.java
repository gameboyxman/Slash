package com.slash.chats.styles;

import java.util.ArrayList;
import com.slash.chats.templates.ChatText;
import com.slash.commands.Run;
import com.slash.commands.templates.RunCode;
import com.slash.elements.Location;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class LocationStyle extends ChatStyle
{
	public LocationStyle(Location loc)
	{
		Object[] args = new Object[]{loc};
		RunCode runCode = new RunCode("warp",args);
		Double id = Run.getUniqueCodeID();
		Run.map.put(id, runCode);
		this.setBold(true);
		this.setColor(EnumChatFormatting.AQUA);		
		HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatText("click to warp"));
		ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/run " + id);
		this.setChatHoverEvent(hover);
		this.setChatClickEvent(click);
	}

}
