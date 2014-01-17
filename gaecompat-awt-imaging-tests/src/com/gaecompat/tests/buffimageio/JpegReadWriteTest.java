package com.gaecompat.tests.buffimageio;

import java.io.IOException;

import org.devlib.schmidt.imageinfo.ImageInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gaecompat.tests.TestIO;

public class JpegReadWriteTest  extends AbstractReadWriteTest {


	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testReadJpeg() throws IOException {
		TestIO.testRead(ImageInfo.Format.JPEG);
	}
	
	@Test
	public void testWriteJpeg() throws IOException {
		TestIO.testWrite(ImageInfo.Format.JPEG);
	}

}
