package com.gaecompat.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gaecompat.imaging.ServiceImageIO;
import com.gaecompat.repackaged.com.google.common.collect.ImmutableList;
import com.gaecompat.repackaged.com.google.common.collect.Iterators;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.labs.repackaged.com.google.common.io.Resources;
import com.google.code.appengine.awt.image.BufferedImage;
import com.google.code.appengine.awt.image.WritableRaster;

/**
 * Servlet that responds with images on get requests, cycling through some
 * different filters that demonstrate {@code BufferedImage} operations.
 * @author mike
 *
 */
@SuppressWarnings("serial")
public class ImagingDemoServlet extends HttpServlet {
	
	private int filterType = 0;
	private final Object lock = new Object();
	
	private final Iterator<URL> inputImageUris = Iterators.cycle(ImmutableList.of(
			getClass().getResource("resources/image1.png"),
			getClass().getResource("resources/image2.jpg"),
			getClass().getResource("resources/image3.webp"),
			getClass().getResource("resources/image4.gif"),
			getClass().getResource("resources/image5.jpg")
			));
	
	private URL nextURL() {
		synchronized (lock) {
			return inputImageUris.next();
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("image/jpeg");
		byte[] filteredBytes = createFilteredImage();
		OutputStream out = resp.getOutputStream();
		out.write(filteredBytes);
	}
	
	private ImagesService imagesService;
	
	private synchronized ImagesService getImagesService() {
		if (imagesService == null) {
			imagesService = ImagesServiceFactory.getImagesService();
		}
		return imagesService;
	}
	
	private byte[] createFilteredImage() throws IOException {
		byte[] inputBytes = Resources.toByteArray(nextURL());
		ServiceImageIO io = new ServiceImageIO(getImagesService());
		BufferedImage image = io.read(inputBytes);
		image = SimpleFilter.Examples.random().filter(image);
		byte[] outputBytes = io.write(image, org.devlib.schmidt.imageinfo.ImageInfo.Format.PNG);
		return outputBytes;
	}
	
}
