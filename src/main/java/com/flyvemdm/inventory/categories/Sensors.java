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
import android.hardware.Sensor;
import android.hardware.SensorManager;
import com.flyvemdm.inventory.FILog;
import java.util.List;

/**
 * This class get all the information of the Sensor Devices
 */
public class Sensors extends Categories {

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
	 * This constructor load the context and the Sensors information
	 * @param xCtx Context where this class work
	 */
	public Sensors(Context xCtx) {
		super(xCtx);
		SensorManager sensorManager = (SensorManager) mCtx
				.getSystemService(Context.SENSOR_SERVICE);

		List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
		FILog.d("Get sensors ");

		for (Sensor s : sensors) {
			Category c = new Category(mCtx, "SENSORS");
			c.put("NAME", s.getName());
			c.put("MANUFACTURER", s.getVendor());
			int type = s.getType();
			String strtype = "";
			switch (type) {
				case Sensor.TYPE_ACCELEROMETER:
					strtype = "ACCELEROMETER";
					break;
				case Sensor.TYPE_GRAVITY:
					strtype = "GRAVITY";
					break;
				case Sensor.TYPE_GYROSCOPE:
					strtype = "GYROSCOPE";
					break;
				case Sensor.TYPE_LINEAR_ACCELERATION:
					strtype = "LINEAR ACCELERATION";
					break;
				case Sensor.TYPE_MAGNETIC_FIELD:
					strtype = "MAGNETIC FIELD";
					break;
				case Sensor.TYPE_ORIENTATION:
					strtype = "ORIENTATION";
					break;
				case Sensor.TYPE_PRESSURE:
					strtype = "PRESSURE";
					break;
				case Sensor.TYPE_PROXIMITY:
					strtype = "PROXIMITY";
					break;
				case Sensor.TYPE_ROTATION_VECTOR:
					strtype = "ROTATION VECTOR";
					break;
				case Sensor.TYPE_TEMPERATURE:
					strtype = "TEMPERATURE";
					break;
				default:
					strtype = "";
					break;
			}
			c.put("TYPE", strtype);
			Float f = s.getPower();
			c.put("POWER", f.toString());
			Integer version = s.getVersion();
			c.put("VERSION", version.toString());
			this.add(c);
		}
	}
}
