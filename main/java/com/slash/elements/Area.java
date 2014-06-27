package com.slash.elements;

import java.util.ArrayList;

public abstract class Area
{
	/**
	 * if this Area object has enough vertices to define itself
	 */
	public boolean defined = false;
	
	/**
	 * a list of vertices this Area have, Order does matter.
	 */
	public ArrayList<Location> vertices = new ArrayList<Location>();
	
	/**
	 * the number of vertices needed to define this area.
	 */
	protected int requiredNumberOfVertices = 0;
	
	/**
	 *telling if a block is inside this area without loop though a list of blocks.
	 */
	public abstract boolean isInside(Location location);
	
	/**\
	 * draw this area into world
	 */
	public abstract void draw(float partialTicks);
	
	public boolean addVertex(Location vertex)
	{
		if(vertices.size() >= 1 && vertices.get(0).dimension != vertex.dimension)
		{
			//cannot select area across dimension.
			return false;
		}
		
		vertices.add(vertex);
		
		if(vertices.size() >= this.requiredNumberOfVertices)
			defined = true;
		
		return true;
	}
}
