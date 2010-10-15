/*

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package org.apache.batik.svggen;

import com.google.code.appengine.awt.Graphics2D;
import com.google.code.appengine.awt.RenderingHints;
import com.google.code.appengine.awt.Font;

/**
 * This test validates convertion of Java 2D Color into SVG fill,
 * stroke and opacity attributes.
 *
 * @author <a href="mailto:cjolif@ilog.fr">Christophe Jolif</a>
 * @author <a href="mailto:vhardy@eng.sun.com">Vincent Hardy</a>
 * @version $Id: Color1.java 504106 2007-02-06 12:31:06Z dvholten $
 */
public class Color1 implements Painter {
    public void paint(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        com.google.code.appengine.awt.geom.AffineTransform defaultTransform = g.getTransform();

        // Colors used for labels and test output
        com.google.code.appengine.awt.Color labelColor = com.google.code.appengine.awt.Color.black;

        com.google.code.appengine.awt.Color[] colorConstants = { com.google.code.appengine.awt.Color.black,
                                   com.google.code.appengine.awt.Color.blue,
                                   com.google.code.appengine.awt.Color.cyan,
                                   com.google.code.appengine.awt.Color.darkGray,
                                   com.google.code.appengine.awt.Color.gray,
                                   com.google.code.appengine.awt.Color.green,
                                   com.google.code.appengine.awt.Color.lightGray,
                                   com.google.code.appengine.awt.Color.magenta,
                                   com.google.code.appengine.awt.Color.orange,
                                   com.google.code.appengine.awt.Color.pink,
                                   com.google.code.appengine.awt.Color.red,
                                   com.google.code.appengine.awt.Color.white,
                                   com.google.code.appengine.awt.Color.yellow };

        String[] colorConstantStrings =  { "black",
                                           "blue",
                                           "cyan",
                                           "darkGray",
                                           "gray",
                                           "green",
                                           "lightGray",
                                           "magenta",
                                           "orange",
                                           "pink",
                                           "red",
                                           "white",
                                           "yellow" };


        g.translate(20, 20);
        g.setPaint(labelColor);
        g.drawString("Color Constants", -5, 0);
        g.translate(0, 20);

        for(int i=0; i<colorConstants.length; i++){
            g.setPaint(labelColor);
            g.drawString(colorConstantStrings[i], 10, 3);
            g.setPaint(colorConstants[i]);
            g.fillRect(-5, -5, 10, 10);
            g.setPaint(labelColor);
            g.drawRect(-5, -5, 10, 10);
            g.translate(0, 20);
        }

        g.setTransform(defaultTransform);
        g.translate(150, 20);
        g.setColor(labelColor);
        g.drawString("Various opacities", 0, 0);
        g.translate(0, 10);

        //
        // Now, test opacities
        //
        int opacitySteps = 20;
        g.setPaint(new com.google.code.appengine.awt.Color(80, 255, 80));
        g.fillRect(0, 0, 40, 260);
        int stepHeight = 260/opacitySteps;
        Font defaultFont = g.getFont();
        Font opacityFont = new Font(defaultFont.getFamily(),
                                    defaultFont.getStyle(),
                                    (int)(defaultFont.getSize()*0.8));
        g.setFont(opacityFont);

        for(int i=0; i<opacitySteps; i++){
            int opacity = ((i + 1)*255)/opacitySteps;
            com.google.code.appengine.awt.Color color = new com.google.code.appengine.awt.Color(0, 0, 0, opacity);
            g.setPaint(color);
            g.fillRect(0, 0, 40, stepHeight);

            g.setPaint(labelColor);
            g.drawString( String.valueOf( opacity ), 50, stepHeight/2);
            g.translate(0, stepHeight);
        }
    }
}
