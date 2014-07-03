package com.slash.elements;

import java.io.Serializable;
import java.util.ArrayList;

public class ComplexArea implements Serializable
{
	public String name = "";
	public ArrayList<Area> list = new ArrayList<Area>();
	
	public ComplexArea()
	{
		
	}
	
	public ComplexArea(String name)
	{
		this.name = name;
	}
}
