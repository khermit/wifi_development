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




        long wifiId = db.insert("Wifi", null, values);
        db.close();
        return (int)wifiId;
    }
}
