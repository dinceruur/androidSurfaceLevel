package com.github.dinceruur.androidSurfaceLevel.CustomView;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import com.github.dinceruur.androidSurfaceLevel.InterFaces.SensorInterFace;
import com.github.dinceruur.androidSurfaceLevel.MainActivity;
import com.github.dinceruur.androidSurfaceLevel.R;
import com.github.dinceruur.androidSurfaceLevel.Sensors.SensorListener;

public class Circle extends View implements SensorInterFace {
    Resources resources;
    MainActivity mainActivity;
    TextView    angleX;
    TextView    angleY;
    private SensorListener  fastestListener;
    private int padding;
    private int inRadius;
    private Paint inPaint;
    private Paint outPaint;
    private String inColor;
    private String outColor;
    private Context ctx;
    private boolean isRunning;
    float[] angle = new float[3];

    public Circle(Context context) {
        super(context);
    }

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        getAttributes(context,attrs);
    }

    public Circle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctx = context;
        getAttributes(context,attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String textX = String.format(resources.getString(R.string.x_axis), - angle[1]);
        String textY = String.format(resources.getString(R.string.y_axis), angle[2]);

        angleX = mainActivity.findViewById(R.id.angleX);
        angleY = mainActivity.findViewById(R.id.angleY);

        if(angleX != null && angleY != null){
            angleX.setText(textX);
            angleY.setText(textY);
        }

        if (!isRunning){
            this.isRunning = true;
            initializeFastestListener();
            inPaint     = new Paint();
            outPaint    = new Paint();
        }

        inPaint.setColor(Color.parseColor(inColor));
        outPaint.setColor(Color.parseColor(outColor));
        inPaint.setStyle(Paint.Style.FILL);
        outPaint.setStyle(Paint.Style.FILL);

        float width     = getWidth();
        float height    = getHeight();

        float x =  width / 2;
        float y =  height / 2;
        float min = Math.min(x,y);

        float outRad;
        float dx;
        float dy;

        if (x < y){
            outRad = min - padding;
            dx = -(angle[2] * outRad) / 180; // angle[2] rotation about y axis
            dy = (angle[1] * outRad) / 180; // angle[1] rotation about x axis
        }else{
            outRad = min - padding;
            dy = (angle[2] * outRad) / 180; // angle[2] rotation about y axis
            dx = (angle[1] * outRad) / 180; // angle[1] rotation about x axis
        }

        canvas.translate(x,y);
        canvas.drawCircle(dx, dy, outRad, outPaint);
        canvas.drawCircle(dx, dy, inRadius, inPaint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void notifySensorChanged() {
        angle = fastestListener.getFusedOrientation();
        invalidate();
    }

    private void initializeFastestListener(){
        fastestListener = new SensorListener(this);

        SensorManager mSensorManager = (SensorManager) ctx.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);

        mSensorManager.registerListener(fastestListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

        mSensorManager.registerListener(fastestListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_FASTEST);

        mSensorManager.registerListener(fastestListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_FASTEST);

        fastestListener.init();
    }

    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray   = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0);
        resources               = context.getApplicationContext().getResources();
        try {
            inColor     = typedArray.getString(R.styleable.CircleView_circleViewInCircleColor);
            outColor    = typedArray.getString(R.styleable.CircleView_circleViewOutCircleColor);
            padding     = typedArray.getInteger(R.styleable.CircleView_circleViewPadding,100);
            inRadius    = dpToPx(typedArray.getInteger(R.styleable.CircleView_circleViewInRadius,250));
        } finally {
            typedArray.recycle();
        }
    }

    public int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ctx.getResources().getDisplayMetrics());
    }

    public void setCallerContext(MainActivity activity){
        this.mainActivity = activity;
    }
}
