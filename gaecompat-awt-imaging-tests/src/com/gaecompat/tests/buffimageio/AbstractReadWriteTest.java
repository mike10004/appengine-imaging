package com.gaecompat.tests.buffimageio;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalImagesServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class AbstractReadWriteTest {

	protected final LocalServiceTestHelper helper = 
			new LocalServiceTestHelper(new LocalImagesServiceTestConfig());
			
	
	public void setUp() throws Exception {
		helper.setUp();
	}

	public void tearDown() throws Exception {
		helper.tearDown();
	}


	
}
