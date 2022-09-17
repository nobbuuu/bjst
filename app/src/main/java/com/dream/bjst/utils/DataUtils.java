package com.dream.bjst.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataUtils {
    /**
     * 根据固定标识分组
     * fild 对象字段名
     * lpList全部集合
     */
    public static <T> Map<String, List<T>> groupByMode(String fild, List<T> lpList) {
        // 定义一个map集合用于分组
        Map<String, List<T>> mapList = new HashMap<String, List<T>>();
        for (Iterator<T> it = lpList.iterator(); it.hasNext(); ) {
            // 将循环读取的结果放入对象中
            T e = (T) it.next();
            Field declaredField;
            String str = null;
            try {
                declaredField = e.getClass().getDeclaredField(fild);
                declaredField.setAccessible(true);
                str = (String) declaredField.get(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            // 如果在这个map中包含有相同的键，这创建一个集合将其存起来
            if (mapList.containsKey(str)) {
                List<T> syn = mapList.get(str);
                syn.add(e);
                // 如果没有包含相同的键，在创建一个集合保存数据
            } else {
                List<T> syns = new ArrayList<T>();
                syns.add(e);
                mapList.put(str, syns);
            }
        }
        return mapList;
    }
}
