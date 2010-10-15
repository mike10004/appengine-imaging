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

import com.google.code.appengine.awt.Button;
import com.google.code.appengine.awt.Color;
import com.google.code.appengine.awt.Graphics2D;
import com.google.code.appengine.awt.Image;
import com.google.code.appengine.awt.MediaTracker;
import com.google.code.appengine.awt.RenderingHints;
import com.google.code.appengine.awt.Toolkit;
import com.google.code.appengine.awt.image.BufferedImage;

/**
 * This test validates the convertion of Java 2D RescaleOp
 * into an SVG filer.
 *
 * @author <a href="mailto:cjolif@ilog.fr">Christophe Jolif</a>
 * @author <a href="mailto:vhardy@eng.sun.com">Vincent Hardy</a>
 * @version $Id: Rescale.java 504106 2007-02-06 12:31:06Z dvholten $
 */
public class Rescale implements Painter {
    public void paint(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
        //
        // Load Image
        //
        Image image = Toolkit.getDefaultToolkit().createImage("test-resources/org/apache/batik/svggen/resources/vangogh.jpg");
        MediaTracker tracker = new MediaTracker(new Button(""));
        tracker.addImage(image, 0);
        try{
            tracker.waitForAll();
        }catch(InterruptedException e){
            tracker.removeImage(image);
            image = null;
        }finally {
            if(image != null)
                tracker.removeImage(image);
            if(tracker.isErrorAny())
                image = null;
            if(image != null){
                if(image.getWidth(null)<0 ||
                   image.getHeight(null)<0)
                    image = null;
            }
        }

        if(image == null){
            throw new Error("Could not load image");
        }

        BufferedImage bi = new BufferedImage(image.getWidth(null),
                                             image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D ig = bi.createGraphics();
        ig.drawImage(image, 0, 0, null);

        com.google.code.appengine.awt.image.RescaleOp brighten = new com.google.code.appengine.awt.image.RescaleOp(1.5f, 0, null);
        com.google.code.appengine.awt.image.RescaleOp darken = new com.google.code.appengine.awt.image.RescaleOp(.6f, 0, null);

        // Simply paint the image without and with rescale filters
        g.setPaint(Color.black);
        g.drawString("Brighter / Normal / Darker", 10, 20);
        g.drawImage(bi, brighten, 10, 30);
        g.drawImage(image, 10 + bi.getWidth() + 10, 30, null);
        g.drawImage(bi, darken, 10 + 2*(bi.getWidth() + 10), 30);

        g.translate(0, bi.getHeight() + 30 + 20);
        g.drawString("Rescale Red / Green / Blue", 10, 20);
        com.google.code.appengine.awt.image.RescaleOp redStress = new com.google.code.appengine.awt.image.RescaleOp(new float[]{ 2.0f, 1.0f, 1.0f },
                                            new float[]{ 0, 0, 0 }, null);
        com.google.code.appengine.awt.image.RescaleOp greenStress = new com.google.code.appengine.awt.image.RescaleOp(new float[]{ 1.0f, 2.0f, 1.0f },
                                              new float[]{ 0, 0, 0 }, null);
        com.google.code.appengine.awt.image.RescaleOp blueStress = new com.google.code.appengine.awt.image.RescaleOp(new float[]{ 1.0f, 1.0f, 2.0f },
                                             new float[]{ 0, 0, 0 }, null);

        g.drawImage(bi, redStress, 10, 30);
        g.drawImage(bi, greenStress, 10 + bi.getWidth() + 10, 30);
        g.drawImage(bi, blueStress, 10 + 2*(bi.getWidth() + 10), 30);
    }
}