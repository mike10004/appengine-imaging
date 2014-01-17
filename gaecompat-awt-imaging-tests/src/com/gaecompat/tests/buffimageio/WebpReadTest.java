package com.gaecompat.tests.buffimageio;

import java.io.IOException;

import org.devlib.schmidt.imageinfo.ImageInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.gaecompat.tests.TestIO;

public class WebpReadTest extends AbstractReadWriteTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testReadWEBP() throws IOException {
		TestIO.testRead(ImageInfo.Format.WEBP, false);
	}
	
	@Ignore("not implemented by Java VP8 Decoder")
	@Test
	public void testWriteWEBP() throws IOException {
		TestIO.testWrite(ImageInfo.Format.WEBP);
	}
	
}
