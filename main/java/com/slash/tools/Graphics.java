package com.slash.tools;

import com.slash.elements.Area;
import com.slash.elements.ComplexArea;
import com.slash.elements.CubicArea;
import com.slash.elements.Location;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Graphics
{
	public static ComplexArea selectedAreas = new ComplexArea("selectedAreas");
	
	@SubscribeEvent
	public void render(RenderWorldLastEvent e)
	{
		for(Area area : selectedAreas.list)
		{
			if(area.defined)
			{
				area.draw(e.partialTicks);
			}
		}
	}
}
