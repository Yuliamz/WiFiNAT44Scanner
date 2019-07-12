package cn.edu.xjtlu.eee.wifiscanner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ScanResultGeneralView extends LinearLayout {

    LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    Button button;
    ConnectionUtils utils;
    private boolean connectedYet;
    ScanResultTextView scan;
    ClipboardManager clipboard;
    View viewDivider;

    public ScanResultGeneralView(final Context context, final ScanResultTextView scan) {
        super(context);
        this.setOrientation(VERTICAL);
        this.scan = scan;
        this.addView(this.scan,layoutParams);
        viewDivider = new View(context);
        button = new Button(context);
        button.setTextColor(Color.BLACK);
        utils = new ConnectionUtils(context,this.scan.getSSID(),this.scan.getPASSWORD());
        connectedYet = utils.isConnectedTo(this.scan.getBSSID());
        clipboard = (ClipboardManager)  context.getSystemService(Context.CLIPBOARD_SERVICE);

        addButton();
        addDivider();


    }

    void setButtonAsConnecting(){
        button.setText(R.string.test_password);
        button.setEnabled(false);
    }

    void addDivider(){
        viewDivider.setBackgroundColor(Color.BLACK);
        int dividerHeight = (int)getResources().getDisplayMetrics().density;
        viewDivider.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight));
        this.addView(viewDivider);
    }


    void addButton(){
        if (connectedYet){
            setButtonAsConnected();
        }else{
            if (this.scan.getPASSWORD()!=null){
                setButtonAsUnconnected();
            }else if(this.scan.getIP()!=null){
                setButtonAsUndefined();
            }
        }
    }

    void setButtonAsConnected(){
        button.setText(R.string.wifi_connected);
        button.setEnabled(false);
        button.setTextColor(Color.BLUE);
        this.addView(button,layoutParams);
    }

    void setButtonAsUndefined(){
        button.setText(R.string.answer_developer);
        button.setEnabled(true);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clipboard.setPrimaryClip(ClipData.newPlainText("IP",scan.getIP()+"-"+scan.getBSSID()));
                Toast.makeText(getContext(), R.string.copied_wifi_info, Toast.LENGTH_LONG).show();
            }
        });
        this.addView(button,layoutParams);
    }

    void setButtonAsUnconnected(){
        button.setText(R.string.connect);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAsConnecting();
                if (utils.connect()){
                    button.setText(R.string.wifi_connected);
                    button.setEnabled(false);
                    button.setTextColor(Color.BLUE);
                }
            }
        });

        button.setEnabled(true);
        this.addView(button,layoutParams);
    }

}
