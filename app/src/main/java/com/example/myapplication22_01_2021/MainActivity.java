package com.example.myapplication22_01_2021;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView textView, textViewX, textViewY, textViewZ;
    private SensorManager mSensorManager;
    private Sensor mLight;
    private float valuenow = 0f;

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    private MyListener myListener;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textViewX = findViewById(R.id.textViewX);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        setMyListener((MyListener) findViewById(R.id.surfaceView));


        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensorList) {
            textView.append("\n");
            textView.append(sensor.getStringType());
        }
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        textViewX.setText(String.valueOf(event.values[0]));
        valuenow = event.values[0];
        if (myListener != null){
            myListener.onEvent(valuenow);
        }

    }


}