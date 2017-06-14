/**
 * FILog
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
 * This file is part of FILog project.
 *
 * FILog is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * FILog is distributed in the hope that it will be useful,
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

package com.flyvemdm.inventory;

import android.util.Log;

/**
 * This class is a grapper for Log
 */
public class FILog {

    // This is the tag to search on console
    public static String TAGLOG = "FlyveMDMInventory";

    /**
     * Create a log for debug
     * @param msg String message
     */
    public static void d(String msg) {
        Log.d(TAGLOG, msg);
    }

    /**
     * Create a log for error
     * @param msg String message
     */
    public static void e(String msg) {
        Log.e(TAGLOG, msg);
    }

}