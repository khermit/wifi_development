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
                "currentTime" + " TEXT ," +

                "SSID" + " TEXT ," +
                "BSSID" + " TEXT ," +
                "MAC" + " TEXT ," +
                "state" + " TEXT ," +
                "RSSI" + " INTEGER ," +
                "linkSpeed" + " INTEGER ," +
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
