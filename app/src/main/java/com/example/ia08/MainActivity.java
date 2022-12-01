package com.example.ia08;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity  {

    private ImageView imageView;
    private float floatStartX = -1, floatStartY = -1,
    floatEndX = -1, floatEndY = -1;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint =  new Paint();
    private int opacity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paint.setColor(Color.BLACK);
        imageView = findViewById(R.id.imageView);
        setTitle("Painting App");

        SeekBar sb = (SeekBar)findViewById(R.id.seekBar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                opacity = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                paint.setAlpha(opacity);
            }
        });

    }

    private void Draw(){
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(imageView.getWidth(),
                    imageView.getHeight(),
                    Bitmap.Config.ARGB_8888);

            canvas = new Canvas(bitmap);

            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(8);
            paint.setAlpha(opacity);
        }
        canvas.drawLine(floatStartX, floatStartY-200, floatEndX, floatEndY-200, paint);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            floatStartX = event.getX();
            floatStartY = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            floatEndX = event.getX();
            floatEndY = event.getY();

            Draw();

            floatStartX = event.getX();
            floatStartY = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            floatEndX = event.getX();
            floatEndY = event.getY();

            Draw();
        }

        return super.onTouchEvent(event);
    }

    public void setColorRed(View view) {
        paint.setColor(Color.RED);
    }

    public void setColorGreen(View view) {
        paint.setColor(Color.GREEN);
    }

    public void setColorBlue(View view) {
        paint.setColor(Color.BLUE);
    }

    public void setColorBlack(View view) {
        paint.setColor(Color.BLACK);
    }

    public void setBrushSmall(View view) {
        paint.setStrokeWidth(4);
    }

    public void setBrushReg(View view) {
        paint.setStrokeWidth(8);
    }

    public void setBrushLarge(View view) {
        paint.setStrokeWidth(16);
    }

    public void setBrushXL(View view) {
        paint.setStrokeWidth(32);
    }

    public void clearCanvas(View view) {
    bitmap.eraseColor(Color.TRANSPARENT);
    }
}