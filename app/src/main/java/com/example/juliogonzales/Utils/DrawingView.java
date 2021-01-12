package com.example.juliogonzales.Utils;


import android.content.Context;
import android.graphics.*;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

public class DrawingView extends View {

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF000000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    //detect drawing
    private Boolean detect = false;


    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();

    }

    private void setupDrawing(){
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        //respond to down, move and up events


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        //redraw
        invalidate();
        return true;
    }

    public void setPattern(Bitmap Pattern){
        //set pattern

        invalidate();
//        int patternID = getResources().getIdentifier(newPattern, "drawable", "com.example.drawingtest");
//        Log.d("TEST","HERE"+ patternID);
//
//        Bitmap patternBPM = BitmapFactory.decodeResource(getResources(),patternID);

//        Log.d("TEST","BPM"+ patternBPM);

        BitmapShader patternBPMshader = new BitmapShader(Pattern, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        drawPaint.setColor(0xFFFFFFFF);
        drawPaint.setShader(patternBPMshader);

    }

    public void setBrushWidth(int width){
        //set width
        drawPaint.setStrokeWidth(width);
    }

    public void setBrushColor(int color){
        //set color
        drawPaint.setShader(null);
        drawPaint.setColor(color);
    }
    public void clearCanvas(){
        if(drawCanvas != null){
            drawCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            invalidate();
            drawPath.reset();

        }
    }

    public File createFile(View view, Context context){
        View content = view;
        content.setDrawingCacheEnabled(true);
        content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = content.getDrawingCache();
        File file = new File(context.getCacheDir(),"drawingBackground.png");
        FileOutputStream ostream;
        try {
            file.createNewFile();
            ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            ostream.flush();
            ostream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Boolean getDetect(){
        return detect;
    }

}