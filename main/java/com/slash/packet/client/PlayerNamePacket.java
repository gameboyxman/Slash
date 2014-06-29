package com.slash.packet.client;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PlayerNamePacket implements IMessage
{

	String data = "";
	
	public PlayerNamePacket()
	{
		
	}
	
	public PlayerNamePacket(String name)
	{
		this.data = name;
	}
	
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

}
