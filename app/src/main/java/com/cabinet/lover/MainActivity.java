package com.cabinet.lover;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cabinet.lover.bezier.Bezier;
import com.cabinet.lover.utils.ScreenUtils;
import com.cabinet.lover.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final long TIMER = 1500;

    // 变量相关
    private ViewGroup loverContainer;
    private List<Point> points;
    private ImageView mImageView;
    private Handler handler = new Handler();
    private List<View> itemViews;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenUtils.init(getApplication());
        init();
    }

    private void init() {
        loverContainer = (ViewGroup) getWindow().getDecorView();
        mImageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        points = Utils.getPoints();
        mImageView.setOnTouchListener(this);
        itemViews = new ArrayList<>();

        textView.setY(-60);
        textView.setX(40);
    }


    private void start() {
        handler.postDelayed(runnable, 80);
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 判断是否可以执行
            if (points.size() <= 0) {
                onEndLover();
                return;
            }

            // 获取在屏幕中的位置
            final int[] location = new int[2];
            mImageView.getLocationOnScreen(location);

            // 初始化view和位置
            final ImageView imageView = new ImageView(MainActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            imageView.setImageResource(R.mipmap.heart3);
            loverContainer.addView(imageView);
            itemViews.add(imageView);
            // 初始化位置
            imageView.setX(location[0]);
            imageView.setY(location[1]);

            // 随机取值
            int index = (int) (Math.random() * (points.size()));
            Point remove = points.remove(index);

            // 贝赛尔曲线
            ValueAnimator animator = ValueAnimator.ofObject(new Bezier(new Point(location[0], location[1]), remove), new Point(location[0], location[1]), remove);
            animator.setDuration(TIMER);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Point value = (Point) animation.getAnimatedValue();
                    imageView.setX(value.x);
                    imageView.setY(value.y);
                }
            });

            animator.start();

        }
    };


    /**
     * 飘心结束
     */
    private void onEndLover() {
        mImageView.setEnabled(false);

        final int[] location = new int[2];
        mImageView.getLocationOnScreen(location);
        Log.e("TAG", "location ( " + location[0] + " , " + +location[1] + " )");
        ValueAnimator animator = ValueAnimator.ofObject(new Bezier(new Point(location[0], location[1]), Utils.point), new Point(location[0], location[1]), Utils.point);
        animator.setStartDelay(TIMER);
        animator.setDuration(TIMER);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point value = (Point) animation.getAnimatedValue();
                mImageView.setX(value.x-20);
                mImageView.setY(value.y-60);
                    Log.e("TAG", "( " + value.x + " , " + +value.y + " )");
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mImageView.setScaleX(2.0f);
                mImageView.setScaleY(2.0f);
                textView.setText("I         U");
                textView.setTextSize(50);
                textView.setTextColor(Color.parseColor("#FFE15A52"));
            }
        });
        animator.start();

    }


    private boolean isFlag;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                isFlag = true;
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        while (isFlag) {
                            try {
                                Thread.sleep(60);
                                start();
                                if (points.size()<=0){
                                    break;
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                break;

            case MotionEvent.ACTION_UP:
                isFlag = false;
                break;
        }

        return true;
    }
}
