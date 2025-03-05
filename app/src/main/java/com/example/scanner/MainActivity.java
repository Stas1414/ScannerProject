package com.example.scanner;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scanner.adapter.ProductAdapter;
import com.example.scanner.model.Product;
import com.example.scanner.service.ScanService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Product> products = new ArrayList<>();
    private BroadcastReceiver scanDataReceiver;

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, ScanService.class));

        ListView listProducts = findViewById(R.id.listProducts);
        ProductAdapter productAdapter = new ProductAdapter(this, products);

        listProducts.setAdapter(productAdapter);

        scanDataReceiver = new BroadcastReceiver() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null && action.equals("Scan_data_received")) {
                    Product product = new Product(intent.getStringExtra("scanData"),
                            intent.getStringExtra("symbology"));
                    if (product.checkInList(products)) {
                        showAlertInfo();
                    }
                    else {
                        products.add(0, product);
                        productAdapter.notifyDataSetChanged();
                    }

                }
            }
        };

        IntentFilter intentFilter = new IntentFilter("Scan_data_received");
        registerReceiver(scanDataReceiver, intentFilter);
    }

    private void showAlertInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Предупреждение")
                .setMessage("Этот товар уже существует")
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