package com.nexis.lab12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayDistance();
    }
    
    private OdometerService odometer;
    private boolean bound = false;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            OdometerService.OdometerBinder odometerBinder = (OdometerService.OdometerBinder) service;
            odometer = odometerBinder.getOdometer();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    protected  void onStart(){
        super.onStart();
        Intent i = new Intent(this, OdometerService.class);
        bindService(i, connection, Context.BIND_AUTO_CREATE);
    }

    protected  void onStop(){
        super.onStop();
        if (bound){
            unbindService(connection);
            bound = false;
        }
    }

    public void onClickStart(View view) {
        this.onStart();
    }

    public void onClickStop(View view) {
        this.onStop();
    }

    private void displayDistance(){
        final TextView distanceView = findViewById(R.id.distance);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                double distance = 0.0;
                if (bound && odometer != null){
                    distance = odometer.getDistance();
                }
                String strDistance = String.format(Locale.getDefault(),"%1$, .2f miles", distance);
                distanceView.setText(strDistance);
                handler.postDelayed(this,1000);
            }
        });
    }
}