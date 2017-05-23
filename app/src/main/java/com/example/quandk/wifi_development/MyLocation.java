package com.example.quandk.wifi_development;
import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
/**
 * Created by quandk on 17-5-16.
 */

public class MyLocation {
    private LocationManager mLocationManager;
    private Location location = null;

    public MyLocation(Context context){
        mLocationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
    }

    //判断是否开启GPS
    public static final boolean isOpen(Context context){
        Log.i("MyLocation", "______isOpen________");
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if ( gps )
            return true;
        else
            return false;
    }

    //强制帮用户打开GPS
    public static final void openGPS(Context context){
        Log.i("MyLocation", "______openGPS________");
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try{
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch(PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public void getAllProvidres(){
        //List<String> providersNames = mLocationManager.getAllProviders().toString();
        Log.i("Location", "_____" + mLocationManager.getAllProviders().toString());
        //过滤
        Criteria cri = new Criteria();
        cri.setBearingRequired(true);
        cri.setCostAllowed(false);
        cri.setSpeedRequired(true);
        Log.i("Location", "_____" + "Criteria:" + mLocationManager.getAllProviders().toString());
    }

    //获取位置信息
    public String getMyLocation(Context context){
        //显示地检查是否有GPS权限
        Log.i("MyLocation", "____getMyLocation_____");
        if(ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED){
            LocationManager myLocationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            Log.i("MyLocation", "____MyLocationManager___:"+myLocationManager.toString());
            int flag = 0;
            Log.d("MyLocation", "1____flag:" + flag);
            location = null;
            while(null == location || flag++<6 ){
                location = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            Log.d("MyLocation", "2____flag:" + flag);
            if(null == location){
                while(null == location || flag++<12 ){
                    location = myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
            Log.d("MyLocation", "3____flag:" + flag);
            if(null == location){
                Log.d("MyLocation", "____return null");
                return "null";
            }
            else{
                Log.i("MyLocation", "Location: "+ location.toString());
                return location.toString();
            }
        }
        else {
            Log.i("MyLocation", "_____getMyLocation permission denied");
            return "permission denied";
        }

    }
}
