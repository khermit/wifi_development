package com.example.quandk.wifi_development;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.net.wifi.ScanResult;
//import android.net.wifi.WifiInfo;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by quandk on 17-5-16.
 */

public class WifiActivity extends Activity {
    //Called when the activity is first created
    private TextView allNetWork;
    private TextView tx;
    private Button scan;
    private Button start;
    private Button stop;
    private Button check;
    private WifiAdmin mWifiAdmin;
    private MyLocation mLocation;
    private Location myLocation = null;
    private LocationManager lm = null;
    public Context mContext;
    private MyTime myTime;
    private WifiData d = new WifiData();
    private WifiDataCURD wifiDataCURD;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss.SSSZ");


    //scan_result list
    private List<ScanResult> list;
    private ScanResult mScanResult;
    private StringBuffer sb = new StringBuffer();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public static final int UPDATE_TEXT = 1;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    Log.i("WifiActivity", "Handler___UPDATE____");
                    break;
                case 22://每隔几秒进行数据更新
                    Log.i("WifiActivity", "Handler___22____");
                    allNetWork.setText("2");
                    //allNetWork.setText(setAllInfo());
                    break;
                case 33:
                    Log.i("WifiActivity", "Handler___33____");
                    allNetWork.setText("1");
                    break;
                default:
                    Log.i("WifiActivity", "Handler___dafault____");
                    break;
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);////
        mWifiAdmin = new WifiAdmin(WifiActivity.this);
        mLocation = new MyLocation(WifiActivity.this);
        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Log.i("WifiActivity", "__________onCreate_________");


        Log.i("WifiActivity", "__________1_________");
        //MyThread myThread = new MyThread();
        //myThread.mylooper = Looper.getMainLooper();
        //Log.i("WifiActivity", "__________2_________");
        //new Thread(myThread).start();
        //Log.i("WifiActivity", "__________3_________");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message;

                while (true) {
                    Log.i("WifiActivity", "run_______");
                    message = Message.obtain();
                    message.what = 22;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("WifiActivity", "run____over___");

                    message = Message.obtain();
                    message.what = 33;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //加锁、获取信息、打开文件、写入数据、清楚对象里的数据、解锁
                    int f = 2;
                    if(1==f){//不断切换wifi,并将连接上的wifi数据存入数据库
                        if(mWifiAdmin.connectWifi(mContext)){
                        //if(mWifiAdmin.changeWifi(mContext)){
                            int a_num = 0;
                            while( !mWifiAdmin.isWifiConnected(mContext) && a_num++ < 100 )
                            {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Log.i("WifiActivity", "connect__a_num: " + a_num);
                            }
                            //Log.i("WifiActivity", "connect__: " + mWifiAdmin.isWifiConnected(mContext));
                            if(a_num < 100) {
                                setAllInfo();
                                d.lock = false;
                                d.currentTime = myTime.getTime().toString();
                                int id = wifiDataCURD.insert(d);
                                Log.i("WifiActivity", "_____wifi____id____" + id);
                                Log.i("WifiActivity", "_____wifi____SSID____" + d.SSID);
                                Log.i("WifiActivity", "_____wifi____BSSID____" + d.BSSID);
                                d.lock = true;
                                d.clear();
                            }
                        }
                    }
                    else{
                        int a_num = 0;
                        while( !mWifiAdmin.isWifiConnected(mContext) && a_num++ < 100 )
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.i("WifiActivity", "connect__a_num: " + a_num);
                        }
                        //Log.i("WifiActivity", "connect__: " + mWifiAdmin.isWifiConnected(mContext));
                        if(a_num < 100) {
                            mWifiAdmin.startScan();
                            setAllInfo();
                            d.lock = false;
                            d.currentTime = myTime.getTime().toString();
                            int id = wifiDataCURD.insert(d);
                            Log.i("WifiActivity", "_____wifi____id____" + id);
                            d.lock = true;
                            d.clear();
                        }
                    }
                }
            }
        }).start();
    }


    public void init() {

        allNetWork = (TextView) findViewById(R.id.allNetWork);
        allNetWork.setMovementMethod(ScrollingMovementMethod.getInstance());
        //scan = (Button) findViewById(R.id.scan);
        //start = (Button) findViewById(R.id.start);
        //stop = (Button) findViewById(R.id.stop);
        //check = (Button) findViewById(R.id.check);

        //scan.setOnClickListener(new MyListener());
        //start.setOnClickListener(new MyListener());
        //stop.setOnClickListener(new MyListener());
        //check.setOnClickListener(new MyListener());

        mContext = this;
        myTime = new MyTime();
        wifiDataCURD = new WifiDataCURD(this);

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Log.i("WifiActivity", "____getMyLocation_____");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        tx = (TextView) findViewById(R.id.tx_GPS);
        tx.setText("Buddy, have a nice day!");
        Log.i("WifiActivity", "__________init()_________");
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Wifi Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class MyListener implements View.OnClickListener {
        public void onClick(View v) {
            Log.i("WifiActivity", "__________onClick()_________");
            switch (v.getId()) {
                /*
                case R.id.scan:
                    getAllNetWorkList();
                    Log.i("Activity", "________" + myTime.getTime() + "_____" + myTime.getTimeMillis());
                    break;
                case R.id.start:
                    mWifiAdmin.openWifi();
                    Toast.makeText(WifiActivity.this, "当前wifi状态为："
                            + mWifiAdmin.checkState(), 1).show();
                    break;
                case R.id.stop:
                    mWifiAdmin.closeWifi();
                    Toast.makeText(WifiActivity.this, "当前wifi状态为："
                            + mWifiAdmin.checkState(), 1).show();
                    break;
                case R.id.check:
                    Toast.makeText(WifiActivity.this, "当前wifi状态为："
                            + mWifiAdmin.checkState(), 1).show();
                    break;
                    */
                default:
                    break;
            }
        }
    }

    public void getAllNetWorkList() {
        Log.i("WifiActivity", "__________getAllNetWorkList()_________");
        //clear the last scan result before clicking ererytime
        if (sb != null) {
            sb = new StringBuffer();
        }
        //start to scan network
        mWifiAdmin.startScan();
        list = mWifiAdmin.getmWifiList();
        mWifiAdmin.getWifiInfo(d,mContext);
        //WifiInfo mWifiInfo = mWifiAdmin.getWifiInfo();
        if (null != list) {
            for (int i = 0; i < list.size(); ++i) {
                //for( int i=0; i<1; ++i ) {
                mScanResult = list.get(i);
                sb = sb.append("BSSID:" + mScanResult.BSSID + "  ").append(
                        "SSID:" + mScanResult.SSID + "  ").append(
                        "capabilities:" + mScanResult.capabilities + "  ").append(
                        "frequency:" + mScanResult.frequency + "  ").append(
                        "level:" + mScanResult.level + "  ").append(
                        //"centerFreq0:" + mScanResult.centerFreq0 + "  ").append(
                        //"centerFreq1:" + mScanResult.centerFreq1 + "  ").append(
                        "channelWidth:" + mScanResult.channelWidth + "  ").append(
                        "describeContents:" + mScanResult.describeContents() + "  ").append(
                        //"is80211mcResponder:" + mScanResult.is80211mcResponder() + "  ").append(
                        //"isPasspointNetwork:" + mScanResult.isPasspointNetwork() + "  ").append(
                        // "operatorFriendlyName:" + mScanResult.operatorFriendlyName + "  ").append(
                        //"timestamp:" + mScanResult.timestamp + "  ").append(
                        //"venueName:" + mScanResult.venueName + "  ").append(

                        "IpAddress:" + mWifiAdmin.getIpAddress() + "  ").append(
                        "\n\n"
                );
            }
            //allNetWork.setText("扫描到的wifi网络：\n" + mWifiAdmin.getWifiInfo(d) + sb.toString());
        }
    }

    public String setAllInfo() {
        StringBuffer info = new StringBuffer();
        info.append("Wifi:\n").append(
                mWifiAdmin.getWifiInfo(d, mContext)).append(
                "\n\nCurrentTime:").append(
                myTime.getTime());
        return info.toString();
    }
    //GPS
    private void updateView(Location location){
        if(null == location){
            tx.setText("null");
        }
        else{
            tx.setText(location.toString());
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("LocationListener", "___onLocationChanged");
            //makeUseOfNewLocation(location);
            if(isBetterLocation(location, myLocation)){
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                String provider = location.getProvider();
                float accuracy = location.getAccuracy();
                double altitude = location.getAltitude();
                float bearing = location.getBearing();
                float speed = location.getSpeed();
                String locationTime = sdf.format(new Date(location.getTime()));
                String currentTime = null;
                if(null != myLocation){
                    currentTime = sdf.format(new Date(myLocation.getTime()));
                    myLocation = location;
                }
                else{
                    myLocation = location;
                }

                if(d.lock) {
                    d.Latitude = Double.toString(lat);
                    d.Longitude = Double.toString(lon);
                    d.Provider = provider;
                    d.Accuracy = Float.toString(accuracy);
                    d.Altitude = Double.toString(altitude);
                    d.Bearing = Float.toString(bearing);
                    d.Speed = Float.toString(speed);
                    d.last_time = currentTime;
                    d.new_time = locationTime;
                }

                //获取当前详细地址
                StringBuffer sb = new StringBuffer();
                /*if (myLocation != null)
                {
                    Geocoder gc = new Geocoder(this);
                    List<Address> addresses = null;
                    try
                    {
                        addresses = gc.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
                    }
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (addresses != null && addresses.size() > 0)
                    {
                        Address address = addresses.get(0);
                        sb.append(address.getCountryName() + address.getLocality());
                        sb.append(address.getSubThoroughfare());

                    }
                }*/
                //tx.setText("经度：" + lon + "\n纬度：" + lat + "\n服务商：" + provider + "\n准确性：" + accuracy + "\n高度：" + altitude + "\n方向角：" + bearing
                //       + "\n速度：" + speed + "\n上次上报时间：" + currentTime + "\n最新上报时间：" + locationTime );//+ "\n您所在的城市：" + sb.toString());
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private static final int TWO_MINUTES = 1000 * 60 * 2;
    /**
     * Determines whether one Location reading is better than the current
     * Location fix
     *
     * @param location
     *            The new Location that you want to evaluate
     * @param currentBestLocation
     *            The current Location fix, to which you want to compare the new
     *            one
     */
    protected boolean isBetterLocation(Location location,
                                       Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use
        // the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be
            // worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
                .getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and
        // accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate
                && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

}
