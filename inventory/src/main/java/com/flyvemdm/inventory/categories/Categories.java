/**
 *
 * Copyright 2017 Teclib.
 * Copyright 2010-2016 by the FusionInventory Development
 *
 * http://www.fusioninventory.org/
 * https://github.com/fusioninventory/fusioninventory-android
 *
 * ------------------------------------------------------------------------
 *
 * LICENSE
 *
 * This file is part of FusionInventory project.
 *
 * FusionInventory is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * FusionInventory is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @update    07/06/2017
 * @license   GPLv2 https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * @link      https://github.com/fusioninventory/fusioninventory-android
 * @link      http://www.fusioninventory.org/
 * ------------------------------------------------------------------------------
 */

package com.flyvemdm.inventory.categories;

import android.content.Context;
import android.util.Log;
import com.flyvemdm.inventory.FILog;
import org.xmlpull.v1.XmlSerializer;
import java.io.IOException;
import java.util.ArrayList;

public class Categories extends ArrayList<Category>{


    /*
     * The serialization runtime associates with each serializable class a version number,
     * called a serialVersionUID, which is used during deserialization to verify that the sender
     * and receiver of a serialized object have loaded classes for that object that are compatible
     * with respect to serialization. If the receiver has loaded a class for the object that has a
     * different serialVersionUID than that of the corresponding sender's class, then deserialization
     * will result in an  InvalidClassException
     *
     *  from: https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
     */
    private static final long serialVersionUID = 2278660715848751766L;

    public Context mCtx;


    /**
     * This constructor load the Context of the instance
     * @param xCtx Context where this class work
     */
    public Categories(Context xCtx) {
        mCtx = xCtx;
    }

    /**
     *  Create a XML object
     * @param xSerializer object to create XML
     */
    public void toXML(XmlSerializer xSerializer) {
        for( Category c : this) {
            try {
                c.toXML(xSerializer);
            } catch (IllegalArgumentException | IllegalStateException | IOException e) {
                Log.e(FILog.TAGLOG, e.getMessage());
            }
        }
    }


}
