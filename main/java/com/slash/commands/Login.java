package com.slash.commands;

import java.util.Iterator;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import com.google.common.base.Charsets;
import com.slash.commands.templates.Command;
import com.slash.elements.Player;
import com.slash.tools.Server;
import cpw.mods.fml.common.FMLCommonHandler;

public class Login extends Command
{

	@Override
	public String getName()
	{
		return "login";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "[password] sign in your character.";
	}

	@Override
	public void processPlayer(Player sender, String[] args)
	{
		this.loginPlayer(sender.entityPlayerMP);
		
	}
	
	private void loginPlayer(EntityPlayerMP player)
	{
		System.out.println("LOGIN!");
		
		WorldServer worldserver = Server.getServer().worldServerForDimension(0);
		ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();
		ServerConfigurationManager scm = Server.getServer().getConfigurationManager();
		NetHandlerPlayServer pnsh = player.playerNetServerHandler;
		NBTTagCompound nbttagcompound = scm.readPlayerDataFromFile(player);
		
		pnsh.sendPacket(new S3FPacketCustomPayload("MC|Brand", Server.getServer().getServerModName().getBytes(Charsets.UTF_8)));
		pnsh.sendPacket(new S05PacketSpawnPosition(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ));
		pnsh.sendPacket(new S39PacketPlayerAbilities(player.capabilities));
		pnsh.sendPacket(new S09PacketHeldItemChange(player.inventory.currentItem));
		
		player.func_147099_x().func_150877_d();
		player.func_147099_x().func_150884_b(player);
		//not visible
		//Server.getServer().getConfigurationManager().func_96456_a((ServerScoreboard) worldserver.getScoreboard(), player);
		Server.getServer().func_147132_au();

		ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined",
				new Object[] { player.func_145748_c_() });
		
		chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		scm.sendChatMsg(chatcomponenttranslation);
		
		worldserver.getPlayerManager().removePlayer(player);
		scm.playerEntityList.remove(player);
		scm.playerLoggedIn(player);
		
		pnsh.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw,
				player.rotationPitch);
		scm.updateTimeAndWeatherForPlayer(player, worldserver);
		
		if (Server.getServer().getTexturePack().length() > 0)
		{
			player.requestTexturePackLoad(Server.getServer().getTexturePack());
		}

		Iterator iterator = player.getActivePotionEffects().iterator();

		while (iterator.hasNext())
		{
			PotionEffect potioneffect = (PotionEffect) iterator.next();
			pnsh.sendPacket(new S1DPacketEntityEffect(player.getEntityId(), potioneffect));
		}

		player.addSelfToInternalCraftingInventory();

		FMLCommonHandler.instance().firePlayerLoggedIn(player);
		if (nbttagcompound != null && nbttagcompound.hasKey("Riding", 10))
		{
			Entity entity = EntityList.createEntityFromNBT(nbttagcompound.getCompoundTag("Riding"), worldserver);

			if (entity != null)
			{
				entity.forceSpawn = true;
				worldserver.spawnEntityInWorld(entity);
				player.mountEntity(entity);
				entity.forceSpawn = false;
			}
		}
	}

	@Override
	public void processConsole(ICommandSender console, String[] args)
	{
		
	}

	@Override
	public IChatComponent getFancyUsage()
	{
		return null;
	}

}
