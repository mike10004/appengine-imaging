package com.gaecompat.javax.swing.text;

import java.util.Enumeration;

public interface AttributeSet {

	public interface ParagraphAttribute {

	}

	public interface ColorAttribute {

	}

	public interface CharacterAttribute {

	}

	public interface FontAttribute {

	}

	public Object getAttribute(Object key);

	public Enumeration<?> getAttributeNames() ;

}
