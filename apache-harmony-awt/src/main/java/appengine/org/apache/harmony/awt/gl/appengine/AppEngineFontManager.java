package org.apache.harmony.awt.gl.appengine;

import org.apache.harmony.awt.gl.font.FontManager;

import com.google.code.appengine.awt.Font;
import com.google.code.appengine.awt.peer.FontPeer;

public class AppEngineFontManager extends FontManager
{
	public static final AppEngineFontManager inst = new AppEngineFontManager();
	
	@Override
	public FontPeer createDefaultFont(int style, int size)
	{
		return null;
	}

	@Override
	public FontPeer createPhysicalFontPeer(String name, int style, int size)
	{
		return null;
	}

	@Override
	public String[] getAllFamilies()
	{
		return null;
	}

	@Override
	public Font[] getAllFonts()
	{
		return null;
	}

	@Override
	public void initLCIDTable()
	{
	}
}
