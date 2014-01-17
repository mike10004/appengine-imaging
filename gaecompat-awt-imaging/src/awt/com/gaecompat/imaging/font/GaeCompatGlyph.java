package com.gaecompat.imaging.font;

import org.apache.harmony.awt.gl.font.Glyph;

import com.google.code.appengine.awt.Shape;

public class GaeCompatGlyph extends Glyph {

	GaeCompatGlyph(char c, int size) {
        glChar = c;
    }
	
	@Override
	public byte[] getBitmap() {
		// TODO implement getBitmap
		return null;
	}

	@Override
	public Shape initOutline(char c) {
		// TODO implement initOutline
		return null;
	}

}
