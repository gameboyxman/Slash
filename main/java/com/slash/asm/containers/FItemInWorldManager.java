package com.slash.asm.containers;

import com.slash.elements.Location;
import com.slash.events.PlayerPlaceBlockEvent;
import com.slash.events.PlayerUseBlockEvent;
import com.slash.tools.Server;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class FItemInWorldManager extends ItemInWorldManager
{

	public FItemInWorldManager(World par1World) 
	{
		super(par1World);
	}
	
	@Override
	public boolean activateBlockOrUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        PlayerInteractEvent event = ForgeEventFactory.onPlayerInteract(par1EntityPlayer, Action.RIGHT_CLICK_BLOCK, par4, par5, par6, par7);
        
        
        if (event.isCanceled())
        {
            super.thisPlayerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(par4, par5, par6, super.theWorld));
            return false;
        }

        if (par3ItemStack != null && par3ItemStack.getItem().onItemUseFirst(par3ItemStack, par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10))
        {
            if (par3ItemStack.stackSize <= 0) ForgeEventFactory.onPlayerDestroyItem(super.thisPlayerMP, par3ItemStack);
            return true;
        }
        
        Block block = par2World.getBlock(par4, par5, par6);
        boolean isAir = block.isAir(par2World, par4, par5, par6);
        boolean useBlock = !par1EntityPlayer.isSneaking() || par1EntityPlayer.getHeldItem() == null;
        if (!useBlock) useBlock = par1EntityPlayer.getHeldItem().getItem().doesSneakBypassUse(par2World, par4, par5, par6, par1EntityPlayer);
        boolean result = false;
        
        if (useBlock)
        {
            if (event.useBlock != Event.Result.DENY)
            {
            	PlayerUseBlockEvent e = new PlayerUseBlockEvent(par1EntityPlayer, new Location(par4,par6,par5,super.theWorld.getWorldInfo().getVanillaDimension()));
            	
            	if(Server.getBus().post(e))
            		return false;
            		
            	result = block.onBlockActivated(par2World, par4, par5, par6, par1EntityPlayer, par7, par8, par9, par10);
            }
            else
            {
            	super.thisPlayerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(par4, par5, par6, super.theWorld));
                result = event.useItem != Event.Result.ALLOW;
            }
        }
        

        if (par3ItemStack != null && !result && event.useItem != Event.Result.DENY)
        {
        	PlayerPlaceBlockEvent e = new PlayerPlaceBlockEvent(par1EntityPlayer, new Location(par4,par6,par5,super.theWorld.getWorldInfo().getVanillaDimension()));
          
        	if(Server.getBus().post(e))
        		return false;
        	
        	int meta = par3ItemStack.getItemDamage();
            int size = par3ItemStack.stackSize;
            result = par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
            if (super.isCreative())
            {
                par3ItemStack.setItemDamage(meta);
                par3ItemStack.stackSize = size;
            }
            if (par3ItemStack.stackSize <= 0) ForgeEventFactory.onPlayerDestroyItem(super.thisPlayerMP, par3ItemStack);
        }
		
        return result;
    }
}
