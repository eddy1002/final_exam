package com.example.igx.problem1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.service.carrier.CarrierMessagingService;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    LocationManager locationManager;
    public Location locationNew;

    public double Lat;
    public double Lod;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGravity;
    private Sensor mGyroscope;
    private Sensor mLight;

    public double accX;
    public double accY;
    public double accZ;

    public double graX;
    public double graY;
    public double graZ;

    public double gyrX;
    public double gyrY;
    public double gyrZ;

    public double light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_getLocation = (Button) findViewById(R.id.btn_getLocation);
        Button btn_getSensors = (Button) findViewById(R.id.btn_getSensors);
        Button btn_sendMessage = (Button) findViewById(R.id.btn_sendMessage);

        final TextView text_selectedData = (TextView) findViewById(R.id.text_selectedData);
        final TextView text_selectedType = (TextView) findViewById(R.id.text_selectedType);
        final EditText edit_phoneNumber = (EditText) findViewById(R.id.edit_phoneNumber);

        //추가한 코드!
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        btn_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_selectedType.setText("LOCATION");
            }
        });

        btn_getSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_selectedType.setText("SENSORS");
                text_selectedData.setText("Accelerometer : (" + String.format("%.2f", accX) + ", " + String.format("%.2f", accY) + ", " + String.format("%.2f", accZ) + ")\n" +
                        "Gravity : (" + graX + ", " + graY + ", " + graZ + ")\n" +
                        "Gyroscope : (" + gyrX + ", " + gyrY + ", " + gyrZ + ")\n" +
                        "Light : (" + light + ")\n");
            }
        });

        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
                intent.putExtra("sms_body", text_selectedData.getText());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                accX = event.values[0];
                accY = event.values[1];
                accZ = event.values[2];
                break;
            case Sensor.TYPE_GRAVITY:
                graX = event.values[0];
                graY = event.values[1];
                graZ = event.values[2];
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyrX = event.values[0];
                gyrY = event.values[1];
                gyrZ = event.values[2];
                break;
            case Sensor.TYPE_LIGHT:
                light = event.values[0];
                break;
        }
    }
}
