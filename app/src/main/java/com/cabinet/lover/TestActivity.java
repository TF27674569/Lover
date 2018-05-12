package com.cabinet.lover;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
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

public class TestActivity extends AppCompatActivity  {
    static final String[] sLover = {"1", "2", "8", "√", "e", "9", "8", "0"};
    private TextView text;
    private LoveText loveText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        text = findViewById(R.id.text);
        loveText = findViewById(R.id.loveText);
        Spannable WordtoSpan = new SpannableString("1 23√e 980");
//        WordtoSpan.setSpan(new AbsoluteSizeSpan(25), 0, 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(50), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(25), 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(35), 5, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(50), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(WordtoSpan);

        loveText.moveFunc();

    }


}
