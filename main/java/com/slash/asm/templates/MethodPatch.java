package com.slash.asm.templates;

import java.util.ArrayList;

public interface MethodPatch
{
	public abstract String[] getMethodList();
	public abstract String getDeobfuscatedName();
}
