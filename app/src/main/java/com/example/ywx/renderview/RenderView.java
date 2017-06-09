package com.example.ywx.renderview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ywx on 2017/6/9.
 */

public class RenderView extends View {
    private int count=6;
    private float angle= (float) (Math.PI*2/count);
    private float radius;
    private int centerX;
    private int centerY;
    private String[] title={"a","b","c","d","e","f"};
    private float[] data={80,64,65,60,100,50,10,20,40,90};
    private float maxValue=100;
    private Paint mainPaint;
    private Paint dataPaint;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public float[] getData() {
        return data;
    }

    public void setData(float[] data) {
        this.data = data;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    private Paint textPaint;
    public RenderView(Context context) {
        this(context,null);
    }

    public RenderView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public RenderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mainPaint=new Paint();
        dataPaint=new Paint();
        textPaint=new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setStrokeWidth(0.6f);
        mainPaint.setColor(Color.BLACK);
        dataPaint.setColor(Color.GRAY);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(36);
        textPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widhMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        if(widhMode==MeasureSpec.UNSPECIFIED)
        {
            widthSize=200;
        }
        if(heightMode==MeasureSpec.UNSPECIFIED)
        {
            heightSize=200;
        }
        radius=Math.min(heightSize,widthSize)/2*0.6f;
        centerX=widthSize/2;
        centerY=heightSize/2;
        setMeasuredDimension(widthSize,heightSize);
    }
    private void drawPolygon(Canvas canvas)
    {
        Path path=new Path();
        float r=radius/(count-1);
        Log.d("r",r+"");
        for(int i=1;i<count;i++)
        {
            float curR=r*i;
            Log.d("curR",curR+"");
            path.reset();
            for(int j=0;j<count;j++)
            {
                if(j==0)
                {
                    path.moveTo(centerX+curR,centerY);
                }
                else {
                    float x=(float)(centerX+curR*Math.cos(angle*j));
                    float y=(float)(centerY+curR*Math.sin(angle*j));
                    path.lineTo(x,y);
                }
            }
            path.close();
            canvas.drawPath(path,mainPaint);
        }
    }
    private void drawLines(Canvas canvas){
        Path path=new Path();
        for(int i=0;i<count;i++)
        {
            path.reset();
            path.moveTo(centerX,centerY);
            float x= (float) (centerX+radius*Math.cos(angle*i));
            float y=(float)(centerY+radius*Math.sin(angle*i));
            path.lineTo(x,y);
            canvas.drawPath(path,mainPaint);
        }
    }
    private void drawRegion(Canvas canvas) {
        Path path=new Path();
        dataPaint.setAlpha(255);
        for(int i=0;i<count;i++)
        {
            double percent=data[i]/maxValue;
            float x=(float)(centerX+radius*Math.cos(angle*i)*percent);
            float y=(float)(centerY+radius*Math.sin(angle*i)*percent);
            if(i==0)
            {
                path.moveTo(x,y);
            }
            else {
                path.lineTo(x,y);
            }
            canvas.drawCircle(x,y,6,dataPaint);
        }
        dataPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,dataPaint);
        dataPaint.setAlpha(127);
        dataPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path,dataPaint);
    }
    private void drawText(Canvas canvas){
        Paint.FontMetrics fontMetrics=textPaint.getFontMetrics();
        float fontHeight=fontMetrics.descent-fontMetrics.ascent;
        for(int i=0;i<count;i++)
        {
            String text=title[i]+" "+data[i];
            float x=(float)(centerX+(radius+fontHeight/2)*Math.cos(angle*i));
            float y=(float)(centerY+(radius+fontHeight/2)*Math.sin(angle*i));
            Log.d("angle",angle*i+"");
            if(angle*i>=0&&angle*i<=Math.PI/2)
            {
                canvas.drawText(text.toString(),x,y,textPaint);
            }else if(angle*i>=3*Math.PI/2&&angle*i<=Math.PI*2){
            canvas.drawText(text.toString(), x,y,textPaint);
        }else if(angle*i>Math.PI/2&&angle*i<=Math.PI){
            float dis = textPaint.measureText(text);
            canvas.drawText(text.toString(), x-dis,y,textPaint);
        }else if(angle*i>=Math.PI&&angle*i<3*Math.PI/2){
            float dis = textPaint.measureText(text);
            canvas.drawText(text.toString(), x-dis,y,textPaint);
        }
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        drawPolygon(canvas);
        drawLines(canvas);
        drawRegion(canvas);
        drawText(canvas);
    }
}
