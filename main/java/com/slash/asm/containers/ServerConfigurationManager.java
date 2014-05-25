package com.slash.asm.containers;

import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.common.base.Charsets;
import com.slash.asm.templates.MethodPatch;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldServer;

public abstract class ServerConfigurationManager extends net.minecraft.server.management.ServerConfigurationManager implements MethodPatch
{
	@Override
	public String[] getMethodList()
	{
		return new String[]{
				"ServerConfigurationManager(MinecraftServer minecraftServer)"
		};
	}
	
	@Override
	public String getDeobfuscatedName()
	{
		return "net.minecraft.server.management.ServerConfigurationManager";
	}
	
	
	
	private static final Logger logger = LogManager.getLogger();
	public ServerConfigurationManager(MinecraftServer minecraftServer)
	{
		super(minecraftServer);
	}

	@Override
	public void initializeConnectionToPlayer(NetworkManager par1INetworkManager, EntityPlayerMP par2EntityPlayerMP, NetHandlerPlayServer nethandlerplayserver)
    {
		NBTTagCompound nbttagcompound = super.readPlayerDataFromFile(par2EntityPlayerMP);
        par2EntityPlayerMP.setWorld(super.getServerInstance().worldServerForDimension(par2EntityPlayerMP.dimension));
        par2EntityPlayerMP.theItemInWorldManager.setWorld((WorldServer)par2EntityPlayerMP.worldObj);
        String s = "local";

        if (par1INetworkManager.getSocketAddress() != null)
        {
            s = par1INetworkManager.getSocketAddress().toString();
        }

        logger.info(par2EntityPlayerMP.getCommandSenderName() + "[" + s + "] logged in with entity id " + par2EntityPlayerMP.getEntityId() + " at (" + par2EntityPlayerMP.posX + ", " + par2EntityPlayerMP.posY + ", " + par2EntityPlayerMP.posZ + ")");
        WorldServer worldserver = super.getServerInstance().worldServerForDimension(par2EntityPlayerMP.dimension);
        ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();
        
        //super.func_72381_a(par2EntityPlayerMP, (EntityPlayerMP)null, worldserver);
        par2EntityPlayerMP.theItemInWorldManager.initializeGameType(worldserver.getWorldInfo().getGameType());
        /////////
        
        par2EntityPlayerMP.playerNetServerHandler = nethandlerplayserver;
        nethandlerplayserver.sendPacket(new S01PacketJoinGame(par2EntityPlayerMP.getEntityId(), par2EntityPlayerMP.theItemInWorldManager.getGameType(), worldserver.getWorldInfo().isHardcoreModeEnabled(), worldserver.provider.dimensionId, worldserver.difficultySetting, super.getMaxPlayers(), worldserver.getWorldInfo().getTerrainType()));
        nethandlerplayserver.sendPacket(new S3FPacketCustomPayload("MC|Brand", super.getServerInstance().getServerModName().getBytes(Charsets.UTF_8)));
        nethandlerplayserver.sendPacket(new S05PacketSpawnPosition(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ));
        nethandlerplayserver.sendPacket(new S39PacketPlayerAbilities(par2EntityPlayerMP.capabilities));
        nethandlerplayserver.sendPacket(new S09PacketHeldItemChange(par2EntityPlayerMP.inventory.currentItem));
        par2EntityPlayerMP.func_147099_x().func_150877_d();
        par2EntityPlayerMP.func_147099_x().func_150884_b(par2EntityPlayerMP);
        super.func_96456_a((ServerScoreboard)worldserver.getScoreboard(), par2EntityPlayerMP);
        super.getServerInstance().func_147132_au();
        ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined", new Object[] {par2EntityPlayerMP.func_145748_c_()});
        chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        super.sendChatMsg(chatcomponenttranslation);
        super.playerLoggedIn(par2EntityPlayerMP);
        nethandlerplayserver.setPlayerLocation(par2EntityPlayerMP.posX, par2EntityPlayerMP.posY, par2EntityPlayerMP.posZ, par2EntityPlayerMP.rotationYaw, par2EntityPlayerMP.rotationPitch);
        super.updateTimeAndWeatherForPlayer(par2EntityPlayerMP, worldserver);

        if (super.getServerInstance().getTexturePack().length() > 0)
        {
            par2EntityPlayerMP.requestTexturePackLoad(super.getServerInstance().getTexturePack());
        }

        Iterator iterator = par2EntityPlayerMP.getActivePotionEffects().iterator();

        while (iterator.hasNext())
        {
            PotionEffect potioneffect = (PotionEffect)iterator.next();
            nethandlerplayserver.sendPacket(new S1DPacketEntityEffect(par2EntityPlayerMP.getEntityId(), potioneffect));
        }

        par2EntityPlayerMP.addSelfToInternalCraftingInventory();

        FMLCommonHandler.instance().firePlayerLoggedIn(par2EntityPlayerMP);
        if (nbttagcompound != null && nbttagcompound.hasKey("Riding", 10))
        {
            Entity entity = EntityList.createEntityFromNBT(nbttagcompound.getCompoundTag("Riding"), worldserver);

            if (entity != null)
            {
                entity.forceSpawn = true;
                worldserver.spawnEntityInWorld(entity);
                par2EntityPlayerMP.mountEntity(entity);
                entity.forceSpawn = false;
            }
        }
    }
}
