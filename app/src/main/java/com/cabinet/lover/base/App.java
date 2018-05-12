package com.cabinet.lover.base;

import android.app.Application;

import com.cabinet.lover.utils.ScreenUtils;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/12
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ScreenUtils.init(this);
    }
}
