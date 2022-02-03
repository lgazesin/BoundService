package com.nexis.lab12;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class OdometerService extends Service {
    public OdometerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    private final IBinder binder = new OdometerBinder();

    public class OdometerBinder extends Binder {
        OdometerService getOdometer(){
            return OdometerService.this;
        }
    }

    private final Random random = new Random();
    public double getDistance(){
        return random.nextDouble();
    }
}