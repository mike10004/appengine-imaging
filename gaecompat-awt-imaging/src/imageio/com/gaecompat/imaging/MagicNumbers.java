package com.gaecompat.imaging;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.devlib.schmidt.imageinfo.ImageInfo;
import org.devlib.schmidt.imageinfo.ImageInfo.Format;

public class MagicNumbers {
	
	public static BytePair read(InputStream in) throws IOException {
		byte[] bytes = new byte[2];
		int numRead = in.read(bytes);
		if (numRead != 2) {
			throw new IOException("not enough data");
		}
		return read(bytes);
	}
	
	public static BytePair read(byte[] bytes) {
		if (bytes.length < 2) {
			throw new IllegalArgumentException("byte array must have length >= 2, not " + bytes.length);
		}
		return new BytePair ( bytes[0], bytes[1] );
	}
	
	public static class BytePair {
		public final byte first, second;

		public BytePair(byte first, byte second) {
			super();
			this.first = first;
			this.second = second;
		}
	
		public String toString() {
			return String.format("0x%02X 0x%02X", first, second);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + first;
			result = prime * result + second;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BytePair other = (BytePair) obj;
			if (first != other.first)
				return false;
			if (second != other.second)
				return false;
			return true;
		}
		
		
	}
	
	private static final Map<ImageInfo.Format, List<BytePair>> bimap = createBiMap();
	
	public static List<BytePair> getBytePairs(ImageInfo.Format format) {
		return bimap.get(format);
	}
	
	public static ImageInfo.Format getFormat(BytePair queryBytePair) {
		for (Format format : bimap.keySet()) {
			for (BytePair bytePair : getBytePairs(format)) {
				if (bytePair.equals(queryBytePair)) {
					return format;
				}
			}
		}
		return null;
	}
	
	private static void put(Map<Format, List<BytePair>> map, Format format, int first, int second) {
		List<BytePair> pairs = new ArrayList<BytePair>();
		pairs.add(new BytePair((byte) first, (byte) second));
		map.put(format, Collections.unmodifiableList(pairs));
	}
	
	private static Map<ImageInfo.Format, List<BytePair>> createBiMap() {
		Map<ImageInfo.Format, List<BytePair>> m = new TreeMap<ImageInfo.Format, List<BytePair>>();
		put(m, Format.GIF, 0x47, 0x49);
		put(m, Format.PNG, 0x89, 0x50);
		put(m, Format.JPEG, 0xff, 0xd8);
		put(m, Format.WEBP, 0x52, 0x49);
		put(m, Format.TIFF, 0x49, 0x49);
		return m;
	}
}