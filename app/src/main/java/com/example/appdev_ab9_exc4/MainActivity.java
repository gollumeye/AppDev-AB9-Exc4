package com.example.appdev_ab9_exc4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private double deviceAcceleration;
    private double deviceAcceleration_before;
    private double deviceAcceleration_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(sensorManager)
                .registerListener(
                        sensorListener,
                        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_NORMAL);
        deviceAcceleration = 10;
        deviceAcceleration_before = SensorManager.GRAVITY_EARTH;
        deviceAcceleration_now = SensorManager.GRAVITY_EARTH;
    }

    private final SensorEventListener sensorListener =
            new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    double x_axis = sensorEvent.values[0];
                    double y_axis = sensorEvent.values[1];
                    double z_axis = sensorEvent.values[2];
                    deviceAcceleration_before = deviceAcceleration_now;
                    deviceAcceleration_now =
                            Math.sqrt(x_axis * x_axis + y_axis * y_axis + z_axis * z_axis);
                    double delta = deviceAcceleration_now - deviceAcceleration_before;
                    deviceAcceleration = deviceAcceleration * 0.9 + delta;

                    if (deviceAcceleration > 9.5) {
                        TextView text = findViewById(R.id.textView);
                        int random = new Random().nextInt((6-1)+1)+1;
                        text.setText("You rolled a " + random + "!\nShake your phone to roll again!");
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {}
            };
}