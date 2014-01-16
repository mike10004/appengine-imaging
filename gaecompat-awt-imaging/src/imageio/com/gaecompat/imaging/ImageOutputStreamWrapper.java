package com.gaecompat.imaging;

import java.io.IOException;
import java.io.OutputStream;

import com.google.code.appengine.imageio.stream.ImageOutputStream;

public class ImageOutputStreamWrapper extends OutputStream {

	private final ImageOutputStream ios;
	
	public ImageOutputStreamWrapper(ImageOutputStream ios) {
		this.ios = ios;
	}
	
	@Override
	public void write(int b) throws IOException {
		ios.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		ios.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		ios.write(b, off, len);
	}

	@Override
	public void flush() throws IOException {
		ios.flush();
	}

	@Override
	public void close() throws IOException {
		ios.close();
	}

	
}
