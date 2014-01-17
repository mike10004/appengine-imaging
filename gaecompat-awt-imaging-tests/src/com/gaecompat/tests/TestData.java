package com.gaecompat.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.devlib.schmidt.imageinfo.ImageInfo;
import org.junit.Assert;

import com.google.code.appengine.awt.image.BufferedImage;
import com.google.code.appengine.awt.image.RenderedImage;
import com.google.code.appengine.awt.image.WritableRaster;
import com.google.code.appengine.imageio.ImageIO;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Closeables;

public class TestData {

	private TestData() {}
	
	public static File getDir(String...pathParts) {
		File dir = new File("data");
		for (String p : pathParts) {
			dir = new File(dir, p);
		}
		Assert.assertTrue(dir.isDirectory());
		return dir;
	}
	
	public static File getPathname(String firstPathPart, String...pathParts) {
		return getPathname(getDir(), firstPathPart, pathParts);
	}
	
	public static File getPathname(File topDir, String firstPathPart, String...pathParts) {
		File file = new File(topDir, firstPathPart);
		for (String pathPart : pathParts) {
			file = new File(topDir, pathPart);
		}
		return file;
	}
	
	public static ImmutableList<File> glob(String pattern) {
		return glob(getDir(), pattern);
	}
	
	public static final IOCase DEFAULT_CASE_SENSITIVITY = IOCase.INSENSITIVE;
	public static final boolean DEFAULT_RECURSIVENESS = true;
	
	public static ImmutableList<File> glob(File dir, String pattern) {
		return glob(dir, pattern, DEFAULT_CASE_SENSITIVITY, DEFAULT_RECURSIVENESS); 
	}
	
	public static ImmutableList<File> glob(File dir, String pattern, IOCase caseSensitivity, boolean recursive) {
		WildcardFileFilter fileFilter = new WildcardFileFilter(pattern);
		return getFiles(dir, fileFilter, caseSensitivity, recursive);
	}
	
	public static ImmutableList<File> getFiles(File dir, IOFileFilter filter, IOCase caseSensitivity, boolean recursive) {
		Collection<File> files = FileUtils.listFiles(dir, filter, 
				recursive ? FileFilterUtils.trueFileFilter() : FileFilterUtils.falseFileFilter());
		return ImmutableList.copyOf(files);
	}

	public static ImmutableList<File> getFiles(IOFileFilter filter) {
		return getFiles(getDir(), filter);
	}
	
	public static ImmutableList<File> getFiles(File dir, IOFileFilter filter) {
		return getFiles(dir, filter, DEFAULT_CASE_SENSITIVITY, DEFAULT_RECURSIVENESS);
	}

	public static ImmutableList<File> glob(String pattern, IOCase caseSensitivity, boolean recursive) {
		return glob(getDir(), pattern, caseSensitivity, recursive);
	}

	public static BufferedImage generateImage() {
		BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster();
		int[] p = new int[3];
		int r, g, b;
		for (int y = 0; y < raster.getHeight(); y++) {
			for (int x = 0; x < raster.getWidth(); x++) {
				if (y < 200 && x < 200) {
					r = 0xff; g = b = 0;
				} else if (y >= 200 && x < 200) {
					g = 0xff; r = b = 0;
				} else if (y < 200 && x >= 200) {
					b = 0xff; r = g = 0;
				} else {
					r = g = b = 0; 
				}
				p[0] = r;
				p[1] = g;
				p[2] = b;
				raster.setPixel(x, y, p);
			}
		}
		return image;
	}
	
	public static ImageInfo readImageInfo(InputStream in) throws IOException {
		ImageInfo ii = new ImageInfo();
		ii.setInput(in);
		if (!ii.check()) {
			throw new IOException("failed imageinfo check");
		}
		return ii;
	}

	public static ImageInfo readImageInfo(byte[] imageBytes) throws IOException  {
		ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
		boolean clean = false;
		try {
			ImageInfo ii = readImageInfo(in);
			clean = true;
			return ii;
		} finally {
			Closeables.close(in, !clean);
		}
	}
	
	public static ImageInfo.Format readFormat(byte[] imageBytes) throws IOException {
		return readImageInfo(imageBytes).getFormat();
	}
}
