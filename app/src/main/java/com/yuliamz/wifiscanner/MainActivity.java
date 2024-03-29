package com.yuliamz.wifiscanner;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity {
	WifiManager wifiManager;
	WifiReceiver wifiReceiver;
	List<ScanResult> wifiList;
	StringBuilder sb = new StringBuilder();
	StringBuilder csv = new StringBuilder();
	boolean scanFinished = false;
	SQLiteDatabase database;
	LinearLayout mainView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainView = (LinearLayout) findViewById(R.id.linearLayout);
		wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		enableWifi();
		wifiReceiver = new WifiReceiver();
		registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifiManager.startScan();
		loadDatabase();
	}

	void enableWifi(){
		try {
			if (!wifiManager.isWifiEnabled()) wifiManager.setWifiEnabled(true);
		}catch (NullPointerException e){
			Toast.makeText(this, R.string.cant_enable_wifi, Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

	}


	void loadDatabase(){
		DatabaseHelper myDbHelper = new DatabaseHelper(this);
		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
		try {
			myDbHelper.openDataBase();
			database = myDbHelper.getMyDataBase();

		} catch (SQLException sqle) {
			throw sqle;
		}
	}


	String[] searchFor(String BSSID){
		String[] campos = new String[] {"IP","PASSWORD"};
		String[] args = new String[] {BSSID};

		Cursor c = database.query("ROUTER", campos, "BSSID=?", args, null, null, null);

		if (c.moveToFirst()) {
				return new String[] {c.getString(0),c.getString(1)};
		}
		return null;
	}



	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, R.string.scann);
		menu.add(0, 1, 1, R.string.exit);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			wifiManager.startScan();
			break;
		case 1:
			Intent scanResults = new Intent();
			scanResults.putExtra("AP_LIST", csv.toString());
			setResult(RESULT_OK, scanResults);
			finish();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	protected void onPause() {
		super.onPause();
		unregisterReceiver(wifiReceiver);
		Intent scanResults = new Intent();
		scanResults.putExtra("AP_LIST", csv.toString());
		setResult(RESULT_OK, scanResults);
		finish();
	}

	protected void onResume() {
		super.onResume();
		registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}


	class WifiReceiver extends BroadcastReceiver {
		public void onReceive(Context c, Intent intent) {
			mainView.removeAllViews();

			sb = new StringBuilder();
			csv = new StringBuilder();
			wifiList = wifiManager.getScanResults();

			// prepare text for display and CSV table
			sb.append("Number of APs Detected: ");
			sb.append((Integer.valueOf(wifiList.size())).toString());
			sb.append("\n\n");

			Collections.sort(wifiList, new Comparator<ScanResult>() {
				@Override
				public int compare(ScanResult scanResult, ScanResult t1) {

					if (scanResult.level>t1.level){
						return -1;
					}else if (scanResult.level==t1.level){
						return 0;
					}
						return 1;
				}
			});

			for (int i = 0; i < wifiList.size(); i++) {
				ScanResult result = wifiList.get(i);
				String[] ipass = searchFor(result.BSSID.toUpperCase());
				ScanResultTextView view = new ScanResultTextView(getApplicationContext(),result);
				if (ipass!=null){
					view.setIP(ipass[0]);
					view.setPASSWORD(ipass[1]);
				}
				mainView.addView(new ScanResultGeneralView(getApplicationContext(),view));

				// SSID
				sb.append("SSID: ").append((wifiList.get(i)).SSID);
				sb.append("\n");
				csv.append((wifiList.get(i)).SSID);
				csv.append(",");
				// BSSID
				sb.append("BSSID: ").append((wifiList.get(i)).BSSID.toUpperCase());
				sb.append("\n");
				csv.append((wifiList.get(i)).BSSID.toUpperCase());
				csv.append(",");
				// capabilities
				sb.append("Capabilities: ").append((wifiList.get(0)).capabilities);
				sb.append("\n");
				// frequency
				sb.append("Frequency: ").append((wifiList.get(i)).frequency);
				sb.append("\n");
				csv.append((wifiList.get(i)).frequency);
				csv.append(",");
				// level
				sb.append("Level: ").append((wifiList.get(i)).level);
				sb.append("\n\n");
				csv.append((wifiList.get(i)).level);
				csv.append("\n");
			}
			System.out.println(csv);

			scanFinished = true;
		}
	}
}