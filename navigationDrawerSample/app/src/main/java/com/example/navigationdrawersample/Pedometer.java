package com.example.navigationdrawersample;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Pedometer extends AppCompatActivity implements SensorEventListener
{

    private int walking = 0;
    private int step = 0;
    private int running = 0,jogging = 0;
    TextView stepCount,stepType,xmove;
    private int RUNNING_THRESHOLD = 30;
    private int JOGGING_THRESHOLD = 25;
    private int WALKING_THRESHOLD = 17;


    public static final int MAX_BUFFER_SIZE = 5;

    private static final int Y_DATA_COUNT = 4;
    private static final double MIN_GRAVITY = 2;
    private static final double MAX_GRAVITY = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        stepCount = findViewById(R.id.stepCount);
        stepType = findViewById(R.id.movementType);
        xmove = findViewById(R.id.xmove);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null)
        {
            Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if(sensorAccelerometer != null)
            {
                sensorManager.registerListener(this,sensorAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        else
        {
            Toast.makeText(this, "Sensor not working", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSensorChanged(final SensorEvent sensorEvent)
    {
        final float[] values = sensorEvent.values;
        final Sensor sensor = sensorEvent.sensor;

        if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            magneticDetector(values, sensorEvent.timestamp / (500 * 10 ^ 6l));
        }
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            accelDetector(values, sensorEvent.timestamp / (500 * 10 ^ 6l));
        }
    }

    private ArrayList<float[]> mAccelDataBuffer = new ArrayList<float[]>();
    private ArrayList<Long> mMagneticFireData = new ArrayList<Long>();
    private Long mLastStepTime = null;
    private ArrayList<Pair> mAccelFireData = new ArrayList<Pair>();


    private void accelDetector(float[] detectedValues, long timeStamp)
    {
        float[] currentValues = new float[3];
        for (int i = 0; i < currentValues.length; ++i)
        {
            currentValues[i] = detectedValues[i];
        }
        mAccelDataBuffer.add(currentValues);
        if (mAccelDataBuffer.size() > Pedometer.MAX_BUFFER_SIZE)
        {
            double avgGravity = 0;
            for (float[] values : mAccelDataBuffer)
            {
                avgGravity += Math.abs(Math.sqrt(
                        values[0] * values[0] + values[1] * values[1] + values[2] * values[2]) -    SensorManager.STANDARD_GRAVITY);
                xmove.setText("x axis: "+String.valueOf(values[0]) + "\ny axis: "+String.valueOf(values[1]) + "\nz axis: "+String.valueOf(values[2]));
            }
            avgGravity /= mAccelDataBuffer.size();

            if (avgGravity >= MIN_GRAVITY && avgGravity < MAX_GRAVITY)
            {
                mAccelFireData.add(new Pair(timeStamp, true));
                walking++;
                stepCount.setText(String.valueOf(walking));
            }
            else
            {
                mAccelFireData.add(new Pair(timeStamp, false));
            }

            if (mAccelFireData.size() >= Y_DATA_COUNT)
            {
                checkData(mAccelFireData, timeStamp);

                mAccelFireData.remove(0);
            }

            mAccelDataBuffer.clear();
        }
    }

    private void checkData(ArrayList<Pair> accelFireData, long timeStamp)
    {
        boolean stepAlreadyDetected = false;

        Iterator<Pair> iterator = accelFireData.iterator();
        while (iterator.hasNext() && !stepAlreadyDetected)
        {
            stepAlreadyDetected = iterator.next().first.equals(mLastStepTime);
        }
        if (!stepAlreadyDetected)
        {
            int firstPosition = Collections.binarySearch(mMagneticFireData, accelFireData.get(0).first);
            int secondPosition = Collections
                    .binarySearch(mMagneticFireData, accelFireData.get(accelFireData.size() - 1).first - 1);

            if (firstPosition > 0 || secondPosition > 0 || firstPosition != secondPosition)
            {
                if (firstPosition < 0)
                {
                    firstPosition = -firstPosition - 1;
                }
                if (firstPosition < mMagneticFireData.size() && firstPosition > 0)
                {
                    mMagneticFireData = new ArrayList<Long>(
                            mMagneticFireData.subList(firstPosition - 1, mMagneticFireData.size()));
                }

                iterator = accelFireData.iterator();
                while (iterator.hasNext())
                {
                    if (iterator.next().second)
                    {
                        mLastStepTime = timeStamp;
                        accelFireData.remove(accelFireData.size() - 1);
                        accelFireData.add(new Pair(timeStamp, false));
//                        onStep();
                        break;
                    }
                }
            }
        }
    }

    private float mLastDirections;
    private float mLastValues;
    private float mLastExtremes[] = new float[2];
    private Integer mLastType;
    private ArrayList<Float> mMagneticDataBuffer = new ArrayList<Float>();

    private void magneticDetector(float[] values, long timeStamp)
    {
        mMagneticDataBuffer.add(values[2]);

        if (mMagneticDataBuffer.size() > Pedometer.MAX_BUFFER_SIZE)
        {
            float avg = 0;

            for (int i = 0; i < mMagneticDataBuffer.size(); ++i)
            {
                avg += mMagneticDataBuffer.get(i);
            }

            avg /= mMagneticDataBuffer.size();

            float direction = (avg > mLastValues ? 1 : (avg < mLastValues ? -1 : 0));
            if (direction == -mLastDirections)
            {
                // Direction changed
                int extType = (direction > 0 ? 0 : 1); // minumum or maximum?
                mLastExtremes[extType] = mLastValues;
                float diff = Math.abs(mLastExtremes[extType] - mLastExtremes[1 - extType]);

                if (diff > 8 && (null == mLastType || mLastType != extType))
                {
                    mLastType = extType;

                    mMagneticFireData.add(timeStamp);
                }
            }
            mLastDirections = direction;
            mLastValues = avg;

            mMagneticDataBuffer.clear();
        }
    }

    public static class Pair implements Serializable
    {
        Long first;
        boolean second;

        public Pair(long first, boolean second)
        {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o)
        {
            if (o instanceof Pair)
            {
                return first.equals(((Pair) o).first);
            }
            return false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}