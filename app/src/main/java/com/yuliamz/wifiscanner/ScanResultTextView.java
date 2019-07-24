package com.yuliamz.wifiscanner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.wifi.ScanResult;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
        stringBuilder.append(getContext().getString(R.string.SSID)).append(": ").append(SSID).append("\n");
        stringBuilder.append("BSSID: ").append(BSSID).append("\n");
        stringBuilder.append("CIFRADO: ").append(CAPABILITIES).append("\n");
        stringBuilder.append("INTENSIDAD: ").append(String.valueOf(LEVEL));
        if (IP!=null){
            SpannableString ip = new SpannableString("\nNAT44 IP: "+IP);
            ip.setSpan(new StyleSpan(Typeface.BOLD),0,ip.length(),0);
            stringBuilder.append(ip);
        }

        if (PASSWORD!=null && !PASSWORD.trim().isEmpty()){
            SpannableString pass = new SpannableString("\nCONTRASEÑA ENCONTRADA: "+PASSWORD);
            pass.setSpan(new StyleSpan(Typeface.BOLD),0,pass.length(),0);
            pass.setSpan(new ForegroundColorSpan(Color.RED),0,pass.length(),0);
            stringBuilder.append(pass);
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
