package com.example.gggab.labo3_gabrielgelinas_androidmysql;

import android.net.wifi.WifiManager;
import android.text.format.Formatter;

/**
 * Created by Zombietux on 2018-03-12.
 */

public class NetworkWorker {
    public String ip;
    public WifiManager wifiManager;

    public NetworkWorker(WifiManager wm) {
        this.wifiManager = wm;
        this.ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }
}
