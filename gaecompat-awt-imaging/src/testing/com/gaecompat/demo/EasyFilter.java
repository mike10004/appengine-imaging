package com.gaecompat.demo;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.gaecompat.repackaged.com.google.common.collect.ImmutableList;
import com.gaecompat.repackaged.com.google.common.collect.Lists;
import com.google.code.appengine.awt.Color;
import com.google.code.appengine.awt.image.BufferedImage;
import com.google.code.appengine.awt.image.ConvolveOp;
import com.google.code.appengine.awt.image.Kernel;
import com.google.code.appengine.awt.image.WritableRaster;

public interface EasyFilter {

	BufferedImage filter(BufferedImage inputImage) throws IOException;

	public static abstract class InPlaceRasterFilter implements EasyFilter {

		@Override
		public BufferedImage filter(BufferedImage inputImage)
				throws IOException {
			WritableRaster raster = inputImage.getRaster();
			filter(raster);
			return inputImage;
		}

		protected abstract void filter(WritableRaster raster) throws IOException;
		
	}
	
	public static abstract class PixelOpFilter extends InPlaceRasterFilter {

		@Override
		protected void filter(WritableRaster raster) throws IOException {
			final int w = raster.getWidth(), h = raster.getHeight();
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					mutate(raster, x, y);
				}
			}
		}

		protected abstract void mutate(WritableRaster raster, int x, int y);
	}
	
	public static class ConvolvingFilter implements EasyFilter {

		private ConvolveOp convolveOp;
		
		public ConvolvingFilter(ConvolveOp convolveOp) {
			super();
			this.convolveOp = convolveOp;
		}

		@Override
		public BufferedImage filter(BufferedImage inputImage)
				throws IOException {
			BufferedImage dest = convolveOp.filter(inputImage, null);
			return dest;
		}
	}

	/**
	 * 
	 * @author http://www.cs.bham.ac.uk/~szh/teaching/graphics/sourcecode/RasterDataExample_ChangePixels.java
	 *
	 */
	public static class Convolutions {

		private Convolutions() {}
		
		// The last parameter of ConvolveOp is RenderingHints hints. Not imortant, you can 
		// always use null. 
		// Do sharpening 
		public static ConvolveOp sharpen() {
			float data[] = { -1.0f, -1.0f, -1.0f, 
							-1.0f,  9.0f,  -1.0f, 
							-1.0f,  -1.0f, -1.0f };
			Kernel kernel = new Kernel(3, 3, data);
			ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
					null);

			return convolve;
		}

		// Averaging or smoothing  
		public static ConvolveOp smoothing() {
			float data[] = { 0.111f, 0.111f, 0.111f, 
							0.111f,  0.111f,  0.111f,
							0.111f, 0.111f, 0.111f };
			Kernel kernel = new Kernel(3, 3, data);
			ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL,
					null);
			
			return convolve;
		}

		
		// Detect edges
		public static ConvolveOp edgeDetect() {
			float data[] = { 0.0f, 1.0f,  0.0f, 
							 1.0f, -4.0f,  1.0f, 
							 0.0f, 1.0f,  0.0f };

			Kernel kernel = new Kernel(3, 3, data);
			ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
					null);

			return convolve;
		}
		
		// Embross a image
		public static ConvolveOp emboss() {
			float data[] = { -2.0f, -1.0f, 0.0f, 
							 -1.0f, 1.0f,  1.0f, 
							  0.0f,  1.0f, 2.0f };

			Kernel kernel = new Kernel(3, 3, data);
			ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
					null);

			return convolve;
		}

		public static ConvolveOp motionblur() {
			float data[] = { 0.0f, 0.0f, 1.0f, 
							 0.0f, 0.0f, 0.0f, 
							 1.0f, 0.0f, 0.0f };

			Kernel kernel = new Kernel(3, 3, data);
			ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
					null);

			return convolve;
		}

		// raise edges
		public static ConvolveOp emboss2() {
			float data[] = { 0.0f, 0.0f, -2.0f, 
							 0.0f, 2.0f, 0.0f, 
							 1.0f, 0.0f, 0.0f };

			Kernel kernel = new Kernel(3, 3, data);
			ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
					null);

			return convolve;
		}	
		
	}


	public static class ChannelFilter extends PixelOpFilter {
	
		private double[] channelFactors;
		
		public ChannelFilter(double factor0, double factor1, double factor2, double factor3) {
			channelFactors = new double[] { factor0, factor1, factor2, factor3 };
		}
	
		public ChannelFilter(double factor0, double factor1, double factor2) {
			channelFactors = new double[] { factor0, factor1, factor2};
		}
	
		@Override
		protected void mutate(WritableRaster raster, int x, int y) {
			int numBands = raster.getNumBands();
			int maxBand = Math.min(channelFactors.length, numBands);
			for (int b = 0; b < maxBand; b++) {
				int value = Colors.roundSample(channelFactors[b] * raster.getSample(x, y, b));
				raster.setSample(x, y, b, value);
			}
		}
	}
	
	public static class GrayFilter implements EasyFilter {

				@Override
		public BufferedImage filter(BufferedImage image)
				throws IOException {
			final int w = image.getWidth(), h = image.getHeight();
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					Color color = new Color(image.getRGB(x, y));
					int intensity = Colors.roundSample(color.getRed() * 0.3 + color.getGreen() * 0.59 + color.getBlue() * 0.11);
					Color gray = new Color(intensity, intensity, intensity);
					image.setRGB(x, y, gray.getRGB());
				}
			}
			return image;
		}
		
	}
	
	public static class IdentityFilter implements EasyFilter {

		@Override
		public BufferedImage filter(BufferedImage inputImage)
				throws IOException {
			return inputImage;
		}
		
	}

	public static class Colors {

		private Colors() {}
		
		public static int clamp(int value, int minInclusive, int maxInclusive) {
			if (value < minInclusive) {
				return minInclusive;
			}
			if (value > maxInclusive) {
				return maxInclusive;
			}
			return value;
		}
		
		public static int clamp(int value) {
			return clamp(value, 0, 255);
		}
		
		public static int roundSample(double value) {
			return clamp(Math.round((float)value));
		}
	}

	public static class Examples {

		
		private static final Random random = new Random();
		private Examples() {}
		
		public static ImmutableList<EasyFilter> allFilters() {
			return filters;
		}
		
		public static EasyFilter random() {
			return filters.get(random.nextInt(filters.size()));
		}
		
		private static ImmutableList<EasyFilter> filters = createFilters();
		
		private static ImmutableList<EasyFilter> createFilters() {
			ChannelFilter red = newRedFilter(), green =newGreenFilter(), blue = newBlueFilter();
			List<EasyFilter> filters = Lists.<EasyFilter>newArrayList();
			filters.add(new IdentityFilter());
			filters.add(new ConvolvingFilter(Convolutions.edgeDetect()));
			filters.add(red);
			filters.add(new ConvolvingFilter(Convolutions.emboss()));
			filters.add(new ConvolvingFilter(Convolutions.emboss2()));
			filters.add(new GrayFilter());
			filters.add(blue);
			filters.add(new ConvolvingFilter(Convolutions.motionblur()));
			filters.add(new ConvolvingFilter(Convolutions.sharpen()));
			filters.add(green);
			filters.add(new ConvolvingFilter(Convolutions.smoothing()));
			return ImmutableList.copyOf(filters);
		}
		
		public static ChannelFilter newRedFilter() {
			return new ChannelFilter(1, 0, 0);
		}
		
		public static ChannelFilter newGreenFilter() {
			return new ChannelFilter(0, 1, 0);
		}
		
		public static ChannelFilter newBlueFilter() {
			return new ChannelFilter(0, 0, 1);
		}
	}
	
}
