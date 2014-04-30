package com.slash.chats.templates;


import com.slash.io.Language;
import com.slash.tools.McColor;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

/**
 * added support for "/n"
 * @author fihgu
 *
 */
public class ItemTooltip extends ChatText
{
	public ItemTooltip(String text)
	{
		this.rawText = text;
		NBTTagCompound item = new NBTTagCompound();
		item.setShort("id", (short) 1);
		item.setByte("count", (byte) 1);
		item.setShort("damage", (short) 0);

		NBTTagCompound display = new NBTTagCompound();
		

		NBTTagCompound tag = new NBTTagCompound();
		NBTTagList lore = new NBTTagList();
		
		if(text.contains("\n"))
		{
			
			String[] split = text.split("\n");
			display.setString("Name", Language.Translate(split[0]));
			
			for (int i = 1; i < split.length; i++)
			{
				String line = Language.Translate(split[i]);
				lore.appendTag(new NBTTagString(McColor.white + line));
			}
		}
		else
		{
			display.setString("Name", text);
		}
		

		display.setTag("Lore", lore);
		tag.setTag("display", display);
		item.setTag("tag", tag);

		this.text = item.toString();
	}

	public static IChatComponent getItemTooltip(ItemStack item)
	{
		if (item != null)
			return item.func_151000_E();
		else
			return noItem();
	}
	
	/**
	 * @return the tooltip for an empty hand.
	 */
	private static IChatComponent noItem()
	{
		return new ChatText("[Empty Hand]").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)
				.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, 
						new ItemTooltip("Maybe it's time to craft something..."))));
	}
}
