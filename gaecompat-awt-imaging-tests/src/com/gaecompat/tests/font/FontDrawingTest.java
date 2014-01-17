package com.gaecompat.tests.font;

import java.io.IOException;

import org.apache.harmony.awt.gl.font.FontManager;
import org.junit.Ignore;
import org.junit.Test;

import com.google.code.appengine.awt.Graphics2D;
import com.google.code.appengine.awt.image.BufferedImage;

public class FontDrawingTest {
	
	@Test
	public void testJDKFonts() {
		FontManager fontManager = FontManager.getInstance();
		System.out.println(fontManager);
	}
	
	@Ignore
	@Test
	public void testDrawString() throws IOException {
		BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = image.createGraphics();
		
		g.drawString("hello, world", 100, 100);
	}
	
}
