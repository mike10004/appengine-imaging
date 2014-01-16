package com.gaecompat.javax.swing.text;

/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import java.util.*;
import java.io.*;
import java.awt.font.TextAttribute;
import java.text.Bidi;

import com.gaecompat.javax.swing.UIManager;
import com.gaecompat.javax.swing.undo.*;
import com.gaecompat.javax.swing.event.*;
import com.gaecompat.javax.swing.tree.TreeNode;

import sun.font.BidiUtils;
import sun.swing.SwingUtilities2;

/**
 * An implementation of the document interface to serve as a
 * basis for implementing various kinds of documents.  At this
 * level there is very little policy, so there is a corresponding
 * increase in difficulty of use.
 * <p>
 * This class implements a locking mechanism for the document.  It
 * allows multiple readers or one writer, and writers must wait until
 * all observers of the document have been notified of a previous
 * change before beginning another mutation to the document.  The
 * read lock is acquired and released using the <code>render</code>
 * method.  A write lock is aquired by the methods that mutate the
 * document, and are held for the duration of the method call.
 * Notification is done on the thread that produced the mutation,
 * and the thread has full read access to the document for the
 * duration of the notification, but other readers are kept out
 * until the notification has finished.  The notification is a
 * beans event notification which does not allow any further
 * mutations until all listeners have been notified.
 * <p>
 * Any models subclassed from this class and used in conjunction
 * with a text component that has a look and feel implementation
 * that is derived from BasicTextUI may be safely updated
 * asynchronously, because all access to the View hierarchy
 * is serialized by BasicTextUI if the document is of type
 * <code>AbstractDocument</code>.  The locking assumes that an
 * independent thread will access the View hierarchy only from
 * the DocumentListener methods, and that there will be only
 * one event thread active at a time.
 * <p>
 * If concurrency support is desired, there are the following
 * additional implications.  The code path for any DocumentListener
 * implementation and any UndoListener implementation must be threadsafe,
 * and not access the component lock if trying to be safe from deadlocks.
 * The <code>repaint</code> and <code>revalidate</code> methods
 * on JComponent are safe.
 * <p>
 * AbstractDocument models an implied break at the end of the document.
 * Among other things this allows you to position the caret after the last
 * character. As a result of this, <code>getLength</code> returns one less
 * than the length of the Content. If you create your own Content, be
 * sure and initialize it to have an additional character. Refer to
 * StringContent and GapContent for examples of this. Another implication
 * of this is that Elements that model the implied end character will have
 * an endOffset == (getLength() + 1). For example, in DefaultStyledDocument
 * <code>getParagraphElement(getLength()).getEndOffset() == getLength() + 1
 * </code>.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans<sup><font size="-2">TM</font></sup>
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * @author  Timothy Prinzing
 */
public abstract class AbstractDocument implements Document, Serializable, AbstractDocumentI {

	public static interface Content {
		
	}
	
    /**
     * Constructs a new <code>AbstractDocument</code>, wrapped around some
     * specified content storage mechanism.
     *
     * @param data the content
     */
    protected AbstractDocument(Content data) {
        super();
        throw new UnsupportedOperationException();
    }

    /**
     * Constructs a new <code>AbstractDocument</code>, wrapped around some
     * specified content storage mechanism.
     *
     * @param data the content
     * @param context the attribute context
     */
    protected AbstractDocument(Content data, AttributeContext context) {
        throw new UnsupportedOperationException();
//        this.data = data;
//        this.context = context;
//        bidiRoot = new BidiRootElement();
//
//        if (defaultI18NProperty == null) {
//            // determine default setting for i18n support
//            String o = java.security.AccessController.doPrivileged(
//                new java.security.PrivilegedAction<String>() {
//                    public String run() {
//                        return System.getProperty(I18NProperty);
//                    }
//                }
//            );
//            if (o != null) {
//                defaultI18NProperty = Boolean.valueOf(o);
//            } else {
//                defaultI18NProperty = Boolean.FALSE;
//            }
//        }
//        putProperty( I18NProperty, defaultI18NProperty);
//
//        //REMIND(bcb) This creates an initial bidi element to account for
//        //the \n that exists by default in the content.  Doing it this way
//        //seems to expose a little too much knowledge of the content given
//        //to us by the sub-class.  Consider having the sub-class' constructor
//        //make an initial call to insertUpdate.
//        writeLock();
//        try {
//            Element[] p = new Element[1];
//            p[0] = new BidiElement( bidiRoot, 0, 1, 0 );
//            bidiRoot.replace(0,0,p);
//        } finally {
//            writeUnlock();
//        }
    }

	/**
	 * Supports managing a set of properties. Callers
	 * can use the <code>documentProperties</code> dictionary
	 * to annotate the document with document-wide properties.
	 *
	 * @return a non-<code>null</code> <code>Dictionary</code>
	 * @see #setDocumentProperties
	 */
	public abstract Dictionary<Object, Object> getDocumentProperties();

	/**
	 * Replaces the document properties dictionary for this document.
	 *
	 * @param x the new dictionary
	 * @see #getDocumentProperties
	 */
	public abstract void setDocumentProperties(Dictionary<Object, Object> x);

	/**
	 * Returns an array of all the objects currently registered
	 * as <code><em>Foo</em>Listener</code>s
	 * upon this document.
	 * <code><em>Foo</em>Listener</code>s are registered using the
	 * <code>add<em>Foo</em>Listener</code> method.
	 *
	 * <p>
	 * You can specify the <code>listenerType</code> argument
	 * with a class literal, such as
	 * <code><em>Foo</em>Listener.class</code>.
	 * For example, you can query a
	 * document <code>d</code>
	 * for its document listeners with the following code:
	 *
	 * <pre>DocumentListener[] mls = (DocumentListener[])(d.getListeners(DocumentListener.class));</pre>
	 *
	 * If no such listeners exist, this method returns an empty array.
	 *
	 * @param listenerType the type of listeners requested; this parameter
	 *          should specify an interface that descends from
	 *          <code>java.util.EventListener</code>
	 * @return an array of all objects registered as
	 *          <code><em>Foo</em>Listener</code>s on this component,
	 *          or an empty array if no such
	 *          listeners have been added
	 * @exception ClassCastException if <code>listenerType</code>
	 *          doesn't specify a class or interface that implements
	 *          <code>java.util.EventListener</code>
	 *
	 * @see #getDocumentListeners
	 * @see #getUndoableEditListeners
	 *
	 * @since 1.3
	 */
	public abstract <T extends EventListener> T[] getListeners(
			Class<T> listenerType);

	/**
	 * Gets the asynchronous loading priority.  If less than zero,
	 * the document should not be loaded asynchronously.
	 *
	 * @return the asynchronous loading priority, or <code>-1</code>
	 *   if the document should not be loaded asynchronously
	 */
	public abstract int getAsynchronousLoadPriority();

	/**
	 * Sets the asynchronous loading priority.
	 * @param p the new asynchronous loading priority; a value
	 *   less than zero indicates that the document should not be
	 *   loaded asynchronously
	 */
	public abstract void setAsynchronousLoadPriority(int p);

	/**
	 * Sets the <code>DocumentFilter</code>. The <code>DocumentFilter</code>
	 * is passed <code>insert</code> and <code>remove</code> to conditionally
	 * allow inserting/deleting of the text.  A <code>null</code> value
	 * indicates that no filtering will occur.
	 *
	 * @param filter the <code>DocumentFilter</code> used to constrain text
	 * @see #getDocumentFilter
	 * @since 1.4
	 */
	public abstract void setDocumentFilter(DocumentFilter filter);

	/**
	 * Returns the <code>DocumentFilter</code> that is responsible for
	 * filtering of insertion/removal. A <code>null</code> return value
	 * implies no filtering is to occur.
	 *
	 * @since 1.4
	 * @see #setDocumentFilter
	 * @return the DocumentFilter
	 */
	public abstract DocumentFilter getDocumentFilter();

	/**
	 * This allows the model to be safely rendered in the presence
	 * of currency, if the model supports being updated asynchronously.
	 * The given runnable will be executed in a way that allows it
	 * to safely read the model with no changes while the runnable
	 * is being executed.  The runnable itself may <em>not</em>
	 * make any mutations.
	 * <p>
	 * This is implemented to aquire a read lock for the duration
	 * of the runnables execution.  There may be multiple runnables
	 * executing at the same time, and all writers will be blocked
	 * while there are active rendering runnables.  If the runnable
	 * throws an exception, its lock will be safely released.
	 * There is no protection against a runnable that never exits,
	 * which will effectively leave the document locked for it's
	 * lifetime.
	 * <p>
	 * If the given runnable attempts to make any mutations in
	 * this implementation, a deadlock will occur.  There is
	 * no tracking of individual rendering threads to enable
	 * detecting this situation, but a subclass could incur
	 * the overhead of tracking them and throwing an error.
	 * <p>
	 * This method is thread safe, although most Swing methods
	 * are not. Please see
	 * <A HREF="http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html">How
	 * to Use Threads</A> for more information.
	 *
	 * @param r the renderer to execute
	 */
	public abstract void render(Runnable r);

	/**
	 * Returns the length of the data.  This is the number of
	 * characters of content that represents the users data.
	 *
	 * @return the length >= 0
	 * @see Document#getLength
	 */
	public abstract int getLength();

	/**
	 * Adds a document listener for notification of any changes.
	 *
	 * @param listener the <code>DocumentListener</code> to add
	 * @see Document#addDocumentListener
	 */
	public abstract void addDocumentListener(DocumentListener listener);

	/**
	 * Removes a document listener.
	 *
	 * @param listener the <code>DocumentListener</code> to remove
	 * @see Document#removeDocumentListener
	 */
	public abstract void removeDocumentListener(DocumentListener listener);

	/**
	 * Returns an array of all the document listeners
	 * registered on this document.
	 *
	 * @return all of this document's <code>DocumentListener</code>s
	 *         or an empty array if no document listeners are
	 *         currently registered
	 *
	 * @see #addDocumentListener
	 * @see #removeDocumentListener
	 * @since 1.4
	 */
	public abstract DocumentListener[] getDocumentListeners();

	/**
	 * Adds an undo listener for notification of any changes.
	 * Undo/Redo operations performed on the <code>UndoableEdit</code>
	 * will cause the appropriate DocumentEvent to be fired to keep
	 * the view(s) in sync with the model.
	 *
	 * @param listener the <code>UndoableEditListener</code> to add
	 * @see Document#addUndoableEditListener
	 */
	public abstract void addUndoableEditListener(UndoableEditListener listener);

	/**
	 * Removes an undo listener.
	 *
	 * @param listener the <code>UndoableEditListener</code> to remove
	 * @see Document#removeDocumentListener
	 */
	public abstract void removeUndoableEditListener(
			UndoableEditListener listener);

	/**
	 * Returns an array of all the undoable edit listeners
	 * registered on this document.
	 *
	 * @return all of this document's <code>UndoableEditListener</code>s
	 *         or an empty array if no undoable edit listeners are
	 *         currently registered
	 *
	 * @see #addUndoableEditListener
	 * @see #removeUndoableEditListener
	 *
	 * @since 1.4
	 */
	public abstract UndoableEditListener[] getUndoableEditListeners();

	/**
	 * A convenience method for looking up a property value. It is
	 * equivalent to:
	 * <pre>
	 * getDocumentProperties().get(key);
	 * </pre>
	 *
	 * @param key the non-<code>null</code> property key
	 * @return the value of this property or <code>null</code>
	 * @see #getDocumentProperties
	 */
	public abstract Object getProperty(Object key);

	/**
	 * A convenience method for storing up a property value.  It is
	 * equivalent to:
	 * <pre>
	 * getDocumentProperties().put(key, value);
	 * </pre>
	 * If <code>value</code> is <code>null</code> this method will
	 * remove the property.
	 *
	 * @param key the non-<code>null</code> key
	 * @param value the property value
	 * @see #getDocumentProperties
	 */
	public abstract void putProperty(Object key, Object value);

	/**
	 * Removes some content from the document.
	 * Removing content causes a write lock to be held while the
	 * actual changes are taking place.  Observers are notified
	 * of the change on the thread that called this method.
	 * <p>
	 * This method is thread safe, although most Swing methods
	 * are not. Please see
	 * <A HREF="http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html">How
	 * to Use Threads</A> for more information.
	 *
	 * @param offs the starting offset >= 0
	 * @param len the number of characters to remove >= 0
	 * @exception BadLocationException  the given remove position is not a valid
	 *   position within the document
	 * @see Document#remove
	 */
	public abstract void remove(int offs, int len) throws BadLocationException;

	/**
	 * Deletes the region of text from <code>offset</code> to
	 * <code>offset + length</code>, and replaces it with <code>text</code>.
	 * It is up to the implementation as to how this is implemented, some
	 * implementations may treat this as two distinct operations: a remove
	 * followed by an insert, others may treat the replace as one atomic
	 * operation.
	 *
	 * @param offset index of child element
	 * @param length length of text to delete, may be 0 indicating don't
	 *               delete anything
	 * @param text text to insert, <code>null</code> indicates no text to insert
	 * @param attrs AttributeSet indicating attributes of inserted text,
	 *              <code>null</code>
	 *              is legal, and typically treated as an empty attributeset,
	 *              but exact interpretation is left to the subclass
	 * @exception BadLocationException the given position is not a valid
	 *            position within the document
	 * @since 1.4
	 */
	public abstract void replace(int offset, int length, String text,
			AttributeSet attrs) throws BadLocationException;

	/**
	 * Inserts some content into the document.
	 * Inserting content causes a write lock to be held while the
	 * actual changes are taking place, followed by notification
	 * to the observers on the thread that grabbed the write lock.
	 * <p>
	 * This method is thread safe, although most Swing methods
	 * are not. Please see
	 * <A HREF="http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html">How
	 * to Use Threads</A> for more information.
	 *
	 * @param offs the starting offset >= 0
	 * @param str the string to insert; does nothing with null/empty strings
	 * @param a the attributes for the inserted content
	 * @exception BadLocationException  the given insert position is not a valid
	 *   position within the document
	 * @see Document#insertString
	 */
	public abstract void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException;

	/**
	 * Gets a sequence of text from the document.
	 *
	 * @param offset the starting offset >= 0
	 * @param length the number of characters to retrieve >= 0
	 * @return the text
	 * @exception BadLocationException  the range given includes a position
	 *   that is not a valid position within the document
	 * @see Document#getText
	 */
	public abstract String getText(int offset, int length)
			throws BadLocationException;

	/**
	 * Fetches the text contained within the given portion
	 * of the document.
	 * <p>
	 * If the partialReturn property on the txt parameter is false, the
	 * data returned in the Segment will be the entire length requested and
	 * may or may not be a copy depending upon how the data was stored.
	 * If the partialReturn property is true, only the amount of text that
	 * can be returned without creating a copy is returned.  Using partial
	 * returns will give better performance for situations where large
	 * parts of the document are being scanned.  The following is an example
	 * of using the partial return to access the entire document:
	 * <p>
	 * <pre>
	 * &nbsp; int nleft = doc.getDocumentLength();
	 * &nbsp; Segment text = new Segment();
	 * &nbsp; int offs = 0;
	 * &nbsp; text.setPartialReturn(true);
	 * &nbsp; while (nleft > 0) {
	 * &nbsp;     doc.getText(offs, nleft, text);
	 * &nbsp;     // do something with text
	 * &nbsp;     nleft -= text.count;
	 * &nbsp;     offs += text.count;
	 * &nbsp; }
	 * </pre>
	 *
	 * @param offset the starting offset >= 0
	 * @param length the number of characters to retrieve >= 0
	 * @param txt the Segment object to retrieve the text into
	 * @exception BadLocationException  the range given includes a position
	 *   that is not a valid position within the document
	 */
	public abstract void getText(int offset, int length, Segment txt)
			throws BadLocationException;

	/**
	 * Returns a position that will track change as the document
	 * is altered.
	 * <p>
	 * This method is thread safe, although most Swing methods
	 * are not. Please see
	 * <A HREF="http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html">How
	 * to Use Threads</A> for more information.
	 *
	 * @param offs the position in the model >= 0
	 * @return the position
	 * @exception BadLocationException  if the given position does not
	 *   represent a valid location in the associated document
	 * @see Document#createPosition
	 */
	public abstract Position createPosition(int offs)
			throws BadLocationException;

	/**
	 * Returns a position that represents the start of the document.  The
	 * position returned can be counted on to track change and stay
	 * located at the beginning of the document.
	 *
	 * @return the position
	 */
	public abstract Position getStartPosition();

	/**
	 * Returns a position that represents the end of the document.  The
	 * position returned can be counted on to track change and stay
	 * located at the end of the document.
	 *
	 * @return the position
	 */
	public abstract Position getEndPosition();

	/**
	 * Gets all root elements defined.  Typically, there
	 * will only be one so the default implementation
	 * is to return the default root element.
	 *
	 * @return the root element
	 */
	public abstract Element[] getRootElements();

	/**
	 * Returns the root element that views should be based upon
	 * unless some other mechanism for assigning views to element
	 * structures is provided.
	 *
	 * @return the root element
	 * @see Document#getDefaultRootElement
	 */
	public abstract Element getDefaultRootElement();

	/**
	 * Returns the root element of the bidirectional structure for this
	 * document.  Its children represent character runs with a given
	 * Unicode bidi level.
	 */
	public abstract Element getBidiRootElement();

	/**
	 * Get the paragraph element containing the given position.  Sub-classes
	 * must define for themselves what exactly constitutes a paragraph.  They
	 * should keep in mind however that a paragraph should at least be the
	 * unit of text over which to run the Unicode bidirectional algorithm.
	 *
	 * @param pos the starting offset >= 0
	 * @return the element */
	public abstract Element getParagraphElement(int pos);

	/**
	 * Gives a diagnostic dump.
	 *
	 * @param out the output stream
	 */
	public abstract void dump(PrintStream out);

	/**
	 * Acquires a lock to begin reading some state from the
	 * document.  There can be multiple readers at the same time.
	 * Writing blocks the readers until notification of the change
	 * to the listeners has been completed.  This method should
	 * be used very carefully to avoid unintended compromise
	 * of the document.  It should always be balanced with a
	 * <code>readUnlock</code>.
	 *
	 * @see #readUnlock
	 */
	public abstract void readLock();

	/**
	 * Does a read unlock.  This signals that one
	 * of the readers is done.  If there are no more readers
	 * then writing can begin again.  This should be balanced
	 * with a readLock, and should occur in a finally statement
	 * so that the balance is guaranteed.  The following is an
	 * example.
	 * <pre><code>
	 * &nbsp;   readLock();
	 * &nbsp;   try {
	 * &nbsp;       // do something
	 * &nbsp;   } finally {
	 * &nbsp;       readUnlock();
	 * &nbsp;   }
	 * </code></pre>
	 *
	 * @see #readLock
	 */
	public abstract void readUnlock();

	/**
	 * Name of elements used to represent paragraphs
	 */
	public static final String ParagraphElementName = "paragraph";
	/**
	 * Name of elements used to represent content
	 */
	public static final String ContentElementName = "content";
	/**
	 * Name of elements used to hold sections (lines/paragraphs).
	 */
	public static final String SectionElementName = "section";
	/**
	 * Name of elements used to hold a unidirectional run
	 */
	public static final String BidiElementName = "bidi level";
	/**
	 * Name of the attribute used to specify element
	 * names.
	 */
	public static final String ElementNameAttribute = "$ename";

}