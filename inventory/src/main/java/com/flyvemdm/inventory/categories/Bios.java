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
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import com.flyvemdm.inventory.FILog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * This class get all the information of the Bios
 */
public class Bios extends Categories {

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
	private static final long serialVersionUID = -559572118090134691L;

	/**
	 * This constructor trigger get all the information about Bios
	 * @param xCtx Context where this class work
	 */
	public Bios(Context xCtx) {
		super(xCtx);
		Category c = new Category(xCtx, "BIOS");

		// Bios Date
		c.put("BDATE", (String) DateFormat.format("MM/dd/yy", Build.TIME));

		// Bios Manufacturer
		c.put("BMANUFACTURER", Build.MANUFACTURER);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1) {
			c.put("BVERSION", Build.BOOTLOADER);
		}

		// Mother Board Manufacturer
		c.put("MMANUFACTURER", Build.MANUFACTURER);

		// Mother Board Model
		c.put("SMODEL", Build.MODEL);

		if (Build.VERSION.SDK_INT > 9 && !Build.SERIAL.equals(Build.UNKNOWN)) {
			// Mother Board Serial Number
			// Since in 2.3.3 a.k.a gingerbread
		    FILog.d("Serial:" + Build.SERIAL);

			c.put("SSN", Build.SERIAL);
		} else {
			//Try to get the serial by reading /proc/cpuinfo
			String serial = this.getSerialNumberFromCpuinfo();
			if (!serial.equals("") && !serial.equals("0000000000000000")) {
				c.put("SSN", serial);
			} else {
				//Last try, use the hidden API !
				serial = getSerialFromPrivateAPI();
				if (!serial.equals("")) {
					c.put("SSN", serial);
				}
			}
		}

		this.add(c);
	}

	/**
	 * This is a call to a private api to get Serial number
	 * @return String with a Serial Device
	 */
	private String getSerialFromPrivateAPI() {
		String serial = "";
		try {
	        Class<?> c = Class.forName("android.os.SystemProperties");
	        Method get = c.getMethod("get", String.class);
	        serial = (String) get.invoke(c, "ro.serialno");
	    } catch (Exception e) {
			Log.e(FILog.TAGLOG, e.getMessage());
	    }
	    return serial;
	}

	/**
	 * Get the serial by reading /proc/cpuinfo
	 * @return String
	 */
	private String getSerialNumberFromCpuinfo() {
		String serial = "";
		File f = new File("/proc/cpuinfo");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f), 8 * 1024);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("Serial")) {
					FILog.d(line);

					String[] results = line.split(":");
					serial = results[1].trim();
				}
			}
			br.close();

		} catch (IOException e) {
			Log.e(FILog.TAGLOG, e.getMessage());
		}

		return serial.trim();
	}
}
