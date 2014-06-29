package com.slash.packet.client;

import com.slash.elements.Area;
import com.slash.elements.CubicArea;
import com.slash.tools.Graphics;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class SelectionBoxPacket implements IMessage
{
	public SelectionBoxPacket()
	{
		
	}
	
	public SelectionBoxPacket(Area area)
	{
		if(area !=null)
			this.data = area.toString();
	}
	
	String data = "";
	@Override
	public void fromBytes(ByteBuf buf)
	{
		data = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, data);
	}
	
	public Area getArea()
	{
		if(data.equals(""))
			return null;
		
		Area area = null;
		String shape = data.split(" ")[1];
		
		if(shape.equals("cube"))
		{
			area = new CubicArea(data);
		}
		
		return area;
	}
}
