package com.yuliamz.wifiscanner;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class ConnectionUtils {

    private WifiConfiguration wifiConfiguration;
    private WifiManager wifiManager;
    private Context context;

    public ConnectionUtils(Context context) {
        this.context=context;
        wifiManager = (WifiManager)this.context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiConfiguration = new WifiConfiguration();
    }

    void enableWifi(){
        if (!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);
    }

    public ConnectionUtils(Context context, String SSID, String PASS) {
        this.context=context;
        wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = String.format("\"%s\"", SSID);
        wifiConfiguration.preSharedKey=String.format("\"%s\"", PASS);
    }

    public boolean connect(){
        wifiManager.disconnect();
        int networkID = wifiManager.addNetwork(wifiConfiguration);
        wifiManager.enableNetwork(networkID, true);
        wifiManager.reconnect();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean isConnectionSucces = wifiManager.getConnectionInfo().getNetworkId()==networkID;
        checkResult(isConnectionSucces);
        return isConnectionSucces;
    }

    public boolean isWiFiConnected(){
        return wifiManager.getConnectionInfo().getNetworkId()!=-1;
    }

    public String getConnectedBSSID(){
        return wifiManager.getConnectionInfo().getBSSID();
    }
    public boolean isConnectedTo(String BSSID){
        return isWiFiConnected() && getConnectedBSSID().equalsIgnoreCase(BSSID);
    }

    public boolean connectTo(String SSID, String PASS){
        wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = String.format("\"%s\"", SSID);
        wifiConfiguration.preSharedKey = String.format("\"%s\"", PASS);
        return connect();
    }

    private void checkResult(boolean isConnected) {
        if (isConnected){
            Toast.makeText(this.context.getApplicationContext(), R.string.connected, Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this.context.getApplicationContext(), R.string.cant_connect, Toast.LENGTH_SHORT).show();

        }
    }


    public WifiConfiguration getWifiConfiguration() {
        return wifiConfiguration;
    }

    public void setWifiConfiguration(WifiConfiguration wifiConfiguration) {
        this.wifiConfiguration = wifiConfiguration;
    }

    public WifiManager getWifiManager() {
        return wifiManager;
    }

    public void setWifiManager(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }
}
