package com.slash.chats.templates;


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


public class ChatText implements IChatComponent
{
	/**
	 * the untranslated English text
	 */
	String				rawText		= "";
	/**
	 * translated text.
	 */
	String				text		= "";
	/**
	 * The later siblings of this component. If this component turns the text
	 * bold, that will apply to all the siblings until a later sibling turns the
	 * text something else.
	 */
	protected List		siblings	= Lists.newArrayList();
	private ChatStyle	style;

	public ChatText()
	{

	}

	public ChatText(String text)
	{
		rawText = text;
		this.text = Language.Translate(text);	
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
	 * Gets the sibling components of this one.
	 */
	public List getSiblings()
	{
		return this.siblings;
	}

	/**
	 * Appends the given text to the end of this component.
	 */
	public IChatComponent appendText(String text)
	{
		return this.appendSibling(new ChatText(text));
	}

	public IChatComponent setChatStyle(ChatStyle style)
	{
		this.style = style;
		Iterator iterator = this.siblings.iterator();

		while (iterator.hasNext())
		{
			IChatComponent ichatcomponent = (IChatComponent) iterator.next();
			ichatcomponent.getChatStyle().setParentStyle(this.getChatStyle());
		}

		return this;
	}

	public ChatStyle getChatStyle()
	{
		if (this.style == null)
		{
			this.style = new ChatStyle();
			Iterator iterator = this.siblings.iterator();

			while (iterator.hasNext())
			{
				IChatComponent ichatcomponent = (IChatComponent) iterator.next();
				ichatcomponent.getChatStyle().setParentStyle(this.style);
			}
		}

		return this.style;
	}

	public Iterator iterator()
	{
		return Iterators.concat(Iterators.forArray(new ChatText[] { this }), createDeepCopyIterator(this.siblings));
	}

	/**
	 * Gets the text of this component, with formatting codes added for
	 * rendering.
	 */
	@SideOnly(Side.CLIENT)
	public String getFormattedText()
	{
		StringBuilder stringbuilder = new StringBuilder();
		Iterator iterator = this.iterator();

		while (iterator.hasNext())
		{
			IChatComponent text = (IChatComponent) iterator.next();
			stringbuilder.append(text.getChatStyle().getFormattingCode());
			stringbuilder.append(text.getUnformattedTextForChat());
			stringbuilder.append(EnumChatFormatting.RESET);

		}
		return stringbuilder.toString();
	}

	/**
	 * Creates an iterator that iterates over the given components, returning
	 * deep copies of each component in turn so that the properties of the
	 * returned objects will remain externally consistent after being returned.
	 */
	public static Iterator createDeepCopyIterator(Iterable p_150262_0_)
	{
		Iterator iterator = Iterators.concat(Iterators.transform(p_150262_0_.iterator(), new Function()
		{
			private static final String	__OBFID	= "CL_00001258";

			public Iterator apply(IChatComponent p_150665_1_)
			{
				return p_150665_1_.iterator();
			}

			public Object apply(Object par1Obj)
			{
				return this.apply((IChatComponent) par1Obj);
			}
		}));
		iterator = Iterators.transform(iterator, new Function()
		{
			private static final String	__OBFID	= "CL_00001259";

			public IChatComponent apply(IChatComponent p_150666_1_)
			{
				IChatComponent ichatcomponent1 = p_150666_1_.createCopy();
				ichatcomponent1.setChatStyle(ichatcomponent1.getChatStyle().createDeepCopy());
				return ichatcomponent1;
			}

			public Object apply(Object par1Obj)
			{
				return this.apply((IChatComponent) par1Obj);
			}
		});
		return iterator;
	}

	public int hashCode()
	{
		return 31 * this.style.hashCode() + this.siblings.hashCode();
	}

	public String toString()
	{
		return "ChatText{style=" + this.style + ", siblings=" + this.siblings + '}';
	}

	@Override
	public String getUnformattedTextForChat()
	{
		return getUnformattedText();
	}

	@Override
	public String getUnformattedText()
	{
		return this.text;
	}

	public ChatComponentText createCopy()
	{
		ChatComponentText chatcomponenttext = new ChatComponentText(this.text);
		chatcomponenttext.setChatStyle(this.getChatStyle().createShallowCopy());
		Iterator iterator = this.getSiblings().iterator();

		while (iterator.hasNext())
		{
			IChatComponent ichatcomponent = (IChatComponent) iterator.next();
			chatcomponenttext.appendSibling(ichatcomponent.createCopy());
		}

		return chatcomponenttext;
	}

	public boolean equals(Object par1Obj)
	{
		if (this == par1Obj)
		{
			return true;
		}
		else if (!(par1Obj instanceof ChatText))
		{
			return false;
		}
		else
		{
			ChatText chatText = (ChatText) par1Obj;
			return this.text.equals(chatText.text) && this.siblings.equals(chatText.siblings);
		}
	}
}
