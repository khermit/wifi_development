package com.example.quandk.wifi_development;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by quandk on 17-5-17.
 */

public class DBHelper extends SQLiteOpenHelper {
    //Database version
    private static final int DDTABASE_VERSION = 3;
    private static final String DATABASE_NAME = "wifi.db";

    public DBHelper(Context context, String databasename, SQLiteDatabase.CursorFactory factory, int version){
        super(context, databasename, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE_STUDENT="CREATE TABLE wifi ( " +
                "WifiId" + " INTEGER PRIMARY KEY AUTOINCREMENT ," +

                "SSID" + " TEXT ," +
                "MAC" + " TEXT ," +
                "state" + " TEXT ," +
                "currentTime" + " TEXT ," +
                "BSSID" + " TEXT ," +


                "RSSI" + " INTEGER ," +
                "linkSpeed" + " INTEGER ," +

                "RSSI0" + " INTEGER ," +
                "RSSI1" + " INTEGER ," +
                "RSSI2" + " INTEGER ," +
                "RSSI3" + " INTEGER ," +
                "RSSI4" + " INTEGER ," +
                "RSSI5" + " INTEGER ," +
                "RSSI6" + " INTEGER ," +
                "RSSI7" + " INTEGER ," +
                "RSSI8" + " INTEGER ," +
                "RSSI9" + " INTEGER ," +
                "RSSI10" + " INTEGER ," +
                "RSSI11" + " INTEGER ," +
                "RSSI12" + " INTEGER ," +
                "RSSI13" + " INTEGER ," +
                "RSSI14" + " INTEGER ," +
                "RSSI15" + " INTEGER ," +

                "Frequency" + " INTEGER ," +
                "NetID" + " INTEGER ," +
                "Metered" + " TEXT ," +
                "score" + " TEXT ," +
                "Hidden" + " TEXT ," +
                "IpAddr" + " TEXT ," +

                "Altitude" + " TEXT ," +
                "Longitude" + " TEXT ," +
                "Provider" + " TEXT ," +
                "Accuracy" + " TEXT ," +
                "Latitude" + " TEXT ," +
                "Bearing" + " TEXT ," +
                "Speed" + " TEXT ," +
                "last_time" + " TEXT ," +
                "new_time" + " TEXT)";

        db.execSQL(CREATE_TABLE_STUDENT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS WifiDatabase");
        onCreate(db);
     }
}
