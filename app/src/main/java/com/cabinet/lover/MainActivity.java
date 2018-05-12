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
import com.cabinet.lover.utils.MathUtils;
import com.cabinet.lover.utils.ScreenUtils;
import com.cabinet.lover.utils.Utils;
import com.cabinet.lover.view.LoveText;

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
    private LoveText loveText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenUtils.init(getApplication());
        init();

        double v = MathUtils.loveDouble();

        Log.e("TAG", "onCreate: "+v );
    }

    private void init() {
        // 根容器
        loverContainer = (ViewGroup) getWindow().getDecorView();
        // 起始点所在view
        mImageView = findViewById(R.id.imageView);
        // 文本显示
        textView = findViewById(R.id.textView);
        // 函数文本
        loveText = findViewById(R.id.loveText);
        // 心形采样点
        points = Utils.getPoints();
        // 事件处理
        mImageView.setOnTouchListener(this);
        // TODO view集合后续可做view动画
        itemViews = new ArrayList<>();

        // 调整位置
        textView.setY(-60);
        textView.setX(40);
    }

    /**
     * 启动动画
     */
    private void start() {
        // 200ms 延时
        handler.postDelayed(runnable, 200);
    }


    // 执行体
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

            // 贝赛尔曲线动画
            ValueAnimator animator = ValueAnimator.ofObject(new Bezier(new Point(location[0], location[1]), remove), new Point(location[0], location[1]), remove);
            // 时间
            animator.setDuration(TIMER);
            // 轨迹监听
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
        ValueAnimator animator = ValueAnimator.ofObject(new Bezier(new Point(location[0], location[1]), Utils.point), new Point(location[0], location[1]), Utils.point);
        animator.setStartDelay(TIMER);
        animator.setDuration(TIMER);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point value = (Point) animation.getAnimatedValue();
                mImageView.setX(value.x-20);
                mImageView.setY(value.y-60);
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

                showAnimationFunc();
            }
        });
        animator.start();

    }

    /**
     * 函数动画
     */
    private void showAnimationFunc() {

        loveText.setText("0");
        ValueAnimator animator = ValueAnimator.ofFloat(0, (float) MathUtils.loveDouble());
        animator.setStartDelay(500);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setVisibility(View.GONE);
                loveText.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.GONE);
                float value = (float) animation.getAnimatedValue();
                loveText.setText(value+"");
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loveText.moveFunc();
                    }
                }, 1000);
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
                Executors
                        .newSingleThreadExecutor()
                        .execute(new Runnable() {
                    @Override
                    public void run() {
                        while (isFlag) {
                            try {
                                start();
                                Thread.sleep(800);
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
