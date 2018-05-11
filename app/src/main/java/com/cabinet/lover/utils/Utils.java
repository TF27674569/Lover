package com.cabinet.lover.utils;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :  心形图案平布采点 工具类
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/11
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class Utils {

    private static final float scal = 2.0f;

    public static final Point point = new Point(ScreenUtils.getScreenWidth() / 2-20,ScreenUtils.getScreenHeight() / 2-100);

    /**
     * 获取心形图形上的所有点
     *
     *
     * @return
     */
    public static List<Point> getPoints() {
        List<Point> points = new ArrayList<>();
        float angle = 10;
        while (angle < 180) {
            Point p = getHeartPoint(angle);
            angle = angle + 1f;// 1f 可以更改
            points.add(p);
        }
        return points;
    }

    /**
     * 给定屏幕宽的一般
     * 屏幕高的1/4为中心点的心
     */
    private static Point getHeartPoint(float angle) {
        float t = (float) (angle / Math.PI);
        float x = (float) (19.5 * (16 * Math.pow(Math.sin(t), 3)));
        x = scal*x;
        float y = (float) (-20 * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));
        y = scal*y;
        return new Point(point.x + (int) x, point.y + (int) y);
    }
}
