package com.gaecompat.testing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.*;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

import com.google.appengine.labs.repackaged.com.google.common.io.Resources;
import com.google.code.appengine.awt.image.BufferedImage;
import com.google.code.appengine.awt.image.WritableRaster;
import com.google.code.appengine.imageio.ImageIO;

@SuppressWarnings("serial")
public class ImagingTestingServlet extends HttpServlet {
	
	private int filterType = 0;
	private final Object lock = new Object();
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("image/jpeg");
		int filter;
		synchronized(lock) {
			filter = filterType;
			filterType++;
		}
		byte[] filteredBytes = createFilteredImage(filter);
		OutputStream out = resp.getOutputStream();
		out.write(filteredBytes);
	}
	
	private byte[] createFilteredImage(int filterType) throws IOException {
		byte[] inputBytes = Resources.toByteArray(getClass().getResource("image1.png"));
		BufferedImage image;
		try {
			image = Sanselan.getBufferedImage(inputBytes);
		} catch (ImageReadException e) {
			throw new IOException(e);
		}
		image = filter(filterType, image);
		byte[] outputBytes;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", out);
			outputBytes = out.toByteArray();
		} finally {
			out.close();
		}
		return outputBytes;
	}
	
	private static BufferedImage filter(int filter, BufferedImage inputImage) {
		return keepChannel(filter % 3, inputImage);
	}
	
	private static BufferedImage keepChannel(int keep, BufferedImage image) {
		WritableRaster raster = image.getRaster();
		for (int y = 0; y < raster.getHeight(); y++) {
			for (int x = 0; x < raster.getWidth(); x++) {
				for (int channel = 0; channel < raster.getNumBands(); channel++) {
					if (channel != keep) {
						raster.setSample(x, y, channel, 0);
					}
				}
			}
		}
		return image;
	}
}
