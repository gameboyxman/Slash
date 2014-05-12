package com.slash.chats.styles;

import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.ItemTooltip;
import com.slash.chats.templates.Tooltip;
import com.slash.group.Group;
import com.slash.io.Language;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class GroupText extends ChatText
{
	public GroupText(String name)
	{
		super("<" + name + ">");
		ChatStyle style = new ChatStyle();
		style.setColor(EnumChatFormatting.DARK_PURPLE);
		
		String list = "members:\n";
		Group group = Group.getGroup(name);
		int i = 0;
		for(String member : group.players)
		{
			list += member;
			i++;
			/**
			 * show # of names per line.
			 */
			if(i >= 5)
			{
				i = 0;
				list += "\n";
			}
		}
		
		style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ItemTooltip(list)));
		this.setChatStyle(style);	
	}
}
