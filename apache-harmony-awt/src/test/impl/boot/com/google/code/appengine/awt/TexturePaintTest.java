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

import com.google.code.appengine.awt.Rectangle;
import com.google.code.appengine.awt.TexturePaint;
import com.google.code.appengine.awt.image.BufferedImage;

import junit.framework.TestCase;

public class TexturePaintTest extends TestCase {

    public void testConstructorBad() {
        // Regression HARMONY-1471
        try {
            new TexturePaint(null, new Rectangle());
            fail("expected NPE");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            new TexturePaint(new BufferedImage(10, 10,
                    BufferedImage.TYPE_INT_ARGB), null);
            fail("expected NPE");
        } catch (NullPointerException e) {
            // expected
        }
    }
}