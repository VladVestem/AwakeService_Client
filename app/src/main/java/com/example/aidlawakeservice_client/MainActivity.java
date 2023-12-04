package com.example.aidlawakeservice_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aidlawakeservice_server.IAIDLAwakeServiceInterface;

public class MainActivity extends AppCompatActivity {

    IAIDLAwakeServiceInterface iAIDLAwakeServiceInterface;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iAIDLAwakeServiceInterface = IAIDLAwakeServiceInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("AIDLAwakeService");
        intent.setPackage("com.example.aidlawakeservice_server");

        bindService(intent, mConnection, BIND_AUTO_CREATE);

        // Button onClick Listener
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    long elapsedTime = iAIDLAwakeServiceInterface.GetServiceElapsedTime();
                    TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText((double) elapsedTime / 1_000_000_000 + " seconds");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}