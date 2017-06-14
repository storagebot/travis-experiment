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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class get all the information of the Cpus
 */
public class Cpus extends Categories {

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
    private static final long serialVersionUID = 4846706700566208666L;

    /**
     * This constructor trigger get all the information about Cpus
     * @param xCtx Context where this class work
     */
    public Cpus(Context xCtx) {
        super(xCtx);

        Category c = new Category(mCtx, "CPUS");
        c.put("NAME", getCpuName());
        c.put("SPEED", getCpuFrequency());

        this.add(c);
        
    }

    public String getCpuName() {
        FILog.d("Parse /proc/cpuinfo");

        String cpuname = "";
        File f = new File("/proc/cpuinfo");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f), 8 * 1024);
            String infos = br.readLine();
            cpuname = infos.replaceAll("(.*):\\ (.*)", "$2");
            br.close();
        } catch (IOException e) {
            Log.e(FILog.TAGLOG, e.getMessage());
        }
    	return cpuname;
    }
    
    public String getCpuFrequency() {
        String cpuFrequency = "";

    	  FILog.d("Parse /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
      
        File f = new File("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f),8 * 1024);
            String line = br.readLine();
            Integer speed = new Integer(line);
            speed = speed / 1000;
            cpuFrequency = speed.toString();
            br.close();
        } catch (IOException e) {
            Log.e(FILog.TAGLOG, e.getMessage());
        }
    	return cpuFrequency;
    }
}
