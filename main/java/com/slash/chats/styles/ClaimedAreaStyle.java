package com.slash.chats.styles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.ItemTooltip;
import com.slash.commands.Run;
import com.slash.commands.Select;
import com.slash.commands.templates.RunCode;
import com.slash.elements.ClaimedArea;
import com.slash.elements.ComplexArea;
import com.slash.elements.Location;
import com.slash.elements.Player;
import com.slash.tools.Teleport;
import com.slash.tools.Protection.Access;

public class ClaimedAreaStyle extends ChatStyle
{
	public ClaimedAreaStyle(ClaimedArea area)
	{
		ArrayList list = new ArrayList();
		list.add(area);
		
		RunCode runCode = new RunCode(list)
		{
			@Override
			public void run(Player clicker)
			{				
				IChatComponent reply = new ChatText("Cannot find that area.").setChatStyle(new WarningStyle());
				
				if(Select.selectArea(clicker, ((ClaimedArea)args.get(0)).name))
					reply = new ChatText("You have selected " + ((ClaimedArea)args.get(0)).name + ".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));
					
				clicker.sendChatMessage(reply);
			}
		};
		
		String tooltip ="name: " + area.name + "\nowner: " + area.owner + "\n\nshared with: ";
		
		for(String resident : area.share.keySet())
		{
			tooltip+="\n" + resident + ": ";
			List<Access> accessList = area.share.get(resident);
			for(Access access : accessList)
			{
				tooltip+=access.toString();
			}
		}
		
		Double id = Run.getUniqueCodeID();
		Run.map.put(id, runCode);
		this.setItalic(true);
		this.setColor(EnumChatFormatting.LIGHT_PURPLE);		
		HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ItemTooltip(tooltip));
		ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/run " + id);
		this.setChatHoverEvent(hover);
		this.setChatClickEvent(click);
		
	}
}
