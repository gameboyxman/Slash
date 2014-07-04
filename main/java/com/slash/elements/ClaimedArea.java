package com.slash.elements;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.slash.chats.styles.ClaimedAreaStyle;
import com.slash.chats.templates.ChatText;
import com.slash.chats.templates.Tooltip;
import com.slash.tools.Protection;
import com.slash.tools.Protection.Access;

public class ClaimedArea extends ComplexArea implements Serializable
{
	public String owner = null;
	public HashMap<String,List<Protection.Access>> share = new HashMap<String,List<Protection.Access>>();
	
	public ClaimedArea(ComplexArea area)
	{
		if(area.name != null)
			this.name = area.name;
		
		this.list = area.list;
	}
	
	public boolean checkAcess(Player player, Protection.Access acess)
	{
		if(player.equals(owner))
			return true;
		
		List<Protection.Access> list = share.get(player.name);
		if(list.contains(acess))
			return true;
		
		return false;
	}
	
	public ChatText toFancyString()
	{
		ChatText text = new ChatText("[" + this.name + "]");
		text.setChatStyle(new ClaimedAreaStyle(this));
		return text;
	}
}
