package com.cabinet.lover.bezier;

import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Description : p0 起始点  p1 p2 控制点  p3 结束点
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/11
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class Bezier implements TypeEvaluator<Point> {

    // 由于这里使用的是三阶的贝塞儿曲线, 所以我们要定义两个控制点
    private Point p1;
    private Point p2;

    public Bezier(Point start, Point end) {
        // y轴中心点
        int dy = (start.y + end.y) / 2;
        int dx = (start.x + end.x) / 2;

        p1 = new Point((int) (dx - Math.random() * 400), (int) (dy - Math.random() * 150));
        p2 = new Point((int) (dx + Math.random() * 500), (int) (dy + Math.random() * 150));
    }

    @Override
    public Point evaluate(float t, Point p0, Point p3) {
        // 这里运用了三阶贝塞儿曲线的公式, 请自行上网查阅
        float temp = 1.0f - t;
        Point point = new Point();

        point.x = (int) (p0.x * temp * temp * temp + 3 * p1.x * t * temp * temp + 3 * p2.x * t * t * temp + p3.x * t * t * t);
        point.y = (int) (p0.y * temp * temp * temp + 3 * p1.y * t * temp * temp + 3 * p2.y * t * t * temp + p3.y * t * t * t);

        return point;
    }
}
