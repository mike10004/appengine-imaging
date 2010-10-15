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

import com.google.code.appengine.awt.Color;
import com.google.code.appengine.awt.Font;
import com.google.code.appengine.awt.Graphics2D;
import com.google.code.appengine.awt.RenderingHints;
import com.google.code.appengine.awt.font.FontRenderContext;

/**
 * This test validates the convertion of Java 2D Fonts into
 * SVG font attributes.
 *
 * @author <a href="mailto:cjolif@ilog.fr">Christophe Jolif</a>
 * @author <a href="mailto:vhardy@eng.sun.com">Vincent Hardy</a>
 * @version $Id: Font1.java 482118 2006-12-04 09:52:54Z dvholten $
 */
public class Font1 implements Painter {
    public void paint(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        // Set default font
        g.setFont(new com.google.code.appengine.awt.Font("Arial", com.google.code.appengine.awt.Font.BOLD, 12));

        // Colors used for labels and test output
        Color labelColor = new Color(0x666699);
        Color fontColor = Color.black;

        //
        // First, font size
        //
        com.google.code.appengine.awt.geom.AffineTransform defaultTransform = g.getTransform();
        com.google.code.appengine.awt.Font defaultFont = new com.google.code.appengine.awt.Font("Arial", com.google.code.appengine.awt.Font.BOLD, 16);
        g.setFont(defaultFont);
        FontRenderContext frc = g.getFontRenderContext();
        g.setPaint(labelColor);

        g.drawString("Font size", 10, 30);
        g.setPaint(fontColor);
        g.translate(0, 20);
        int[] fontSizes = { 6, 8, 10, 12, 18, 36, 48 };
        for(int i=0; i<fontSizes.length; i++){
            com.google.code.appengine.awt.Font font = new com.google.code.appengine.awt.Font(defaultFont.getFamily(),
                                 com.google.code.appengine.awt.Font.PLAIN,
                                 fontSizes[i]);
            g.setFont(font);
            g.drawString("aA", 10, 40);
            double width = font.createGlyphVector(frc, "aA").getVisualBounds().getWidth();
            g.translate(width*1.2, 0);
        }

        g.setTransform(defaultTransform);
        g.translate(0, 60);

        //
        // Font style
        //
        int[] fontStyles = { com.google.code.appengine.awt.Font.PLAIN,
                             com.google.code.appengine.awt.Font.BOLD,
                             com.google.code.appengine.awt.Font.ITALIC,
                             com.google.code.appengine.awt.Font.BOLD | com.google.code.appengine.awt.Font.ITALIC };
        String[] fontStyleStrings = { "Plain", "Bold", "Italic", "Bold Italic" };

        g.setFont(defaultFont);
        g.setPaint(labelColor);
        g.drawString("Font Styles", 10, 30);
        g.translate(0, 20);
        g.setPaint(fontColor);

        for(int i=0; i<fontStyles.length; i++){
            com.google.code.appengine.awt.Font font = new com.google.code.appengine.awt.Font(defaultFont.getFamily(),
                                 fontStyles[i], 20);
            g.setFont(font);
            g.drawString(fontStyleStrings[i], 10, 40);
            double width = font.createGlyphVector(frc, fontStyleStrings[i]).getVisualBounds().getWidth();
            g.translate(width*1.2, 0);
        }

        g.setTransform(defaultTransform);
        g.translate(0, 120);

        //
        // Font families
        //
        String[] fontFamilies = { "Arial",
                                  "Times New Roman",
                                  "Courier New",
                                  "Verdana" };

        g.setFont(defaultFont);
        g.setPaint(labelColor);
        g.drawString("Font Families", 10, 30);
        g.setPaint(fontColor);

        for(int i=0; i<fontFamilies.length; i++){
            com.google.code.appengine.awt.Font font = new com.google.code.appengine.awt.Font(fontFamilies[i], com.google.code.appengine.awt.Font.PLAIN, 18);
            g.setFont(font);
            double height = font.createGlyphVector(frc, fontFamilies[i]).getVisualBounds().getHeight();
            g.translate(0, height*1.4);
            g.drawString(fontFamilies[i], 10, 40);
        }

        //
        // Logical fonts
        //
        Font[] logicalFonts = { new com.google.code.appengine.awt.Font("dialog", com.google.code.appengine.awt.Font.PLAIN, 14),
                                new com.google.code.appengine.awt.Font("dialoginput", com.google.code.appengine.awt.Font.BOLD, 14),
                                new com.google.code.appengine.awt.Font("monospaced", com.google.code.appengine.awt.Font.ITALIC, 14),
                                new com.google.code.appengine.awt.Font("serif", com.google.code.appengine.awt.Font.PLAIN, 14),
                                new com.google.code.appengine.awt.Font("sansserif", com.google.code.appengine.awt.Font.BOLD, 14)};

        g.translate(0, 70);
        g.setFont(defaultFont);
        g.setPaint(labelColor);
        g.drawString("Logical Fonts", 10, 0);
        g.setPaint(fontColor);

        for(int i=0; i<logicalFonts.length; i++){
            Font font = logicalFonts[i];
            g.setFont(font);
            double height = font.createGlyphVector(frc, font.getName()).getVisualBounds().getHeight();
            g.translate(0, height*1.4);
            g.drawString(font.getName(), 10, 0);
        }
    }
}
