package com.tt.percentdigitalview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.tt.percentdigitalview.R;

/**
 * Created by TuoZhaoBing on 2016/4/7 0007.
 */
public class PercentDigitalView extends View implements ViewTreeObserver.OnGlobalLayoutListener{

    public static final String TAG = "PercentDigitalView";
    private Context mContext;
    private int mCircleX;
    private float mRadius;
    private int mTmpSweepValue =0,mSweepValue;
    private RectF mInnerArcRectF;
    private Paint mCirclePaint,mArcPaint,mTextPaint;
    public String mShowText = "%";
    private int mWidth,mHeight;
    private int mInnerArcWidth;

    public PercentDigitalView(Context context) {
        super(context);
        init(context,null);
    }

    public PercentDigitalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public PercentDigitalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public void init(Context context,AttributeSet attrs){
        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PercentDigitalView);
        mRadius = ta.getInteger(R.styleable.PercentDigitalView_innerCircleRadius,100);
        mInnerArcWidth = ta.getInteger(R.styleable.PercentDigitalView_ringWidth,50);
        mSweepValue = ta.getInteger(R.styleable.PercentDigitalView_ringPercent,350);
        ta.recycle();

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStyle(Paint.Style.FILL);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(Color.BLACK);
        mCirclePaint.setColor(Color.GREEN);
        mArcPaint.setColor(Color.GREEN);
        mArcPaint.setStrokeWidth((float)(mInnerArcWidth));
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCircleX = mWidth/2;
        mInnerArcRectF = new RectF((float) (mWidth*0.1),
                (float) (mWidth*0.1),
                (float) (mWidth*0.9),
                (float) (mWidth*0.9));
        canvas.drawCircle(mCircleX,mCircleX,mRadius,mCirclePaint);
        canvas.drawArc(mInnerArcRectF,270,mTmpSweepValue,false,mArcPaint);
        java.text.DecimalFormat df=new java.text.DecimalFormat("#.##");
        canvas.drawText(mShowText+(df.format(mTmpSweepValue*100/360d)),0,mShowText.length()+(df.format(mTmpSweepValue*100/360d)+"").length(),mCircleX,mCircleX+(mShowText.length()/4),mTextPaint);
        if (mTmpSweepValue < mSweepValue){
            mTmpSweepValue += 1;
            postInvalidate();
        }
        super.onDraw(canvas);
    }

    @Override
    public void onGlobalLayout() {
        mWidth = getWidth();
        mHeight = getHeight();
    }
}
