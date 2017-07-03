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
    private List<ScanResult> mWifiListInfo;
    private List<WifiConfiguration> mWifiConfigurations;
    private WifiConfiguration xjtuConfiguration = null;
    private int netIdIndex = 1;
    private int oldid = -5;
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
        mWifiListInfo = mWifiList;
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
    public boolean changeRssi(Context context){
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        mWifiManager.startScan();
        mWifiList = mWifiManager.getScanResults();

        for(int i=0; i<mWifiList.size(); i++){
            Log.i("WifiAdmin", "____ConSize___________mWifilist----:"  + mWifiList.get(i));
        }

        for(;num_xjtu1x<mWifiList.size();){
            if("xjtu1x".equals(mWifiList.get(num_xjtu1x).SSID)){
                mWifiList.get(num_xjtu1x).level = -20;
                num_xjtu1x++;
                break;
            }
            else{
                num_xjtu1x++;
                if(num_xjtu1x>=mWifiList.size())
                    num_xjtu1x = 0;
            }

        }
        for(int i=0; i<mWifiList.size(); i++){
            Log.i("WifiAdmin", "____ConSize___________mWifilist2222----:"  + mWifiList.get(i));
        }

        boolean isDisc = mWifiManager.disconnect();
        Log.i("WifiAdmin", "____isDisc:"+isDisc);
        //boolean isEnable = mWifiManager.enableNetwork(mWifiConfigurations.get(netIdIndex).networkId, true);
        //boolean isEnable = mWifiManager.enableNetwork(newid, true);
        //Log.i("WifiAdmin", "____isEnable:"+isEnable);
        ++netIdIndex;

        boolean isConn = mWifiManager.reconnect();
        Log.i("WifiAdmin", "____isConn:"+isConn);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isConn;
    }
    public boolean connectWifi(Context context)
    {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        mWifiManager.startScan();
        mWifiList = mWifiManager.getScanResults();

        for(int i=0; i<mWifiList.size(); i++){
            Log.i("WifiAdmin", "____ConSize___________mWifilist----:"  + mWifiList.get(i));
        }

        mWifiConfigurations = mWifiManager.getConfiguredNetworks();
        WifiConfiguration conf;
        if(xjtuConfiguration == null){
            for(int i=0; i<mWifiConfigurations.size(); i++){
                if("xjtu1x".equals(mWifiConfigurations.get(i).SSID.replaceAll("\"",""))){
                    xjtuConfiguration = mWifiConfigurations.get(i);

                    Log.i("WifiAdmin", "____ConSize___________mWifiConfigurations----:"  + mWifiConfigurations.get(i));

                }
            }
        }
        conf = xjtuConfiguration;
        mWifiManager.startScan();
        mWifiList = mWifiManager.getScanResults();
        if(num_xjtu1x>=mWifiList.size())
            num_xjtu1x = 0;
        for(;num_xjtu1x<mWifiList.size();){
            if("xjtu1x".equals(mWifiList.get(num_xjtu1x).SSID)){
                conf.BSSID = mWifiList.get(num_xjtu1x).BSSID;
                num_xjtu1x++;
                break;
            }
            else{
                num_xjtu1x++;
                if(num_xjtu1x>=mWifiList.size())
                    num_xjtu1x = 0;
            }

        }

        //conf.BSSID = "38:91:d5:c9:81:c1";
        Log.i("WifiAdmin", "____conf___________conf---:"  + num_xjtu1x + "  " + conf);

        if(oldid>-1)
            ;//conf.BSSID = "38:91:d5:c9:81:c1";;//mWifiManager.removeNetwork(oldid);
        //conf.BSSID = "38:91:d5:c6:98:f1";
        //conf.BSSID = "38:91:d5:c9:81:c1";
        int newid = mWifiManager.updateNetwork(conf);
        oldid = newid;
        mWifiManager.startScan();
        mWifiListInfo = mWifiManager.getScanResults();


        boolean isDisc = mWifiManager.disconnect();
        Log.i("WifiAdmin", "____isDisc:"+isDisc);
        //boolean isEnable = mWifiManager.enableNetwork(mWifiConfigurations.get(netIdIndex).networkId, true);
        //boolean isEnable = mWifiManager.enableNetwork(newid, true);
        //Log.i("WifiAdmin", "____isEnable:"+isEnable);
        ++netIdIndex;

        boolean isConn = mWifiManager.reconnect();
        Log.i("WifiAdmin", "____isConn:"+isConn);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isConn;

    }

    public boolean changeWifi(Context context) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiConfigurations = mWifiManager.getConfiguredNetworks();//return a list of all networks configured for the current foreground user. Upon failure to fetch or when Wifi if turn off ,it can be null
        int newid = 0;

        WifiConfiguration conf = new WifiConfiguration();


        if(xjtuConfiguration == null){
            for(int i=0; i<mWifiConfigurations.size(); i++){
                if("xjtu1x".equals(mWifiConfigurations.get(i).SSID.replaceAll("\"",""))){
                    xjtuConfiguration = mWifiConfigurations.get(i);
                    conf = xjtuConfiguration;
                    Log.i("WifiAdmin", "____ConSize___________mWifiConfigurations----:"  + mWifiConfigurations.get(i).preSharedKey);
                    Log.i("WifiAdmin", "____ConSize___________mWifiConfigurations--xjtu--:"  + xjtuConfiguration);
                    Log.i("WifiAdmin", "____ConSize___________mWifiConfigurations--psk--:"  + xjtuConfiguration.preSharedKey.toString());
                }
            }
        }

        mWifiManager.startScan();
        mWifiList = mWifiManager.getScanResults();
        for(int i=0; i<mWifiList.size(); i++){
            Log.i("WifiAdmin", "____ConSize___________mWifilist----:"  + mWifiList.get(i));
        }


            if (netIdIndex >= mWifiConfigurations.size())
                netIdIndex = 0;

            //判断当前的配置是否在扫描的结果中，得到一个配置的netIdIndex
            boolean flag_wifi = true;
            //while(flag_wifi){
                //mWifiManager.startScan();
                //mWifiList = mWifiManager.getScanResults();

                //String ssid = mWifiConfigurations.get(netIdIndex++).SSID.replaceAll("\"","");//得到配置文件中的ssid   需要判断 循环扫描列表，看xjtu1的BSSID（mac）是否在 配置文件中
                //if (netIdIndex >= mWifiConfigurations.size())
                //   netIdIndex = 0;
                //for(int i=0; i<mWifiList.size(); i++){//循环扫描列表
                if(num_xjtu1x >= mWifiList.size())
                    num_xjtu1x=0;
                while(flag_wifi){
                    //if(ssid.equals(mWifiList.get(i).SSID)){
                    //   flag_wifi = false;
                    //}
                    if("xjtu1x".equals(mWifiList.get(num_xjtu1x).SSID)){
                        flag_wifi = false;
                        xjtuConfiguration.BSSID = mWifiList.get(num_xjtu1x).BSSID;
                        if(oldid>-1)
                            mWifiManager.removeNetwork(oldid);
                        //newid = mWifiManager.addNetwork(xjtuConfiguration);
                        //oldid = newid;
                        newid = mWifiManager.updateNetwork(xjtuConfiguration);
                        Log.i("WifiAdmin", "____ConSize___________newNetworkId:" + newid);
                        Log.i("WifiAdmin", "____ConSize___________num_xjtu1x:" + num_xjtu1x);
                        Log.i("WifiAdmin", "____ConSize___________xjtuConfiguration:" + xjtuConfiguration);
                        num_xjtu1x++;
                        /*for(int j=0; j<mWifiConfigurations.size();j++){
                            Log.i("WifiAdmin", "___________Con____:" + j + ":" + mWifiConfigurations.get(j));
                            Log.i("WifiAdmin", "___________Lis____:" + num_xjtu1x + ":" + mWifiList.get(num_xjtu1x));
                            Log.i("WifiAdmin", "___________null____:" + "null".equals(mWifiConfigurations.get(j).BSSID));
                            if ( ("null".equals(mWifiConfigurations.get(j).BSSID) || "any".equals(mWifiConfigurations.get(j).BSSID) ) && mWifiConfigurations.get(j).BSSID.equals(mWifiList.get(i).BSSID));
                            else{
                                xjtuConfiguration.BSSID = mWifiList.get(i).BSSID;
                                int newid = mWifiManager.addNetwork(xjtuConfiguration);
                                Log.i("WifiAdmin", "____ConSize___________newNetworkId:" + newid);
                            }

                        }*/

                    }
                    num_xjtu1x++;
                    if(num_xjtu1x >= mWifiList.size())
                        num_xjtu1x=0;
                    //Log.i("WifiAdmin", "____ConSize___________scanresult----:" + ssid+ "," + mWifiList.get(i).SSID.toString() + ',' + ssid.equals(mWifiList.get(i).SSID));
                }
            //}
        /*
        for(int i=0; i<mWifiList.size(); i++)
            Log.i("WifiAdmin", "____ConSize___________scanresult----:" + mWifiList.get(i).toString());
        for(int i=0; i<mWifiConfigurations.size(); i++)
            Log.i("WifiAdmin", "____ConSize___________mWifiConfigurations----:" + mWifiConfigurations.get(i).toString());

            if(netIdIndex == 0)
                netIdIndex = mWifiConfigurations.size()-1;
            else
                netIdIndex--;
        */
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
            //boolean isEnable = mWifiManager.enableNetwork(mWifiConfigurations.get(netIdIndex).networkId, true);
            boolean isEnable = mWifiManager.enableNetwork(newid, true);
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
            Log.i("WifiAdmin", "____getSupplicantState:"+ mWifiInfo.toString());
        }
        int j = 1;
        for(int ii=0; ii<mWifiListInfo.size(); ++ii){
            if("xjtu1x".equals(mWifiListInfo.get(ii).SSID)){
                if(mWifiInfo.getBSSID().equals(mWifiListInfo.get(ii).BSSID)){
                    d.RSSI_arr[0] = mWifiListInfo.get(ii).level;
                }
                else{
                    if(j<d.RSSI_arr.length){
                        d.RSSI_arr[j] = mWifiListInfo.get(ii).level;
                        ++j;
                    }

                }
            }
        }
        return sb.toString();
    }
}


