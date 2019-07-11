package cn.edu.xjtlu.eee.wifiscanner;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class ScanResultGeneralView extends LinearLayout {

    LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    Button button;

    public ScanResultGeneralView(Context context, final ScanResultTextView scanResultTextView) {
        super(context);
        this.setOrientation(VERTICAL);
        this.setPadding(0,10,0,0);
        button = new Button(context);
        button.setText("Conectar");
        button.setTextColor(Color.BLACK);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Intenta conectar a: "+scanResultTextView.getSSID());
            }
        });

        this.addView(scanResultTextView,layoutParams);
        this.addView(button,layoutParams);
    }
}
