package com.slash.packet.client;

import com.slash.elements.Area;
import com.slash.tools.Graphics;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class SelectionBoxHandler implements IMessageHandler<SelectionBoxPacket,IMessage>
{
	@Override
	public IMessage onMessage(SelectionBoxPacket message, MessageContext ctx)
	{
		
		Area area = message.getArea();		
		if(area != null)
		{
			Graphics.selectedAreas.list.add(area);
		}
		else
		{
			Graphics.selectedAreas.list.clear();
		}
		return null;
	}
}
