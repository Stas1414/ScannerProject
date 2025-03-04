package com.example.scanner.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class ScanService extends Service {

    private BroadcastReceiver scanReceiver;

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    public void onCreate() {
        super.onCreate();
        scanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null && action.equals("com.xcheng.scanner.action.BARCODE_DECODING_BROADCAST")) {
                    String scanData = intent.getStringExtra("EXTRA_BARCODE_DECODING_DATA");
                    String symbology = intent.getStringExtra("EXTRA_BARCODE_DECODING_SYMBOLE");

                    Intent localIntent = new Intent("Scan_data_received");
                    localIntent.putExtra("scanData", scanData);
                    localIntent.putExtra("symbology", symbology);
                    sendBroadcast(localIntent);
                    Log.d("ScanService","data sent");
                }
            }
        };
        IntentFilter filter = new IntentFilter("com.xcheng.scanner.action.BARCODE_DECODING_BROADCAST");
        registerReceiver(scanReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (scanReceiver != null) {
            unregisterReceiver(scanReceiver);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}