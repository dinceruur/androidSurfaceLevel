package com.github.dinceruur.androidSurfaceLevel.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.github.dinceruur.androidSurfaceLevel.R;

public class MaskView extends View {

    private int padding;
    private Paint mTransparentPaint;
    private Paint mSemiBlackPaint;
    private Path mPath = new Path();

    public MaskView(Context context) {
        super(context);
        setWillNotDraw(false);
        initPaints();
    }

    public MaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context,attrs);
        setWillNotDraw(false);
        initPaints();
    }

    public MaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context,attrs);
        setWillNotDraw(false);
        initPaints();
    }

    private void initPaints() {
        mTransparentPaint = new Paint();
        mTransparentPaint.setColor(Color.TRANSPARENT);
        mTransparentPaint.setStrokeWidth(10);

        mSemiBlackPaint = new Paint();
        mSemiBlackPaint.setColor(Color.TRANSPARENT);
        mSemiBlackPaint.setStrokeWidth(10);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        //setZ(100);
        float w = (float) getWidth() / 2;
        float h = (float) getHeight() / 2;
        float rad = Math.min(w,h) - padding;

        mPath.addCircle(w, h, rad, Path.Direction.CW);
        mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        canvas.drawCircle(w, h, 550, mTransparentPaint);
        canvas.drawPath(mPath, mSemiBlackPaint);
        canvas.clipPath(mPath);
        canvas.drawColor(Color.parseColor("#FF2E2F34"));
    }


    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaskView, 0, 0);
        try {
            padding     = typedArray.getInteger(R.styleable.MaskView_maskViewPadding,100);
        } finally {
            typedArray.recycle();
        }
    }
}





