package com.github.dinceruur.androidSurfaceLevel;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.dinceruur.androidSurfaceLevel.CustomView.Circle;

public class MainActivity extends AppCompatActivity {

    Circle circle;
    TextView angleX;
    TextView angleY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        angleX = findViewById(R.id.angleX);
        angleY = findViewById(R.id.angleY);

        circle = findViewById(R.id.circle);
        circle.setCallerContext(this);
    }


    /*public void notifySensorChanged() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }*/
}
