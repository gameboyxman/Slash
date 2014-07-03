package com.slash.elements;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import org.lwjgl.opengl.GL11;

import com.slash.fml.SlashMod;
import com.slash.tools.Server;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class CuboidArea extends Area
{
	public CuboidArea()
	{
		this.requiredNumberOfVertices = 2;
		this.shape = "cube";
	}

	public CuboidArea(String data)
	{
		super(data);
	}

	public boolean isInside(Location location)
	{
		if (location.dimension != vertices.get(0).dimension)
			return false;

		if (location.height > vertices.get(0).height && location.height > vertices.get(1).height)
			return false;

		if (location.height < vertices.get(0).height && location.height < vertices.get(1).height)
			return false;

		if (location.y > vertices.get(0).y && location.y > vertices.get(1).y)
			return false;

		if (location.x > vertices.get(0).x && location.x > vertices.get(1).x)
			return false;

		if (location.y < vertices.get(0).y && location.y < vertices.get(1).y)
			return false;

		if (location.x < vertices.get(0).x && location.x < vertices.get(1).x)
			return false;

		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void draw(float partialTicks)
	{
		//System.out.println("draw");
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		Location playerLoc = new Location(player);

		// in different dimension, abort drawing.
		if (playerLoc.dimension != vertices.get(0).dimension)
			return;

		GL11.glPushMatrix();

		double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
		double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
		double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
		GL11.glTranslated(-doubleX, -doubleY, -doubleZ);

		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);

		GL11.glLineWidth(5);
		GL11.glBegin(GL11.GL_LINES);

		double x, y, height;
		if (vertices.get(0).x < vertices.get(1).x)
			x = vertices.get(0).x;
		else
			x = vertices.get(1).x;

		if (vertices.get(0).y < vertices.get(1).y)
			y = vertices.get(0).y;
		else
			y = vertices.get(1).y;

		if (vertices.get(0).height < vertices.get(1).height)
			height = vertices.get(0).height;
		else
			height = vertices.get(1).height;

		Location v1 = new Location(x, y, height);

		if (vertices.get(0).x > vertices.get(1).x)
			x = vertices.get(0).x + 1;
		else
			x = vertices.get(1).x + 1;

		if (vertices.get(0).y > vertices.get(1).y)
			y = vertices.get(0).y + 1;
		else
			y = vertices.get(1).y + 1;

		if (vertices.get(0).height > vertices.get(1).height)
			height = vertices.get(0).height + 1;
		else
			height = vertices.get(1).height + 1;

		Location v7 = new Location(x, y, height);

		Location v2 = new Location(v1.x, v1.y, v7.height);
		Location v3 = new Location(v1.x, v7.y, v7.height);
		Location v4 = new Location(v1.x, v7.y, v1.height);
		Location v5 = new Location(v7.x, v7.y, v1.height);
		Location v6 = new Location(v7.x, v1.y, v1.height);
		Location v8 = new Location(v7.x, v1.y, v7.height);

		GL11.glColor3ub((byte) 0, (byte) 70, (byte) 255);

		GL11.glVertex3d(v1.x, v1.height, v1.y);
		GL11.glVertex3d(v2.x, v2.height, v2.y);

		GL11.glVertex3d(v3.x, v3.height, v3.y);
		GL11.glVertex3d(v2.x, v2.height, v2.y);

		GL11.glVertex3d(v3.x, v3.height, v3.y);
		GL11.glVertex3d(v4.x, v4.height, v4.y);

		GL11.glVertex3d(v1.x, v1.height, v1.y);
		GL11.glVertex3d(v4.x, v4.height, v4.y);

		GL11.glVertex3d(v1.x, v1.height, v1.y);
		GL11.glVertex3d(v6.x, v6.height, v6.y);

		GL11.glVertex3d(v6.x, v6.height, v6.y);
		GL11.glVertex3d(v5.x, v5.height, v5.y);

		GL11.glVertex3d(v3.x, v3.height, v3.y);
		GL11.glVertex3d(v7.x, v7.height, v7.y);

		GL11.glVertex3d(v2.x, v2.height, v2.y);
		GL11.glVertex3d(v8.x, v8.height, v8.y);

		GL11.glVertex3d(v8.x, v8.height, v8.y);
		GL11.glVertex3d(v6.x, v6.height, v6.y);

		GL11.glVertex3d(v5.x, v5.height, v5.y);
		GL11.glVertex3d(v7.x, v7.height, v7.y);

		GL11.glVertex3d(v5.x, v5.height, v5.y);
		GL11.glVertex3d(v4.x, v4.height, v4.y);

		GL11.glVertex3d(v7.x, v7.height, v7.y);
		GL11.glVertex3d(v8.x, v8.height, v8.y);

		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	/**
	 * return false if it's not defined or not overlapping
	 */
	public boolean isOverlapping(Area area) 
	{
		//TODO:needs more work to support more shapes
		if(area instanceof CuboidArea)
		{
			CuboidArea cuboid = (CuboidArea)area;
			return area.defined && this.defined && (cuboid.isInside(this.vertices.get(0)) || cuboid.isInside(this.vertices.get(1)));
		}
		
		return false;
	}
}
