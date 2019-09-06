package com.github.dinceruur.androidSurfaceLevel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.github.dinceruur.androidSurfaceLevel.InterFaces.SensorInterFace;


public class MainActivity extends AppCompatActivity implements SensorInterFace {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void notifySensorChanged() {

    }
}
