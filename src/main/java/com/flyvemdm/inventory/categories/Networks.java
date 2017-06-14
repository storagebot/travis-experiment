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

import android.app.Service;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.flyvemdm.inventory.FILog;

/**
 * This class get all the information of the Network
 */
public class Networks extends Categories {

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
	private static final long serialVersionUID = 6829495385432791427L;

	/**
	 * This constructor load the context and the Networks information
	 * @param xCtx Context where this class work
	 */
	public Networks(Context xCtx) {
		super(xCtx);

		WifiManager pWM = (WifiManager) xCtx.getSystemService(Service.WIFI_SERVICE);
		boolean wasWifiEnabled = pWM.isWifiEnabled();

		// Enable Wifi State if not
		if (!wasWifiEnabled) {
			pWM.setWifiEnabled(true);
		}
		Category c = new Category(xCtx, "NETWORKS");
		c.put("TYPE", "WIFI");

		DhcpInfo dhcp = pWM.getDhcpInfo();
		WifiInfo wifi = pWM.getConnectionInfo();

		FILog.d("<===WIFI INFO===>");
		if (wifi.getMacAddress() != null) {
			FILog.d("Mac Adress=" + wifi.getMacAddress());
			c.put("MACADDR", wifi.getMacAddress());
		}
		FILog.d("Link Speed=" + wifi.getLinkSpeed() + WifiInfo.LINK_SPEED_UNITS);
		c.put("SPEED", String.valueOf(wifi.getLinkSpeed()));
		if (wifi.getBSSID() != null) {
			c.put("BSSID", String.valueOf(wifi.getBSSID()));
		}
		if (wifi.getSSID() != null) {
			c.put("SSID", String.valueOf(wifi.getBSSID()));
		}

		FILog.d("<===WIFI DHCP===>");
		FILog.d("dns1=" + StringUtils.int_to_ip(dhcp.dns1));
		FILog.d("dns2=" + StringUtils.int_to_ip(dhcp.dns2));
		FILog.d("gateway=" + StringUtils.int_to_ip(dhcp.gateway));
		c.put("IPGATEWAY", StringUtils.int_to_ip(dhcp.gateway));

		FILog.d("ipAddress=" + StringUtils.int_to_ip(dhcp.ipAddress));
		c.put("IPADDRESS", StringUtils.int_to_ip(dhcp.ipAddress));

		FILog.d("leaseDuration=" + dhcp.leaseDuration);
		FILog.d("netmask=" + StringUtils.int_to_ip(dhcp.netmask));
		c.put("IPMASK", StringUtils.int_to_ip(dhcp.netmask));

		FILog.d("serverAdress=" + StringUtils.int_to_ip(dhcp.serverAddress));
		c.put("IPDHCP", StringUtils.int_to_ip(dhcp.serverAddress));

		this.add(c);
		// Restore Wifi State
		if (!wasWifiEnabled) {
			pWM.setWifiEnabled(false);
		}

		/*
		 * ConnectivityManager CM = (ConnectivityManager)
		 * mCtx.getSystemService(Service.CONNECTIVITY_SERVICE);
		 * 
		 * NetworkInfo[] list = CM.getAllNetworkInfo();
		 * 
		 * 
		 * for( NetworkInfo e : list ) { c = new Category(xCtx, "NETWORKS");
		 * c.put("TYPE", e.getTypeName()); c.put("SUBTYPE", e.getSubtypeName());
		 * c.put("STATE",e.getState().toString());
		 * 
		 * this.add(c); }
		 */

		// TODO Use java.net.NetworkInterface with android 2.3+
		// (More informations will be unlocked)
		/*
		 * Enumeration<NetworkInterface> pNetIfs = null; NetworkInterface pNetIf
		 * = null; try { pNetIfs = NetworkInterface.getNetworkInterfaces(); }
		 * catch (SocketException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * 
		 * if(pNetIfs != null) { while (pNetIfs.hasMoreElements()) { //Category
		 * c = new Category(xCtx, "NETWORKS"); pNetIf = pNetIfs.nextElement();
		 * if(pNetIf != null) { //c.put("DESCRIPTION", pNetIf.getDisplayName());
		 * 
		 * 
		 * FILog.d(this, "DESCRIPTION = "+pNetIf.getDisplayName() ,
		 * Log.VERBOSE);
		 * 
		 * InetAddress inet; Enumeration<InetAddress> inets =
		 * pNetIf.getInetAddresses(); while ( inets.hasMoreElements()) { inet =
		 * inets.nextElement(); FILog.d(this,
		 * "|-> INET = "+inet.getHostAddress() , Log.VERBOSE);
		 * FILog.d(this, "|-> HOST = "+inet.getHostName() ,
		 * Log.VERBOSE);
		 * 
		 * }
		 * 
		 * } }
		 * 
		 * }
		 */
	}

}
