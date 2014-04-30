package com.slash.chats.templates;

import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class Tooltip extends ChatText
{
	public Tooltip(String name, String tooltip)
	{
		this.text = "[" + name + "]";
		ChatStyle style = new ChatStyle();
		style.setColor(EnumChatFormatting.BLUE);
		style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ItemTooltip(tooltip)));
		this.setChatStyle(style);
	}
}
