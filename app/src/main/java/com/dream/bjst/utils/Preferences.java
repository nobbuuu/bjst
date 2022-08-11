package com.dream.bjst.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * // 在具体需要调用的地方，其他类型的照旧即可。
 * // 存
 * Preferences.getPreferences().putInt("myKey", -1);
 * // 取
 * Preferences.getPreferences().getInt("myKey", -1)
 */
public class Preferences implements IPreferences {
    private static final String TAG = "Preferences";

    private SharedPreferences preferences;

    @SuppressLint("StaticFieldLeak")
    private static Preferences instance = null;


    public static Preferences init(Context context) {
        if (instance == null) {
            synchronized (Preferences.class) {
                if (instance == null) {
                    instance = new Preferences(context);
                }
            }
        }
        return instance;
    }

    private Preferences(Context context) {
        if (preferences == null)
            preferences = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
    }

    public static Preferences getPreferences() {
        return instance;
    }

    private void throwIfKeyDoesNotExist(final String key) throws Exception {
        if (!preferences.contains(key)) {
            throw new Exception("Key " + key + " does not exist in preferences.");
        }
    }

    @Override
    public synchronized void remove(final String key) {
        preferences.edit().remove(key).apply();
    }

    @Override
    public synchronized boolean contains(String key) {

        return preferences.contains(key);
    }

    @Override
    public synchronized boolean getBoolean(final String key, final boolean defaultValue) {
        boolean returnedValue = defaultValue;
        try {
            returnedValue = preferences.getBoolean(key, defaultValue);
        } catch (Exception e) {
            //TODO:这儿可以自定义自己的错误日志打印

        }
        return returnedValue;
    }

    @Override
    public synchronized boolean getBoolean(final String key) throws Exception {
        throwIfKeyDoesNotExist(key);
        return getBoolean(key, false);
    }

    @Override
    public synchronized void putBoolean(final String key, final boolean value) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putBoolean(key, value);
        preferencesEditor.apply();
    }

    @Override
    public synchronized int getInt(final String key, final int defaultValue) {
        int returnedValue = defaultValue;
        try {
            returnedValue = preferences.getInt(key, defaultValue);
        } catch (Exception e) {
            //TODO:这儿可以自定义自己的错误日志打印
        }
        return returnedValue;
    }

    @Override
    public synchronized int getInt(final String key) throws Exception {
        throwIfKeyDoesNotExist(key);
        return getInt(key, 0);
    }

    @Override
    public synchronized void putInt(final String key, final int value) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putInt(key, value);
        preferencesEditor.apply();
    }

    @Override
    public synchronized String getString(final String key, final String defaultValue) {
        String returnedValue = defaultValue;
        try {
            returnedValue = preferences.getString(key, defaultValue);
        } catch (Exception e) {
            //TODO:这儿可以自定义自己的错误日志打印
        }
        return returnedValue;
    }

    @Override
    public synchronized String getString(final String key) throws Exception {
        throwIfKeyDoesNotExist(key);
        return getString(key, "");
    }

    @Override
    public synchronized void putString(final String key, final String value) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(key, value);
        preferencesEditor.apply();

    }

    @Override
    public JSONObject getJSONObject(final String key, final JSONObject defaultValue) {
        JSONObject returnedValue;
        try {
            String jsonString = preferences.getString(key, "");
            returnedValue = (!jsonString.isEmpty() ? new JSONObject(jsonString) : null);
        } catch (Exception e) {
            returnedValue = defaultValue;
        }
        return returnedValue;
    }

    @Override
    public synchronized JSONObject getJSONObject(final String key) throws Exception {
        throwIfKeyDoesNotExist(key);
        return getJSONObject(key, null);
    }

    @Override
    public void putJSONObject(final String key, JSONObject jsonObject) {
        putString(key, jsonObject.toString());
    }

    @Override
    public JSONArray getJSONArray(final String key, final JSONArray defaultValue) {
        JSONArray returnedValue;
        try {
            String jsonString = preferences.getString(key, "");
            returnedValue = (!jsonString.isEmpty() ? new JSONArray(jsonString) : null);
        } catch (Exception e) {
            returnedValue = defaultValue;
        }
        return returnedValue;
    }

    @Override
    public synchronized JSONArray getJSONArray(final String key) throws Exception {
        throwIfKeyDoesNotExist(key);
        return getJSONArray(key, null);
    }

    @Override
    public void putJSONArray(final String key, final JSONArray jsonArray) {
        putString(key, jsonArray.toString());
    }


}
