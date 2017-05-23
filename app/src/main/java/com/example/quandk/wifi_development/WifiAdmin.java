package com.example.quandk.wifi_development;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by quandk on 17-5-16.
 */

public class WifiAdmin {
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private List<ScanResult> mWifiList;
    private List<WifiConfiguration> mWifiConfigurations;
    private int netIdIndex = 1;
    private int num_xjtu1x = 0;
    StringBuffer sb = new StringBuffer();
    WifiManager.WifiLock mWifiLock;
    public WifiAdmin(Context context){
        Log.i("WifiAdminActivity", "__________WifiAdmin_________");
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
    }
    public void openWifi(){
        Log.i("WifiAdminActivity", "__________openWifi_________");
        if(!mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(true);
        }
    }
    public void closeWifi(){
        Log.i("WifiAdminActivity", "__________closeWifi_________");
        if(!mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(false);
        }
    }
    public int checkState(){
        Log.i("WifiAdminActivity", "__________checkState_________");
        return mWifiManager.getWifiState();
    }
    public void acquireWifiLock(){
        mWifiLock.acquire();
    }
    public void releaseWifiLock(){
        if(mWifiLock.isHeld())
            mWifiLock.acquire();
    }
    public void createWifiLock(){
        mWifiLock = mWifiManager.createWifiLock("test");
    }
    public List<WifiConfiguration> getConfiguration(){
        return mWifiConfigurations;
    }
    public void connectionConfiguration(int index){
        if(index>mWifiConfigurations.size()){
            return ;
        }
        mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId, true);
    }

    public void startScan(){
        Log.i("WifiAdminActivity", "__________startScan_________");
        mWifiManager.startScan();
        mWifiList = mWifiManager.getScanResults();
        mWifiConfigurations = mWifiManager.getConfiguredNetworks();//return a list of all networks configured for the current foreground user. Upon failure to fetch or when Wifi if turn off ,it can be null
    }
    public List<ScanResult> getmWifiList(){
        Log.i("WifiAdminActivity", "__________getmWifiList_________");
        return mWifiList;
    }
    public StringBuffer lookUpScan(){
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<mWifiList.size(); ++i){
            sb.append("Index_" + new Integer(i + 1).toString() + ":");
            sb.append((mWifiList.get(i)).toString()).append("\n");
        }
        return sb;
    }
    public String getMacAddress(){
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }
    public String getBSSID(){
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }
    public int getIpAddress(){
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }
    public int getNetWorkId(){
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }
    //public String getWifiInfo(){
    //    return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    //}
    public void addNetWork(WifiConfiguration configuration){
        int wcgId = mWifiManager.addNetwork(configuration);
        mWifiManager.enableNetwork(wcgId, true);
    }
    public void disConnectionWifi(int netId){
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

    public boolean changeWifi(Context context) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiConfigurations = mWifiManager.getConfiguredNetworks();//return a list of all networks configured for the current foreground user. Upon failure to fetch or when Wifi if turn off ,it can be null

        mWifiManager.startScan();
        mWifiList = mWifiManager.getScanResults();

        if (netIdIndex >= mWifiConfigurations.size())
                netIdIndex = 0;

            //判断当前的配置是否在扫描的结果中，得到一个配置的netIdIndex
            boolean flag_wifi = true;
            while(flag_wifi){
                mWifiManager.startScan();
                mWifiList = mWifiManager.getScanResults();

                String ssid = mWifiConfigurations.get(netIdIndex++).SSID.replaceAll("\"","");//得到配置文件中的ssid   需要判断 循环扫描列表，看xjtu1的BSSID（mac）是否在 配置文件中
                if (netIdIndex >= mWifiConfigurations.size())
                    netIdIndex = 0;
                for(int i=0; i<mWifiList.size(); i++){

                    if(ssid.equals(mWifiList.get(i).SSID)){
                        flag_wifi = false;
                    }
                    if("xjtu1x".equals(mWifiList.get(i).SSID)){

                    }
                    //Log.i("WifiAdmin", "____ConSize___________scanresult----:" + ssid+ "," + mWifiList.get(i).SSID.toString() + ',' + ssid.equals(mWifiList.get(i).SSID));
                }
            }

        for(int i=0; i<mWifiList.size(); i++)
            Log.i("WifiAdmin", "____ConSize___________scanresult----:" + mWifiList.get(i).toString());
        for(int i=0; i<mWifiConfigurations.size(); i++)
            Log.i("WifiAdmin", "____ConSize___________mWifiConfigurations----:" + mWifiConfigurations.get(i).toString());

            if(netIdIndex == 0)
                netIdIndex = mWifiConfigurations.size()-1;
            else
                netIdIndex--;

            /*Log.i("WifiAdmin", "____ConSize_____networkId:" + mWifiConfigurations.get(netIdIndex).networkId + "  " + mWifiConfigurations.get(netIdIndex).SSID);
            if (mWifiConfigurations.get(netIdIndex).SSID.toString().indexOf(mWifiManager.getScanResults().toString()) != -1)
                Log.i("WifiAdmin", "____ConSize_________:OKOKOKOKOKOKOKOK");
            Log.i("WifiAdmin", "____ConSize__________________________________________:" + mWifiConfigurations.toString());
            Log.i("WifiAdmin", "____ConSize:" + mWifiConfigurations.size() + "  netIdIndex:" + netIdIndex);
            for (netIdIndex = 0; netIdIndex < mWifiConfigurations.size(); netIdIndex++)
                Log.i("WifiAdmin", "____NetworkId:" + mWifiConfigurations.get(netIdIndex).networkId + "  " + mWifiConfigurations.get(netIdIndex).SSID);
            //mWifiManager.enableNetwork(mWifiConfigurations.get(netIdIndex).networkId, true);
            */
            boolean isDisc = mWifiManager.disconnect();
            Log.i("WifiAdmin", "____isDisc:"+isDisc);
            boolean isEnable = mWifiManager.enableNetwork(mWifiConfigurations.get(netIdIndex).networkId, true);
            Log.i("WifiAdmin", "____isEnable:"+isEnable);
            ++netIdIndex;

            boolean isConn = mWifiManager.reconnect();
            Log.i("WifiAdmin", "____isConn:"+isConn);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return isConn;
    }

    public boolean isWifiConnected(Context context){
        if(context != null){
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //NetworkInfo mWifiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo ni = mConnectivityManager.getActiveNetworkInfo();
            if(ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI){
                WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
                //String state = wifiInfo.getSupplicantState().toString();
                //Log.i("WifiAdmin", "____getSupplicantState:"+ wifiInfo.getSupplicantState().toString().equals("COMPLETED"));
                return wifiInfo.getSupplicantState().toString().equals("COMPLETED");
            }
            else
                return false;
        }
        return false;
    }

    public String getWifiInfo(WifiData d, Context context){
        sb.setLength(0);
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();// return dynamic information about the current Wi-Fi connection, if any is active
        if(null == mWifiInfo)
            return "No connection";
        else {
            mWifiInfo.getLinkSpeed();
            sb = sb.append(
                    //"ConnectionInfo:   " + mWifiInfo.toString() + "   ----  ;").append(
                    /*";" + mWifiInfo.getLinkSpeed()).append(
                    ";" + mWifiInfo.getNetworkId()).append(
                    ";" + mWifiInfo.describeContents()).append(
                    ";" + mWifiInfo.getBSSID()).append(
                    ";" + mWifiInfo.getMacAddress()).append(
                    ";" + mWifiInfo.getSSID()).append(
                    ";" + mWifiInfo.getFrequency()).append(
                    ";" + mWifiInfo.getHiddenSSID()).append(
                    ";" + mWifiInfo.getIpAddress()).append(
                    ";" + mWifiInfo.getRssi()).append(
                    ";" + mWifiInfo.getMacAddress()).append(*/
                    ";" + mWifiInfo.getSupplicantState());

            d.linkSpeed = mWifiInfo.getLinkSpeed();
            d.SSID = mWifiInfo.getSSID();
            d.MAC = mWifiInfo.getMacAddress();
            d.state = mWifiInfo.getSupplicantState().toString();
            d.RSSI  =mWifiInfo.getRssi();
            d.Frequency = mWifiInfo.getFrequency();
            d.NetID = mWifiInfo.getNetworkId();
            d.Metered = "null";
            d.score = "null";
            d.Hidden = mWifiInfo.getHiddenSSID();
            d.IpAddr = mWifiInfo.getIpAddress();
            d.BSSID = mWifiInfo.getBSSID();
        }
        return sb.toString();
    }
}
