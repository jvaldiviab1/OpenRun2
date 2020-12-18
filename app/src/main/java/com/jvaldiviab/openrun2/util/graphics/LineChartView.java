package com.jvaldiviab.openrun2.util.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.jvaldiviab.openrun2.util.UtilsValidate;

import java.util.ArrayList;
import java.util.List;

public class LineChartView extends View {

    private float mSelfWidth;
    private float mSelfHeight;
    private Paint mPaint;
    private float mData[] = {8.01f, 7.35f, 6.78f};
    private String mDays[] = {"X", "Y", "Z"};
    float bottomTextW;
    float bottomTextH;
    float horizontalSpace = 10F;
    float verticalSpace = 10F;
    float bottomVerticalLineHeight = 20F;
    float textWithLineSpace = bottomVerticalLineHeight + 20F;
    float proportionWidth;
    float bottomHeight;
    float topTextHeight = 120F;
    float insideRadius = 6;
    float lineWidth = 1F;
    Path linePath;
    List<PointF> points = new ArrayList<>();
    float topTextSpace = 20f;


    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePath = new Path();
        Rect rect = new Rect();
        mPaint.getTextBounds(mDays[0],0, mDays[0].length(), rect);
        bottomTextW = rect.width();
        bottomTextH = rect.height();
        proportionWidth = (mSelfWidth - 2 * horizontalSpace) / mDays.length;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSelfWidth = w;
        mSelfHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        initPoint();
        drawTitle(canvas);
        drawBottomTitle(canvas);
        drawLeftTitle(canvas);
        drawBottomText(canvas);
        drawBottomLine(canvas);
        drawBrokenLine(canvas);
        drawTopText(canvas);
    }

    private void initPoint() {
        float brokenLineHeight = mSelfHeight - topTextHeight - bottomHeight;
        float proportionHeight = brokenLineHeight / getMaxData();
        float circleCenterX = horizontalSpace + proportionWidth / 2;
        float circleCenterY;
        points.clear();
        for (int i = 0; i < mData.length; i++) {
            float currentProportionHeight = mData[i] * proportionHeight;
            circleCenterY = mSelfHeight - bottomHeight - currentProportionHeight;
            points.add(new PointF(circleCenterX, circleCenterY));
            circleCenterX += proportionWidth;
        }
    }

    private void  drawTitle(Canvas canvas) {
        float currentTextTitleX = 0;
        float currentTextTitleY = 0;
        currentTextTitleX = mSelfWidth / 2;
        currentTextTitleY = 50;
        mPaint.setTextSize(UtilsValidate.sp2px(getContext(), 18));
        mPaint.setColor(Color.parseColor("#666666"));
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("PROGRESO", currentTextTitleX, currentTextTitleY,mPaint);
    }

    private void drawBottomTitle(Canvas canvas) {
        float currentTextTitleX = 0;
        float currentTextTitleY = 0;
        currentTextTitleX = mSelfWidth / 2;
        currentTextTitleY = mSelfHeight;
        mPaint.setTextSize(UtilsValidate.sp2px(getContext(), 13));
        mPaint.setColor(Color.parseColor("#666666"));
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Días", currentTextTitleX, currentTextTitleY,mPaint);
    }

    private void drawLeftTitle(Canvas canvas) {

    }

    private void drawBottomText(Canvas canvas) {
        float currentTextX = 0;
        float currentTextY = 0;
        currentTextX = proportionWidth / 2 + horizontalSpace;
        currentTextY = mSelfHeight - verticalSpace-25;
        mPaint.setTextSize(UtilsValidate.sp2px(getContext(), 13));
        mPaint.setColor(Color.parseColor("#666666"));
        mPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < mDays.length; i++) {
            canvas.drawText(mDays[i], currentTextX, currentTextY, mPaint);
            currentTextX += proportionWidth;
        }
    }

    private void drawBottomLine(Canvas canvas) {
        float bottomLineStartX = horizontalSpace;
        float bottomLineStopX = mSelfWidth - horizontalSpace;
        float bottomLineStartY = mSelfHeight - bottomTextH - textWithLineSpace - verticalSpace-25;
        float bottomLineStopY = bottomLineStartY;

        mPaint.setColor(Color.parseColor("#FF602A"));
        mPaint.setStrokeWidth(lineWidth);

        canvas.drawLine(bottomLineStartX, bottomLineStartY, bottomLineStopX, bottomLineStopY, mPaint);
        float verticalLineStartX = horizontalSpace;
        float verticalLineStartY = mSelfHeight - bottomTextH - textWithLineSpace - verticalSpace;
        float verticalLineStopX = verticalLineStartX;
        float verticalLineStopY = verticalLineStartY + bottomVerticalLineHeight;

        bottomHeight = mSelfHeight - verticalLineStartY;
    }

    private void drawBrokenLine(Canvas canvas) {
        for (int i = 0; i < points.size(); i++) {

            mPaint.setColor(Color.parseColor("#FF602A"));
            canvas.drawCircle(points.get(i).x, points.get(i).y, insideRadius, mPaint);
        }

        linePath.reset();
        for (int i = 0; i < points.size(); i++) {
            if (i == 0) {
                linePath.moveTo(points.get(i).x, points.get(i).y);
            } else {
                linePath.lineTo(points.get(i).x, points.get(i).y);
            }
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setColor(Color.parseColor("#FF602A"));
        canvas.drawPath(linePath, mPaint);
    }


    private void drawTopText(Canvas canvas) {
        Rect rect = new Rect();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#333333"));
        mPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < mData.length; i++) {
            float textX = points.get(i).x;
            float textY = points.get(i).y - topTextSpace;
            canvas.drawText(String.valueOf(mData[i]), textX, textY, mPaint);
        }
    }

    private float getMaxData() {
        float max = 0;
        for (int i = 0; i < mData.length; i++) {
            max = Math.max(max, mData[i]);
        }
        return max;
    }

    public void setData(float[] data, String[] years) {
        if (data.length != years.length) {
            throw new RuntimeException("Datos no coinciden");
        }
        mData = data;
        mDays = years;
        invalidate();
    }

}