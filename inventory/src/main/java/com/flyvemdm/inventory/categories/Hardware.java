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

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import android.text.format.DateFormat;

import java.util.Properties;

/**
 * This class get all the information of the Environment
 */
public class Hardware
        extends Categories {

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
    private static final long serialVersionUID = 3528873342443549732L;

    /**
     * This constructor load the context and the Hardware information
     * @param xCtx Context where this class work
     */
    public Hardware(Context xCtx) {
        super(xCtx);
        
        Properties props = System.getProperties();
        
        ActivityManager activityManager = (ActivityManager) mCtx.getSystemService(Service.ACTIVITY_SERVICE);
        MemoryInfo info = new MemoryInfo();
        activityManager.getMemoryInfo(info);
        
        Category c = new Category(mCtx,"HARDWARE");
        
        c.put("CHECKSUM" , String.valueOf(0xFFFF));
        c.put("DATELASTLOGGEDUSER",String.valueOf(DateFormat.format("MM/dd/yy", Build.TIME)) );
        if (!Build.USER.equals(Build.UNKNOWN)) {
            c.put("LASTLOGGEDUSER",Build.USER);
        } else { 
            String user = props.getProperty("user.name");
            if (!user.equals("")) {
                c.put("LASTLOGGEDUSER", props.getProperty("user.name"));

            }
        }

        c.put("NAME", Build.MODEL);
        c.put("OSNAME", "Android");
        c.put("OSVERSION", Build.VERSION.RELEASE);
        c.put("ARCHNAME", (String)props.getProperty("os.arch"));
        c.put("SDK", Integer.valueOf(Build.VERSION.SDK_INT).toString());

        String deviceId = Secure.getString(xCtx.getContentResolver(), Secure.ANDROID_ID);
        c.put("UUID", deviceId);
        
        //For OCS compatibility
        Memory memory = new Memory(xCtx);
        c.put("MEMORY", memory.getCapacity());

        Cpus cpu = new Cpus(xCtx);
        c.put("PROCESSORT", cpu.getCpuName());
        c.put("PROCESSORS", cpu.getCpuFrequency());
        this.add(c);

    }
}
