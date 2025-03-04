package com.example.scanner;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.scanner.service.ScanService;

public class MainActivity extends AppCompatActivity {

    private TextView info;

    private BroadcastReceiver scanDataReceiver;

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, ScanService.class));
        info = findViewById(R.id.information);
        Log.d("MainActivity", "check");


        scanDataReceiver = new BroadcastReceiver() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null && action.equals("Scan_data_received")) {
                    String scanData = intent.getStringExtra("scanData");
                    String symbology = intent.getStringExtra("symbology");

                    showAlertInfo("Информация: " + scanData + " \nСимволика: " + symbology );
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter("Scan_data_received");
        registerReceiver(scanDataReceiver, intentFilter);
    }

    private void showAlertInfo(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Информация")
                .setMessage(text)
                .setCancelable(false)
                .setPositiveButton("закрыть", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scanDataReceiver != null) {
            unregisterReceiver(scanDataReceiver);
        }
    }
}