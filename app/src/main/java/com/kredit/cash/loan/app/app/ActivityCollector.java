package com.kredit.cash.loan.app.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2022-08-12 on 12:50
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
public class ActivityCollector {
    public static List<Activity> activitys = new ArrayList<Activity>();

    /**
     * 向List中添加一个活动
     *
     * @param activity 活动
     */
    public static void addActivity(Activity activity) {

        activitys.add(activity);
    }

    /**
     * 从List中移除活动
     *
     * @param activity 活动
     */
    public static void removeActivity(Activity activity) {

        activitys.remove(activity);
    }

    /**
     * 将List中存储的活动全部销毁掉
     */
    public static void finishAll() {

        for (Activity activity : activitys) {

            if (!activity.isFinishing()) {

                activity.finish();
            }
        }
    }
}

