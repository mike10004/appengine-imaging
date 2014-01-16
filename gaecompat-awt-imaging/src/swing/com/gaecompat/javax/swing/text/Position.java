package com.gaecompat.javax.swing.text;

public class Position {

	public static class Bias {
		public static final Bias Forward = new Bias();
		public static final Bias Backward = new Bias();
	}

	public int getOffset() {
		throw new UnsupportedOperationException();
	}
}
