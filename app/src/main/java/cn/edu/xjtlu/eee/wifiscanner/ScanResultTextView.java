package cn.edu.xjtlu.eee.wifiscanner;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScanResultTextView extends TextView {
    private String SSID;
    private String BSSID;
    private String CAPABILITIES;
    private int LEVEL;
    private String PASSWORD;

    public ScanResultTextView(Context context, ScanResult scanResult) {
        super(context);
        SSID = scanResult.SSID;
        BSSID = scanResult.BSSID.toUpperCase();
        CAPABILITIES = scanResult.capabilities;
        LEVEL = scanResult.level;

        showText();
        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setTextColor(Color.BLACK);
    }

    void showText(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SSID: ").append(SSID).append("\n");
        stringBuilder.append("BSSID: ").append(BSSID).append("\n");
        stringBuilder.append("CIFRADO: ").append(CAPABILITIES).append("\n");
        stringBuilder.append("INTENSIDAD: ").append(LEVEL).append("\n");
        if (PASSWORD!=null){
            stringBuilder.append("CONTRASEÑA ENCONTRADA: ").append(PASSWORD).append("\n");
        }
        this.setText(stringBuilder);
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
        showText();

    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getSSID() {
        return SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public String getCAPABILITIES() {
        return CAPABILITIES;
    }

    public int getLEVEL() {
        return LEVEL;
    }
}
