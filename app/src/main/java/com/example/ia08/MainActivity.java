package com.example.ia08;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity  {

    private ImageView imageView;
    private float floatStartX = -1, floatStartY = -1,
    floatEndX = -1, floatEndY = -1;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint =  new Paint();
    private int opacity = 255;

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
                opacity = (int) (255 * (progress * 0.01));
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

    private void Draw() {
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

    public void resetOpacity() {
        SeekBar sb = (SeekBar)findViewById(R.id.seekBar);
        sb.setProgress(100);
        opacity = 255;
        paint.setAlpha(opacity);
    }
    public void setColorRed(View view) {
        paint.setColor(Color.RED);
        resetOpacity();
    }

    public void setColorGreen(View view) {
        paint.setColor(Color.GREEN);
        resetOpacity();
    }

    public void setColorBlue(View view) {
        paint.setColor(Color.BLUE);
        resetOpacity();
    }

    public void setColorBlack(View view) {
        paint.setColor(Color.BLACK);
        resetOpacity();
    }

    public void setBrushSmall(View view) { paint.setStrokeWidth(4); }

    public void setBrushReg(View view) { paint.setStrokeWidth(8); }

    public void setBrushLarge(View view) {paint.setStrokeWidth(16); }

    public void setBrushXL(View view) { paint.setStrokeWidth(32);}

    public void clearCanvas(View view) {
    bitmap.eraseColor(Color.TRANSPARENT);
    }

    public void saveImageButton(View view) {
        File savedImage = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                Calendar.getInstance().getTime().toString() + ".jpg");

        try {

            FileOutputStream fileOutputStream = new FileOutputStream(savedImage);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            MediaStore.Images.Media.insertImage(getContentResolver()
                    ,savedImage.getAbsolutePath(),savedImage.getName(),savedImage.getName());
            Toast.makeText(this,
                    "File Saved Successfully",
                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}