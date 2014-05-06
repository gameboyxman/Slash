package com.slash.chats.templates;


import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.opengl.Display;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.slash.io.Language;
import com.slash.tools.Server;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;


public class ChatText extends ChatComponentText
{
	/**
	 * the untranslated English text
	 */
	String				rawText		= "";

	public ChatText()
	{
		super("");
	}

	public ChatText(String text)
	{
		super(Language.Translate(text));
		rawText = text;
	}

	/**
	 * Appends the given component to the end of this one.
	 */
	public IChatComponent appendSibling(IChatComponent sibling)
	{
		sibling.getChatStyle().setParentStyle(this.getChatStyle());
		this.siblings.add(sibling);
		return this;
	}

	/**
	 * Appends the given text to the end of this component.
	 */
	public IChatComponent appendText(String text)
	{
		return this.appendSibling(new ChatText(text));
	}

}
