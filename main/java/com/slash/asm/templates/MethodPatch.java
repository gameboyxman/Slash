package com.slash.asm.templates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import com.slash.commands.Test;

public class MethodPatch
{
	/**
	 * full name of the class needs to be edited.
	 */
	public String className = null;
	/**
	 * contains information about which method in this class needs to be edited.
	 * key:method name as deobfuscated name.
	 * value:the description for that method.
	 */
	public ArrayList<MethodInfo> methods = new ArrayList<MethodInfo>();
	
	/**
	 *fully qualified name of the container class containing methods used to replace base class methods.
	 */
	public String containerClass = null;
}
