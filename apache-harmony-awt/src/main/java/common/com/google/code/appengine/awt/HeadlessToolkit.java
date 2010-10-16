/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.google.code.appengine.awt;

import java.util.Map;
import java.util.Properties;

import org.apache.harmony.awt.ComponentInternals;
import org.apache.harmony.awt.datatransfer.DTK;
import org.apache.harmony.awt.wtk.GraphicsFactory;
import org.apache.harmony.awt.wtk.NativeEventQueue;
import org.apache.harmony.awt.wtk.WindowFactory;

import com.google.code.appengine.awt.Button;
import com.google.code.appengine.awt.Checkbox;
import com.google.code.appengine.awt.CheckboxMenuItem;
import com.google.code.appengine.awt.Choice;
import com.google.code.appengine.awt.Component;
import com.google.code.appengine.awt.Cursor;
import com.google.code.appengine.awt.Dialog;
import com.google.code.appengine.awt.Dimension;
import com.google.code.appengine.awt.EventQueue;
import com.google.code.appengine.awt.FileDialog;
import com.google.code.appengine.awt.Frame;
import com.google.code.appengine.awt.GraphicsConfiguration;
import com.google.code.appengine.awt.HeadlessException;
import com.google.code.appengine.awt.Image;
import com.google.code.appengine.awt.Insets;
import com.google.code.appengine.awt.JobAttributes;
import com.google.code.appengine.awt.Label;
import com.google.code.appengine.awt.List;
import com.google.code.appengine.awt.Menu;
import com.google.code.appengine.awt.MenuBar;
import com.google.code.appengine.awt.MenuItem;
import com.google.code.appengine.awt.PageAttributes;
import com.google.code.appengine.awt.Point;
import com.google.code.appengine.awt.PopupMenu;
import com.google.code.appengine.awt.PrintJob;
import com.google.code.appengine.awt.ScrollPane;
import com.google.code.appengine.awt.Scrollbar;
import com.google.code.appengine.awt.TextArea;
import com.google.code.appengine.awt.TextField;
import com.google.code.appengine.awt.Window;
import com.google.code.appengine.awt.datatransfer.Clipboard;
import com.google.code.appengine.awt.dnd.DragGestureEvent;
import com.google.code.appengine.awt.dnd.DragGestureListener;
import com.google.code.appengine.awt.dnd.DragGestureRecognizer;
import com.google.code.appengine.awt.dnd.DragSource;
import com.google.code.appengine.awt.dnd.InvalidDnDOperationException;
import com.google.code.appengine.awt.dnd.peer.DragSourceContextPeer;
import com.google.code.appengine.awt.im.InputMethodHighlight;
import com.google.code.appengine.awt.image.ColorModel;
import com.google.code.appengine.awt.peer.ButtonPeer;
import com.google.code.appengine.awt.peer.CheckboxMenuItemPeer;
import com.google.code.appengine.awt.peer.CheckboxPeer;
import com.google.code.appengine.awt.peer.ChoicePeer;
import com.google.code.appengine.awt.peer.DialogPeer;
import com.google.code.appengine.awt.peer.FileDialogPeer;
import com.google.code.appengine.awt.peer.FramePeer;
import com.google.code.appengine.awt.peer.LabelPeer;
import com.google.code.appengine.awt.peer.ListPeer;
import com.google.code.appengine.awt.peer.MenuBarPeer;
import com.google.code.appengine.awt.peer.MenuItemPeer;
import com.google.code.appengine.awt.peer.MenuPeer;
import com.google.code.appengine.awt.peer.PopupMenuPeer;
import com.google.code.appengine.awt.peer.ScrollPanePeer;
import com.google.code.appengine.awt.peer.ScrollbarPeer;
import com.google.code.appengine.awt.peer.TextAreaPeer;
import com.google.code.appengine.awt.peer.TextFieldPeer;
import com.google.code.appengine.awt.peer.WindowPeer;


final class HeadlessToolkit extends ToolkitImpl {

    private class EventMonitor {}
    private final Object eventMonitor = new EventMonitor();
    
    @Override
    protected ButtonPeer createButton(Button a0) throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    protected CheckboxPeer createCheckbox(Checkbox a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem a0) throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    protected ChoicePeer createChoice(Choice a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    public Cursor createCustomCursor(Image img, Point hotSpot, String name)
    throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected DialogPeer createDialog(Dialog a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    public <T extends DragGestureRecognizer> T createDragGestureRecognizer(
            Class<T> recognizerAbstractClass, DragSource ds, Component c, int srcActions,
            DragGestureListener dgl) {
        return null;
    }

    @Override
    public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent dge)
    throws InvalidDnDOperationException {
        throw new InvalidDnDOperationException();
    }

    @Override
    protected FileDialogPeer createFileDialog(FileDialog a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected FramePeer createFrame(Frame a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected LabelPeer createLabel(Label a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected ListPeer createList(List a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected MenuPeer createMenu(Menu a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected MenuBarPeer createMenuBar(MenuBar a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected MenuItemPeer createMenuItem(MenuItem a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected PopupMenuPeer createPopupMenu(PopupMenu a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected ScrollbarPeer createScrollbar(Scrollbar a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected ScrollPanePeer createScrollPane(ScrollPane a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected TextAreaPeer createTextArea(TextArea a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected TextFieldPeer createTextField(TextField a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    protected WindowPeer createWindow(Window a0) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    public Dimension getBestCursorSize(int prefWidth, int prefHeight) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    public ColorModel getColorModel() throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    public final Object getEventMonitor() {
        return eventMonitor;
    }

    @Override
    GraphicsFactory getGraphicsFactory() throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    public boolean getLockingKeyState(int keyCode) throws UnsupportedOperationException {
        throw new HeadlessException();
    }
    
    @Override
    public int getMaximumCursorColors() throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    public int getMenuShortcutKeyMask() throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    NativeEventQueue getNativeEventQueue() throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    public PrintJob getPrintJob(Frame frame, String jobtitle, JobAttributes jobAttributes, 
            PageAttributes pageAttributes) throws IllegalArgumentException {
        throw new IllegalArgumentException();        
    }
    
    @Override
    public PrintJob getPrintJob(Frame frame, String jobtitle, Properties props) throws NullPointerException  {
        throw new NullPointerException();
    }
    
    @Override
    public Insets getScreenInsets(GraphicsConfiguration gc) throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    public int getScreenResolution() throws HeadlessException {
        throw new HeadlessException();
    }    
    
    @Override
    public Dimension getScreenSize() throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    public Clipboard getSystemClipboard() throws HeadlessException {
        throw new HeadlessException();
    }    
    
    @Override
    public Clipboard getSystemSelection() throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    WindowFactory getWindowFactory() throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    void init() {
        lockAWT();
        try {
            ComponentInternals.setComponentInternals(new ComponentInternalsImpl());
            new EventQueue(this); // create the system EventQueue
            dispatcher = new Dispatcher(this);
            awtEventsManager = new AWTEventsManager();
            dispatchThread = new HeadlessEventDispatchThread(this, dispatcher);            
            dtk = DTK.getDTK();
//            dispatchThread.start();
        } finally {
            unlockAWT();
        }
    }
    
    @Override
    public boolean isDynamicLayoutActive() throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    protected boolean isDynamicLayoutSet() throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    public boolean isFrameStateSupported(int state) throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    protected void loadSystemColors(int[] systemColors) throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    public Map<com.google.code.appengine.awt.font.TextAttribute, ?> mapInputMethodHighlight(
            InputMethodHighlight highlight) throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    Map<com.google.code.appengine.awt.font.TextAttribute, ?> mapInputMethodHighlightImpl(
            InputMethodHighlight highlight) throws HeadlessException {
        throw new HeadlessException();
    }

    @Override
    public void setDynamicLayout(boolean dynamic) throws HeadlessException {
        throw new HeadlessException();
    }
    
    @Override
    public void setLockingKeyState(int keyCode, boolean on) throws UnsupportedOperationException {
        throw new HeadlessException();
    }
}
