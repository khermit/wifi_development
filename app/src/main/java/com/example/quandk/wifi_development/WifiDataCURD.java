package com.example.quandk.wifi_development;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by quandk on 17-5-17.
 */

public class WifiDataCURD {
    private DBHelper dbHelper;
    private DatabaseContext dbContext;
    public WifiDataCURD(Context context){
        dbContext = new DatabaseContext(context, "1Wifi");
        dbHelper = new DBHelper(dbContext, "wifi.db", null, 1);
    }
    public int insert(WifiData d){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(d.tcurrentTime, d.currentTime);

        values.put(d.wSSID, d.SSID);
        values.put(d.wBSSID, d.BSSID);
        values.put(d.wMAC, d.MAC);
        values.put(d.wstate, d.state);
        values.put(d.wRSSI, d.RSSI);
        values.put(d.wlinkSpeed, d.linkSpeed);
        values.put(d.wFrequency, d.Frequency);
        values.put(d.wNetID, d.NetID);
        values.put(d.wMetered, d.Metered);
        values.put(d.wscore, d.score);
        values.put(d.wHidden, d.Hidden);
        values.put(d.wIpAddr, d.IpAddr);
        values.put(d.lAltitude, d.Altitude);
        values.put(d.lLongitude, d.Longitude);
        values.put(d.lProvider, d.Provider);
        values.put(d.lAccuracy, d.Accuracy);
        values.put(d.lLatitude, d.Latitude);
        values.put(d.lBearing, d.Bearing);
        values.put(d.lSpeed, d.Speed);
        values.put(d.llast_time, d.last_time);
        values.put(d.lnew_time, d.new_time);
        values.put(d.wRSSI0, d.RSSI_arr[0]);
        values.put(d.wRSSI1, d.RSSI_arr[1]);
        values.put(d.wRSSI2, d.RSSI_arr[2]);
        values.put(d.wRSSI3, d.RSSI_arr[3]);
        values.put(d.wRSSI4, d.RSSI_arr[4]);
        values.put(d.wRSSI5, d.RSSI_arr[5]);
        values.put(d.wRSSI6, d.RSSI_arr[6]);
        values.put(d.wRSSI7, d.RSSI_arr[7]);
        values.put(d.wRSSI8, d.RSSI_arr[8]);
        values.put(d.wRSSI9, d.RSSI_arr[9]);
        values.put(d.wRSSI10, d.RSSI_arr[10]);
        values.put(d.wRSSI11, d.RSSI_arr[11]);
        values.put(d.wRSSI12, d.RSSI_arr[12]);
        values.put(d.wRSSI13, d.RSSI_arr[13]);
        values.put(d.wRSSI14, d.RSSI_arr[14]);
        values.put(d.wRSSI15, d.RSSI_arr[15]);




        long wifiId = db.insert("Wifi", null, values);
        db.close();
        return (int)wifiId;
    }
}
