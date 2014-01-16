package com.gaecompat.javax.swing.text;

import java.io.PrintStream;
import java.util.Dictionary;
import java.util.EventListener;

import com.gaecompat.javax.swing.event.DocumentListener;

public class PlainDocument extends AbstractDocument {

	public PlainDocument() {
		super(new Content(){}, new AttributeContext() {});
	}
	
	protected PlainDocument(Content data, AttributeContext context) {
		super(data, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dictionary<Object, Object> getDocumentProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDocumentProperties(Dictionary<Object, Object> x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAsynchronousLoadPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAsynchronousLoadPriority(int p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDocumentFilter(DocumentFilter filter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DocumentFilter getDocumentFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(Runnable r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addDocumentListener(DocumentListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeDocumentListener(DocumentListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DocumentListener[] getDocumentListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUndoableEditListener(UndoableEditListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUndoableEditListener(UndoableEditListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UndoableEditListener[] getUndoableEditListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getProperty(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putProperty(Object key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int offs, int len) throws BadLocationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replace(int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getText(int offset, int length) throws BadLocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getText(int offset, int length, Segment txt)
			throws BadLocationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Position createPosition(int offs) throws BadLocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position getStartPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position getEndPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element[] getRootElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getDefaultRootElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getBidiRootElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getParagraphElement(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dump(PrintStream out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readLock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readUnlock() {
		// TODO Auto-generated method stub
		
	}

}
