package com.slash.chats.styles;

import com.slash.chats.templates.ChatText;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class WarningStyle extends ChatStyle
{
	public WarningStyle()
	{
		init();
	}
	
	public WarningStyle(String text)
	{
		init();
		this.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatText(text)));
	}
	
	public void init()
	{
		this.setItalic(true);
		this.setColor(EnumChatFormatting.DARK_RED);
	}
}
