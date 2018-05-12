package com.cabinet.lover.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cabinet.lover.R;
import com.cabinet.lover.utils.MathUtils;
import com.cabinet.lover.utils.ScreenUtils;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/12
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class LoveText extends FrameLayout {


    public TextView text1;
    public LinearLayout endRoot;
    public View view,textRoot;

    public LoveText(Context context) {
        this(context, null);
    }

    public LoveText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_text, this);
        init();
    }

    private void init() {
        text1 = findViewById(R.id.text1);
        endRoot = findViewById(R.id.endRoot);
        textRoot = findViewById(R.id.textRoot);
        view = findViewById(R.id.view);
        endRoot.setVisibility(GONE);
        text1.setText(MathUtils.loveDouble()+"");
    }


    public void moveFunc(){
        endRoot.setVisibility(VISIBLE);
        text1.setText("1");

        ValueAnimator animator = ValueAnimator.ofFloat(0, ScreenUtils.dip2px(18));
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = (int) value;
                view.setLayoutParams(layoutParams);
            }
        });
        animator.start();
    }

    public void setText(String s) {
        text1.setText(s);
    }
}
