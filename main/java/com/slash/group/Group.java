package com.slash.group;

import java.io.File;
import java.util.ArrayList;
import com.slash.elements.Player;
import com.slash.io.GroupFile;
import com.slash.io.GroupSettingFile;
import com.slash.io.templates.SaveFile;

public class Group
{
	public static ArrayList<Group> groups = new ArrayList<Group>();
	
	public ArrayList<String> players = new ArrayList<String>();
	public String name = "";
	GroupFile file;
	
	private Group(String name)
	{
		this.name = name;
		file = new GroupFile(name);
		load();
		groups.add(this);
	}
	
	
	public void load()
	{
		file.load();
		players.clear();
		for(String name : file.list)
		{
			players.add(name);
		}
	}
	
	public void save()
	{
		file.list.clear();
		for(String name : players)
		{
			file.list.add(name);
		}
		file.save();
	}
	
	public static void init()
	{
		loadAll();
	}
	
	public static Group getDefaultGroup()
	{
		return getGroup(GroupSettingFile.getDefaultGroup());
	}
	
	public Group addPlayer(Player player)
	{
		this.players.add(player.name);
		return this;
	}
	
	public static void loadAll()
	{
		groups.clear();
		File root = new File("./slash/group/");
		
		if(!root.exists())
			root.mkdirs();
		
		for(String groupName : root.list())
		{
			if(new File("./slash/group/" + groupName + "/Members.txt").exists())
				new Group(groupName);
		}
	}
	
	private boolean contains(Player player)
	{
		return players.contains(player.name);
	}
	
	/**
	 * This will get a group instance or create a new group.
	 * @param name
	 * @return
	 */
	public static Group getGroup(String name)
	{
		for(Group temp : groups)
			if(temp.name.equals(name))
				return temp;
		
		return new Group(name);
	}
	
	/**
	 * get groups this player is in.
	 * @param player
	 * @return
	 */
	public static ArrayList<Group> getGroups(Player player)
	{
		loadAll();
		ArrayList<Group> temp = new ArrayList<Group>();
		
		for(Group group : groups)
			if(group.contains(player))
				temp.add(group);
		
		return temp;
	}
}
