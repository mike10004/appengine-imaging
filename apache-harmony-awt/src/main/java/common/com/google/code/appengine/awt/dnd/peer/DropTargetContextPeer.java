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

package com.google.code.appengine.awt.dnd.peer;

import com.google.code.appengine.awt.datatransfer.DataFlavor;
import com.google.code.appengine.awt.datatransfer.Transferable;
import com.google.code.appengine.awt.dnd.DropTarget;
import com.google.code.appengine.awt.dnd.InvalidDnDOperationException;

public interface DropTargetContextPeer {

    int getTargetActions();

    void setTargetActions(int actions);

    DropTarget getDropTarget();

    DataFlavor[] getTransferDataFlavors();

    Transferable getTransferable() throws InvalidDnDOperationException;

    boolean isTransferableJVMLocal();

    void acceptDrag(int dragAction);

    void rejectDrag();

    void acceptDrop(int dropAction);

    void rejectDrop();

    void dropComplete(boolean success);

}