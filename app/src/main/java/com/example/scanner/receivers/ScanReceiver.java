package com.example.scanner.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScanReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ScanReceiver","Check receiver");
        String action = intent.getAction();
            if (action !=null && action.equals("com.xcheng.scanner.action.BARCODE_DECODING_BROADCAST")) {
                String scanData = intent.getStringExtra("EXTRA_BARCODE_DECODING_DATA");
                String symbology = intent.getStringExtra("EXTRA_BARCODE_DECODING_SYMBOLE");

                Intent localIntent = new Intent("Scan_data_received");

                localIntent.putExtra("scanData", scanData);
                localIntent.putExtra("symbology", symbology);

                context.sendBroadcast(localIntent);
            }

        else {
            Log.d("Intent action", "action is null");
        }
    }
}
