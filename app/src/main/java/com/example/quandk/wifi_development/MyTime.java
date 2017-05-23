package com.example.quandk.wifi_development;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
/**
 * Created by quandk on 17-5-16.
 */

public class MyTime {
    private long time;


    public long getTimeMillis(){
        time = System.currentTimeMillis();
        return time;
    }

    public String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str_time = formatter.format(curDate);
        return str_time;

    }
}
