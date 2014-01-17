package com.gaecompat.imaging;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;
import org.devlib.schmidt.imageinfo.ImageInfo;
import org.devlib.schmidt.imageinfo.ImageInfo.Format;

import static org.devlib.schmidt.imageinfo.ImageInfo.Format.*;

import com.gaecompat.imaging.MagicNumbers.BytePair;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.images.ImagesService.OutputEncoding;
import com.google.code.appengine.awt.image.BufferedImage;
import com.google.code.appengine.awt.image.RenderedImage;
import com.google.code.appengine.imageio.ImageIO;

import static com.gaecompat.imaging.ServiceImageIO.Support.*;

public class ServiceImageIO {

	private final ImagesService imagesService;

	public ServiceImageIO(ImagesService imagesService) {
		super();
		this.imagesService = imagesService;
		if (imagesService == null) {
			throw new NullPointerException("images service must be non-null");
		}
	}
	
	public static enum Support {
		IMAGEIO,
		SANSELAN,
		SERVICE,
		NONE;
		
		public IO getIO(ImagesService imagesService) {
			switch (this) {
				case IMAGEIO: return new ImageIOIO();
				case SANSELAN: return new SanselanIO();
				case SERVICE: return new ServiceIO(imagesService);
				default: return new NullIO();
			}
		}
	}
	
	private static class NullIO implements IO {

		@Override
		public byte[] write(BufferedImage image, Format format)
				throws IOException {
			throw new IOException("write not supported for format " + format);
		}

		@Override
		public BufferedImage read(byte[] imageBytes) throws IOException {
			throw new IOException("read not supported for image data format");
		}
		
	}
	
	private static Map<ImageInfo.Format, Support> readSupport = buildReadSupportMap();
	private static Map<ImageInfo.Format, Support> writeSupport = buildWriteSupportMap();
	
	public static Support getReadSupport(Format format) {
		return readSupport.get(format);
	}
			
	private static Map<Format, Support> buildWriteSupportMap() {
		Map<Format, Support> m = new TreeMap<Format, Support>();
		m.put(BMP, NONE);
		m.put(GIF, SANSELAN);
		m.put(IFF, NONE);
		m.put(JPEG, SERVICE);
		m.put(PBM, NONE);
		m.put(PCX, NONE);
		m.put(PGM, NONE);
		m.put(PNG, IMAGEIO);
		m.put(PPM, NONE);
		m.put(PSD, NONE);
		m.put(RAS, NONE);
		m.put(TIFF, NONE);
		m.put(WEBP, SERVICE);
		m.put(ICO, NONE);
		return Collections.unmodifiableMap(m);
	}

	private static Map<Format, Support> buildReadSupportMap() {
		Map<Format, Support> m = new TreeMap<Format, Support>();
		m.put(BMP, SERVICE);
		m.put(GIF, SANSELAN);
		m.put(IFF, NONE);
		m.put(JPEG, SERVICE);
		m.put(PBM, NONE);
		m.put(PCX, NONE);
		m.put(PGM, NONE);
		m.put(PNG, SANSELAN);
		m.put(PPM, NONE);
		m.put(PSD, NONE);
		m.put(RAS, NONE);
		m.put(TIFF, SERVICE);
		m.put(WEBP, SERVICE);
		m.put(ICO, SERVICE);
		return Collections.unmodifiableMap(m);
	}

	public static Support getWriteSupport(ImageInfo.Format format) {
		return writeSupport.get(format);
	}

	public BufferedImage read(byte[] imageBytes) throws IOException {
		Format format = readFormatQuietly(imageBytes);
		if (format == null) {
			throw new IOException("unsupported image data format");
		}
		Support support = getReadSupport(format);
		return support.getIO(imagesService).read(imageBytes);
	}
	
	public byte[] write(BufferedImage image, ImageInfo.Format format) throws IOException {
		Support support = getWriteSupport(format);
		return support.getIO(imagesService).write(image, format);
	}
	
	public static byte[] convertFormatWithService(ImagesService imagesService, byte[] inputImageBytes, OutputEncoding outputFormat) {
		Transform identity = ImagesServiceFactory.makeRotate(0);
		Image image = ImagesServiceFactory.makeImage(inputImageBytes);
		Image outputImage = imagesService.applyTransform(identity, image, outputFormat);
		return outputImage.getImageData();
	}
	
	private static interface IO {
		byte[] write(BufferedImage image, ImageInfo.Format format) throws IOException;
		BufferedImage read(byte[] imageBytes) throws IOException;
	}
	
	private static class ServiceIO implements IO {
	
		private ImagesService imagesService;
		
		public ServiceIO(ImagesService imagesService) {
			this.imagesService = imagesService;	
		}
		
		public BufferedImage read(byte[] imageBytes) throws IOException {
			Image image = ImagesServiceFactory.makeImage(imageBytes);
			Transform identity = ImagesServiceFactory.makeRotate(0);
			OutputEncoding intermediateFormat = OutputEncoding.PNG;
			Image intermediateImage = imagesService.applyTransform(identity, image, intermediateFormat);
			byte[] intermediateBytes = intermediateImage.getImageData();
			return SANSELAN.getIO(imagesService).read(intermediateBytes);
		}
		
		public byte[] write(BufferedImage image, ImageInfo.Format format) throws IOException {
			OutputEncoding encoding = toOutputEncoding(format);
			if (encoding == null) {
				throw new IOException("images service does not support output format " + format);
			}
			ImageInfo.Format intermediateFormat = ImageInfo.Format.PNG;
			byte[] intermediateBytes = SANSELAN.getIO(imagesService).write(image, intermediateFormat);
			byte[] outputBytes = convertFormatWithService(imagesService, intermediateBytes, encoding);
			return outputBytes;
		}
	}	

	private static class SanselanIO implements IO {
		
		@Override
		public byte[] write(BufferedImage image, ImageInfo.Format format) throws IOException {
			ImageFormat imageFormat = toSanselanFormat(format);
			if (imageFormat == null) {
				throw new IOException("no sanselan support for writing " + format + " format");
			}
			try {
				return Sanselan.writeImageToBytes(image, imageFormat, new TreeMap());
			} catch (ImageWriteException e) {
				throw new IOException(e);
			}
		}
		
		@Override
		public BufferedImage read(byte[] imageBytes) throws IOException {
			try {
				return Sanselan.getBufferedImage(imageBytes);
			} catch (ImageReadException e) {
				throw new IOException(e);
			}
		}
		
	}
	
	private static class ImageIOIO implements IO {

		@Override
		public BufferedImage read(byte[] imageBytes) throws IOException {
			ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
			boolean clean = false;
			BufferedImage image;
			try {
				image = ImageIO.read(in);
				clean = true;
			} finally {
				Closeables.close(in, !clean);
			}
			if (image == null) {
				throw new IOException("ImageIO does not support reading data format");
			}
			return image;
		}
		
		@Override
		public byte[] write(BufferedImage image, ImageInfo.Format format) throws IOException {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			boolean clean = false;
			try {
				ImageIO.write(image, format.getPreferredExtension(), out);
				clean = true;
			} finally {
				Closeables.close(out, !clean);
			}
			return out.toByteArray();
		}
	}
	
	private static ImageInfo.Format readFormatQuietly(byte[] imageBytes) {
		BytePair magic = MagicNumbers.read(imageBytes);
		if (MagicNumbers.getFormat(magic) == Format.WEBP) {
			return Format.WEBP;
		}
		ImageInfo ii = new ImageInfo();
		ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
		try {
			ii.setInput(in);
			ii.check();
		} finally {
			try {
				Closeables.close(in, false);
			} catch (IOException swallow) {
			}
		}
		return ii.getFormat();
	}

	public static OutputEncoding toOutputEncoding(ImageInfo.Format format) {
		switch (format) {
		case PNG:
			return OutputEncoding.PNG;
		case JPEG:
			return OutputEncoding.JPEG;
		case WEBP:
			return OutputEncoding.WEBP;
		default: 
			return null;
		}
	}
	
	public static ImageFormat toSanselanFormat(ImageInfo.Format format) {
		switch (format) {
		case BMP:
			return ImageFormat.IMAGE_FORMAT_BMP;
		case GIF: return ImageFormat.IMAGE_FORMAT_GIF;
		case ICO: return ImageFormat.IMAGE_FORMAT_ICO;
		case IFF: return null;
		case JPEG: return ImageFormat.IMAGE_FORMAT_JPEG;
		case PBM: return ImageFormat.IMAGE_FORMAT_PBM;
		case PPM: return ImageFormat.IMAGE_FORMAT_PPM;
		case PGM: return ImageFormat.IMAGE_FORMAT_PGM;
		case PCX: return null;
		case PNG: return ImageFormat.IMAGE_FORMAT_PNG;
		case PSD: return ImageFormat.IMAGE_FORMAT_PSD;
		case RAS: return null;
		case TIFF: return ImageFormat.IMAGE_FORMAT_TIFF;
		case WEBP: return null;
		default: return null;
		}
	}

	public static class Closeables {
	
		  /**
		   * Closes a {@link Closeable}, with control over whether an {@code IOException} may be thrown.
		   * This is primarily useful in a finally block, where a thrown exception needs to be logged but
		   * not propagated (otherwise the original exception will be lost).
		   *
		   * <p>If {@code swallowIOException} is true then we never throw {@code IOException} but merely log
		   * it.
		   *
		   * <p>Example: <pre>   {@code
		   *
		   *   public void useStreamNicely() throws IOException {
		   *     SomeStream stream = new SomeStream("foo");
		   *     boolean threw = true;
		   *     try {
		   *       // ... code which does something with the stream ...
		   *       threw = false;
		   *     } finally {
		   *       // If an exception occurs, rethrow it only if threw==false:
		   *       Closeables.close(stream, threw);
		   *     }
		   *   }}</pre>
		   *
		   * @param closeable the {@code Closeable} object to be closed, or null, in which case this method
		   *     does nothing
		   * @param swallowIOException if true, don't propagate IO exceptions thrown by the {@code close}
		   *     methods
		   * @throws IOException if {@code swallowIOException} is false and {@code close} throws an
		   *     {@code IOException}.
		   */
		  public static void close(@Nullable Closeable closeable,
			      boolean swallowIOException) throws IOException {
			    if (closeable == null) {
			      return;
			    }
			    try {
			      closeable.close();
			    } catch (IOException e) {
			      if (swallowIOException) {
			        Logger.getLogger(ServiceImageIO.class.getName()).log(Level.WARNING,
			            "IOException thrown while closing Closeable.", e);
			      } else {
			        throw e;
			      }
			    }
			  }
	}	
}
