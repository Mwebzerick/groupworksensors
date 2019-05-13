package com.example.sensors;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_GAME_ROTATION_VECTOR;
import static android.hardware.Sensor.TYPE_HEART_BEAT;
import static android.hardware.Sensor.TYPE_LIGHT;
import static android.hardware.Sensor.TYPE_PROXIMITY;

public class MainActivity extends AppCompatActivity {
private  SensorManager sensorManager;
private  Sensor lightsensor;
private SensorEventListener lightEventListener;
private View myroot;
private float maxValue;//store the max range value of the light sensor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myroot=findViewById(R.id.root);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        lightsensor=sensorManager.getDefaultSensor(TYPE_LIGHT);
        if (lightsensor==null){
            Toast.makeText(this,"There is no light sensor:(", Toast.LENGTH_SHORT).show();
            finish();//if the device has no light sensor
        }
        maxValue=lightsensor.getMaximumRange();//storing the maximum value of the light sensor
        lightEventListener=new SensorEventListener() {//
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value=sensorEvent.values[0];//onchanged method is called when the light sensor detects a new value
                getSupportActionBar().setTitle("Light Intensity: " +value +"lx");
                int newValue= (int)(255f *value/maxValue);//detecting luminosity
                myroot.setBackgroundColor(Color.rgb(newValue, newValue, newValue));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightsensor,SensorManager.SENSOR_DELAY_FASTEST);
    }
    protected void  onPause(){
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }
}
