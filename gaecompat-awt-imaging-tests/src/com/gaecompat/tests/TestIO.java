package com.gaecompat.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.devlib.schmidt.imageinfo.ImageInfo;

import com.gaecompat.imaging.ServiceImageIO;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.repackaged.com.google.common.io.Files;
import com.google.code.appengine.awt.image.BufferedImage;

public class TestIO {
	
	public static void testRead(ImageInfo.Format format) throws IOException {
		testRead(format, true);
	}
	
	public static void testRead(ImageInfo.Format format, boolean confirmInputFormat) throws IOException {
		ServiceImageIO io = new ServiceImageIO(ImagesServiceFactory.getImagesService());
		List<String> patterns = new ArrayList<String>();
		for (String ext : format.getCommonExtensions()) {
			patterns.add("*." + ext);
		}
		WildcardFileFilter filter = new WildcardFileFilter(patterns);
		List<File> files = TestData.getFiles(filter);
		System.out.println(files.size() + " " + format.getName() + " files found");
		assertFalse(files.isEmpty());
		for (File file : files) {
			byte[] bytes = Files.toByteArray(file);
			if (confirmInputFormat) {
				ImageInfo.Format imageFormat ;
				try {
					imageFormat = TestData.readFormat(bytes);
				} catch (IOException e) {
					System.out.format("%s threw %s on image info check%n", file, e);
					continue;
				}
				if (format != imageFormat) {
					System.out.format("not %s format: %s%n", format, file);
					continue;
				}
			}
			BufferedImage image = io.read(bytes);
			System.out.println(file + ": " + image);
			assertNotNull(image);
			assertTrue("image has zero width or height", image.getWidth() > 0 && image.getHeight() > 0);
		}
	}
	
	public static void testWrite(ImageInfo.Format format) throws IOException {
		BufferedImage image = TestData.generateImage();
		testWrite(image, format);
	}
	
	public static void testWrite(BufferedImage image, ImageInfo.Format format) throws IOException {
		ServiceImageIO io = new ServiceImageIO(ImagesServiceFactory.getImagesService());
		byte[] imageBytes = io.write(image, format);
		assertTrue(imageBytes.length > 0);
		ImageInfo info = TestData.readImageInfo(imageBytes);
		assertEquals("actual format is " + info.getFormatName() + ", but should be " + format.getName(), 
				format, info.getFormat());
		
	}
	
}
