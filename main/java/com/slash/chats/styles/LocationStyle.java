package com.slash.chats.styles;

import java.util.ArrayList;
import com.slash.chats.templates.ChatText;
import com.slash.commands.Run;
import com.slash.commands.templates.RunCode;
import com.slash.elements.Location;
import com.slash.elements.Player;
import com.slash.tools.Teleport;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class LocationStyle extends ChatStyle
{
	public LocationStyle(Location loc)
	{		
		ArrayList list = new ArrayList();
		list.add(loc);
		
		RunCode runCode = new RunCode(list)
		{
			@Override
			public void run(Player clicker)
			{
				Teleport.Warp(clicker, (Location) args.get(0));
			}
		};
		
		Double id = Run.getUniqueCodeID();
		Run.map.put(id, runCode);
		this.setItalic(true);
		this.setColor(EnumChatFormatting.AQUA);		
		HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatText("click to warp"));
		ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/run " + id);
		this.setChatHoverEvent(hover);
		this.setChatClickEvent(click);
	}

}
