package com.example.quandk.wifi_development;

/**
 * Created by quandk on 17-5-16.
 */

public class WifiData {
    public Boolean lock = true;

    public String Latitude = "null";
    public String Longitude = "null";
    public String Provider = "null";
    public String Accuracy = "null";
    public String Altitude = "null";
    public String Bearing = "null";
    public String Speed = "null";
    public String last_time = "null";
    public String new_time = "null";

    public String SSID = "null";
    public String BSSID = "null";
    public String MAC = "null";
    public String state = "null";
    public int RSSI = 0;
    public int linkSpeed = 0;
    public int Frequency = 0;
    public int NetID = 0;
    public String Metered = "null";
    public String score = "null";
    public boolean Hidden = false;
    public int IpAddr = 0;

    public String currentTime = "null";

    public String lLatitude = "Latitude";
    public String lLongitude = "Longitude";
    public String lProvider = "Provider";
    public String lAccuracy = "Accuracy";
    public String lAltitude = "Altitude";
    public String lBearing = "Bearing";
    public String lSpeed = "Speed";
    public String llast_time = "last_time";
    public String lnew_time = "new_time";

    public String wSSID = "SSID";
    public String wBSSID = "BSSID";
    public String wMAC = "MAC";
    public String wstate = "state";
    public String wRSSI = "RSSI";
    public String wlinkSpeed = "linkSpeed";
    public String wFrequency = "Frequency";
    public String wNetID = "NetID";
    public String wMetered = "Metered";
    public String wscore = "score";
    public String wHidden = "Hidden";
    public String wIpAddr = "IpAddr";

    public String tcurrentTime = "currentTime";

    public String wRSSI0 = "RSSI0";
    public String wRSSI1 = "RSSI1";
    public String wRSSI2 = "RSSI2";
    public String wRSSI3 = "RSSI3";
    public String wRSSI4 = "RSSI4";
    public String wRSSI5 = "RSSI5";
    public String wRSSI6 = "RSSI6";
    public String wRSSI7 = "RSSI7";
    public String wRSSI8 = "RSSI8";
    public String wRSSI9 = "RSSI9";
    public String wRSSI10 = "RSSI10";
    public String wRSSI11 = "RSSI11";
    public String wRSSI12 = "RSSI12";
    public String wRSSI13 = "RSSI13";
    public String wRSSI14 = "RSSI14";
    public String wRSSI15 = "RSSI15";

    public int RSSI0 = 0;
    public int RSSI1 = 0;
    public int RSSI2 = 0;
    public int RSSI3 = 0;
    public int RSSI4 = 0;
    public int RSSI5 = 0;
    public int RSSI6 = 0;
    public int RSSI7 = 0;
    public int RSSI8 = 0;
    public int RSSI9 = 0;

    public int RSSI_arr[] = new int[16];

    public void WifiData(){
    }
    public void clear(){
        /*
        Latitude = "null";
        Longitude = "null";
        Provider = "null";
        Accuracy = "null";
        Altitude = "null";
        Bearing = "null";
        Speed = "null";
        last_time = "null";
        new_time = "null";*/

        SSID = "null";
        BSSID = "null";
        MAC = "null";
        state = "null";
        RSSI = 0;
        linkSpeed = 0;
        Frequency = 0;
        NetID = 0;
        Metered = "null";
        score = "null";
        Hidden = false;
        IpAddr = 0;

        currentTime = "null";

        RSSI0 = 0;
        RSSI1 = 0;
        RSSI2 = 0;
        RSSI3 = 0;
        RSSI4 = 0;
        RSSI5 = 0;
        RSSI6 = 0;
        RSSI7 = 0;
        RSSI8 = 0;
        RSSI9 = 0;

        for(int i=0;i<RSSI_arr.length;++i){
            RSSI_arr[i] = 0;
        }
    }
}
