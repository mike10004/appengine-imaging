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

/**
 * @author Dennis Ushakov
 */

package javax.accessibility;

import com.google.code.appengine.awt.Color;
//import com.google.code.appengine.awt.Cursor;
import com.google.code.appengine.awt.Dimension;
import com.google.code.appengine.awt.Font;
import com.google.code.appengine.awt.FontMetrics;
import com.google.code.appengine.awt.Point;
import com.google.code.appengine.awt.Rectangle;
//import com.google.code.appengine.awt.event.FocusListener;

public interface AccessibleComponent {
    Color getBackground();
    void setBackground(final Color c);
    Color getForeground();
    void setForeground(Color c);
//    Cursor getCursor();
//    void setCursor(Cursor cursor);
    Font getFont();
    void setFont(Font f);
    FontMetrics getFontMetrics(Font f);
    boolean isEnabled();
    void setEnabled(boolean b);
    boolean isVisible();
    void setVisible(boolean b);
    boolean isShowing();
    boolean contains(Point p);
    Point getLocationOnScreen();
    Point getLocation();
    void setLocation(Point p);
    Rectangle getBounds();
    void setBounds(Rectangle r);
    Dimension getSize();
    void setSize(Dimension d);
    Accessible getAccessibleAt(Point p);
    boolean isFocusTraversable();
    void requestFocus();
//    void addFocusListener(FocusListener l);
//    void removeFocusListener(FocusListener l);
}
