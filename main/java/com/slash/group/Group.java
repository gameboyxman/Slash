package com.slash.group;

import java.io.File;
import java.util.ArrayList;
import com.slash.elements.Player;
import com.slash.io.GroupFile;
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
	}
	
	public static void loadAll()
	{
		File root = new File("./slash/group/");
		
		if(!root.exists())
			root.mkdirs();
		
		for(String groupName : root.list())
		{
			if(new File("./slash/group/" + groupName + "/players.txt").exists())
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
	public Group getGroup(String name)
	{
		for(Group temp : groups)
			if(temp.name.equals(name))
				return temp;
		
		return new Group(name);
	}
	
	public static ArrayList<Group> getGroups(Player player)
	{
		ArrayList<Group> temp = new ArrayList<Group>();
		
		for(Group group : groups)
			if(group.contains(player))
				temp.add(group);
		
		return temp;
	}
}
