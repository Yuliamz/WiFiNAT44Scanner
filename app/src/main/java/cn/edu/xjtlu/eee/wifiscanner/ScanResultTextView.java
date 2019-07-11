package cn.edu.xjtlu.eee.wifiscanner;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScanResultTextView extends TextView {
    private String SSID;
    private String BSSID;
    private String CAPABILITIES;
    private int LEVEL;
    private String IP;
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
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append("SSID: ").append(SSID).append("\n");
        stringBuilder.append("BSSID: ").append(BSSID).append("\n");
        stringBuilder.append("CIFRADO: ").append(CAPABILITIES).append("\n");
        stringBuilder.append("INTENSIDAD: ").append(""+LEVEL).append("\n");
        if (PASSWORD!=null){

            stringBuilder.append("NAT44 IP: ").append(IP).append("\n");

            SpannableString string = new SpannableString("CONTRASEÑA ENCONTRADA: "+PASSWORD);
            string.setSpan(new ForegroundColorSpan(Color.RED),0,string.length(),0);
            stringBuilder.append(string);

        }
        this.setText(stringBuilder, BufferType.SPANNABLE);
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

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}
