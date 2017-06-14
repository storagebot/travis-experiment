/*
 *   Copyright © 2017 Teclib. All rights reserved.
 *
 *   This file is part of flyve-mdm-android-inventory
 *
 * flyve-mdm-android-inventory is a subproject of Flyve MDM. Flyve MDM is a mobile
 * device management software.
 *
 * Flyve MDM is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * Flyve MDM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @author    Rafael Hernandez
 * @date      07/06/2017
 * @copyright Copyright © 2017 Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/flyve-mdm-android-inventory
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */

package com.teclib.flyveinventorygrap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.flyvemdm.inventory.InventoryTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
    private String TAG = "Agent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *  This is an example of the library implementation
         */

        if(checkAndRequestPermissions()) {
            inventory();
        }

//        // Request Permission
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, CONST_ID);
//        } else {
//            inventory();
//        }

    }

    /**
     * Callback for the result from requesting permissions.
     * @param requestCode int The request code passed in requestPermissions(android.app.Activity, String[], int)
     * @param permissions String: The requested permissions. Never null.
     * @param grantResults int: The grant results for the corresponding permissions which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_ACCOUNTS) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (permission.equals(Manifest.permission.READ_PHONE_STATE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        inventory();
                    } else {
                        Log.e(TAG, "You need permission!");
                    }
                }
            }
        }
    }

    /**
     * Check multiples Pemissions
     * @return Boolean
     */
    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        int permissionPHONE = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionPHONE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }

        return true;
    }

    private void inventory() {
        InventoryTask inventoryTask = new InventoryTask(MainActivity.this, "Agent_v1.0", new InventoryTask.OnTaskCompleted() {
            @Override
            public void onTaskSuccess(String data) {
                Log.d("XML", data);
            }

            @Override
            public void onTaskError(String error) {
                Log.e("Example", error);
            }
        });

        inventoryTask.execute();
    }

}
