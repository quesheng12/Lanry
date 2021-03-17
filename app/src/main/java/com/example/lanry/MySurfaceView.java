package com.example.lanry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lanry.character.Bullet;
import com.example.lanry.character.Lanry;

import java.util.List;
import java.util.jar.Attributes;
import java.util.logging.Level;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Paint paint;
    private int second = 0;
    private int frame = 1;
    private int isBoooom = 0;
    Control control;

    public MySurfaceView(Context context, Boolean isRandom) {
        this(context, null, 0, isRandom);
    }

    public MySurfaceView(Context context, AttributeSet attrs, Boolean isRandom) {
        this(context, attrs, 0, isRandom);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr, Boolean isRandom) {
        super(context, attrs, defStyleAttr);
        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        control = new Control(this.getContext(), paint, isRandom);
        init();
    }

    private void init() {
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);

        //设置点击事件，区分双击和单击，拖动事件被集成到MyClickListener
        setOnTouchListener(new MyClickListener
                (new MyClickListener.MyClickCallBack() {

                    @Override
                    public void oneClick() {

                    }

                    @Override
                    public void doubleClick() {
//                        Toast.makeText(getContext(),"aaaaa",Toast.LENGTH_LONG).show();

                        if (control.getLanry().getBooomCount() > 0) {
                            control.boooom();
                            isBoooom = 20;
                        }
                    }
                }, control));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                draw();
            }
        }).start();
    }

    private void draw() {
        while (true) {
            try {
                mCanvas = mSurfaceHolder.lockCanvas();

                //清除之前画的
                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                //control
                if (!control.gameControl(second, frame, mCanvas)) {
                    if (!control.isWin) {
                        Intent intent = new Intent(getContext(), GameOverActivity.class);
                        intent.putExtra("isWin", control.isWin);
                        getContext().startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), LevelActivity.class);
                        getContext().startActivity(intent);
                    }
                    break;
                }


                //draw Lanry
                control.drawLanry(mCanvas, paint, frame, second);

                //Boooom Draw
                if (isBoooom > 0) {
                    control.drawBoooom(mCanvas, paint, isBoooom);
                    isBoooom--;
                }


                Thread.sleep(12);
                frame++;
                if (frame >= 100) {
                    second++;
                    frame = 0;
                }
            } catch (Exception e) {
                Log.e("draw: ", e.toString());
            } finally {
                if (mCanvas != null)
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);

            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        System.out.println("=========surfaceChanged========");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        System.out.println("=========surfaceDestroyed========");
    }
}




