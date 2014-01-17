package com.gaecompat.tests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class WebpPluginTest {

	@Test
	public void testHasReader() {
		
		int n = 0;
		Iterator<javax.imageio.ImageReader> readers = javax.imageio.ImageIO.getImageReadersByFormatName("WEBP");
		while (readers.hasNext()) {
			System.out.println(readers.next());
			n++;
		}
		
		assertTrue(n > 0);
		
	}

}
