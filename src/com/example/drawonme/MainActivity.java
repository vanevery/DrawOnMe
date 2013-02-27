package com.example.drawonme;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity implements SensorEventListener {

	private SensorManager sensorManager;
	private Sensor accelerometerSensor;
    Sensor magnometerSensor;

	MyDrawingView myDrawingView;
	
	float[] accelerometerMatrix;
    float[] magnometerMatrix;
    
    float azimuth = 0.0f;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		myDrawingView = new MyDrawingView(this);
		setContentView(myDrawingView);
		
		//setContentView(R.layout.activity_main);
		
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);	
		accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

	}	

	@Override 
	protected void onPause() {
		super.onPause();
    	sensorManager.unregisterListener(this);
    }	
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) 
    	{
    		magnometerMatrix = event.values;
    	}
    	else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) 
    	{
    		accelerometerMatrix = event.values;
    	}		
		
		if (magnometerMatrix != null && accelerometerMatrix != null) {
    		float R[] = new float[9];
    		float I[] = new float[9];
    		
    		//https://developer.android.com/reference/android/hardware/SensorManager.html
    		boolean rotationMatrixSuccess = SensorManager.getRotationMatrix(R, I, accelerometerMatrix, magnometerMatrix);
    	    
    		if (rotationMatrixSuccess) {
    	        float orientation[] = new float[3];
    	        SensorManager.getOrientation(R, orientation);
    	        azimuth = orientation[0]; // orientation contains: azimuth (around z axis), pitch (around y axis) and roll
    	        Log.v("Bearing",""+azimuth);
    	    }    		
    	}		
		
		
		
		//Log.v("Accel","X: " + event.values[0]);
		//Log.v("Accel","Y: " + event.values[1]);
		//Log.v("Accel","Z: " + event.values[2]);		
		myDrawingView.addToXandY((int)event.values[0], (int)event.values[1]);
		
	}
	
	// Don't restart on rotation
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}
}
