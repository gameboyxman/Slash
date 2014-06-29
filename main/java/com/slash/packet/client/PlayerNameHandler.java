package com.slash.packet.client;

import com.slash.elements.Area;
import com.slash.fml.SlashMod;
import com.slash.tools.Graphics;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PlayerNameHandler implements IMessageHandler<PlayerNamePacket,IMessage>
{
	@Override
	public IMessage onMessage(PlayerNamePacket message, MessageContext ctx)
	{		
		SlashMod.thePlayerName = message.data;
		return null;
	}
}
