package com.slash.chats.templates;

import com.slash.io.Language;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class Tooltip extends ChatText
{
	public Tooltip(String name, String tooltip)
	{
		super("[" + Language.Translate(name) + "]");
		this.rawText = name;
		ChatStyle style = new ChatStyle();
		style.setColor(EnumChatFormatting.BLUE);
		style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ItemTooltip(tooltip)));
		this.setChatStyle(style);
	}
}
