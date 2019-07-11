package cn.edu.xjtlu.eee.wifiscanner;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ScanResultGeneralView extends LinearLayout {

    LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    Button button;

    WifiConfiguration wifiConfig = new WifiConfiguration();

    WifiManager wifiManager;

    void connectTo(String SSID, String PASS){

        wifiConfig.SSID = String.format("\"%s\"", SSID);
        wifiConfig.preSharedKey = String.format("\"%s\"", PASS);

        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       checkResult(wifiManager.getConnectionInfo().getNetworkId()!=-1);

    }

    public ScanResultGeneralView(final Context context, final ScanResultTextView scan) {
        super(context);
        this.setOrientation(VERTICAL);
        this.setPadding(0,10,0,0);


        wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        this.addView(scan,layoutParams);

        if (scan.getPASSWORD()!=null){
            button = new Button(context);
            button.setText("Conectar");
            button.setTextColor(Color.BLACK);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Intenta conectar a: "+scan.getSSID()+" "+scan.getBSSID()+" "+scan.getPASSWORD());
                    connectTo(scan.getSSID(),scan.getPASSWORD());
                }
            });

            this.addView(button,layoutParams);
        }
    }

    private void checkResult(boolean isSuccess)
    {
        if (isSuccess){
            Toast.makeText(getContext(), "Conectado", Toast.LENGTH_SHORT).show();
            button.setText("Ya estás conectado");
            button.setEnabled(false);
        }

        else{
            Toast.makeText(getContext(), "No se pudo conectar", Toast.LENGTH_SHORT).show();
        }

    }

}
