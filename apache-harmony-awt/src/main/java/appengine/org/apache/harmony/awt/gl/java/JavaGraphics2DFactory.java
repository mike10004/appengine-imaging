package org.apache.harmony.awt.gl.java;

import java.io.IOException;

import org.apache.harmony.awt.gl.CommonGraphics2DFactory;
import org.apache.harmony.awt.gl.MultiRectArea;
import org.apache.harmony.awt.gl.font.FontManager;
import org.apache.harmony.awt.wtk.NativeWindow;
import org.apache.harmony.awt.wtk.WindowFactory;

import com.google.code.appengine.awt.Font;
import com.google.code.appengine.awt.Graphics2D;
import com.google.code.appengine.awt.GraphicsEnvironment;


public class JavaGraphics2DFactory extends CommonGraphics2DFactory
{
	public JavaGraphics2DFactory()
	{
		inst = this;
	}

	public GraphicsEnvironment createGraphicsEnvironment(WindowFactory wf)
	{
		return new JavaGraphicsEnvironment();
	}

	@Override
	public Font embedFont(String fontFilePath) throws IOException
	{
		return null;
	}

	public FontManager getFontManager()
	{
		return null;
	}

	public Graphics2D getGraphics2D(NativeWindow win, int translateX,
			int translateY, MultiRectArea clip)
	{
		return null;
	}

	public Graphics2D getGraphics2D(NativeWindow win, int translateX,
			int translateY, int width, int height)
	{
		return null;
	}
}
