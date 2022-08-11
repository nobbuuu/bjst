package com.dream.bjst.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IPreferences {

    /**
     * Removes a key from the preferences.
     * @param key The name of the preference to remove.
     */
    void remove(final String key);

    /**
     * Judge if a key exist in the preferences.
     * @param key The name of the preference to remove.
     */
    boolean contains(String key);

    /**
     * Retrieve a boolean value from the preferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return the preference value if it exists, or defaultValue.
     */
    boolean getBoolean(final String key, final boolean defaultValue);

    /**
     * See getBoolean(final String key, final boolean defaultValue).
     * @throws Exception If the key does not exist or the value is not a boolean.
     */
    boolean getBoolean(final String key) throws Exception;

    /**
     * Saves a boolean value to the preferences.
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     */
    void putBoolean(final String key, final boolean value);

    /**
     * Retrieve an int value from the preferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return the preference value if it exists, or defaultValue.
     */
    int getInt(final String key, final int defaultValue);

    /**
     * See getInt(final String key, final int defaultValue).
     * @throws Exception If the key does not exist or the value is not an integer.
     */
    int getInt(final String key) throws Exception;

    /**
     * Saves an int value to the preferences.
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     */
    void putInt(final String key, final int value);

    /**
     * Retrieve a String value from the preferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return the preference value if it exists, or defaultValue.
     */
    String getString(final String key, final String defaultValue);

    /**
     * See getString(final String key, final String defaultValue).
     * @throws Exception If the key does not exist or the value is not a String.
     */
    String getString(final String key) throws Exception;

    /**
     * Saves a String value in the preferences.
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     */
    void putString(final String key, final String value);


    /**
     * Retrieve a JSONObject, from the preferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return the preference value if it exists, or defaultValue.
     */
    JSONObject getJSONObject(final String key, final JSONObject defaultValue);

    /**
     * See getJSONObject(final String key, final JSONObject defaultValue).
     * @throws Exception If the key does not exist or the value is not a JSONObject.
     */
    JSONObject getJSONObject(final String key) throws Exception;

    /**
     * Saves a JSONObject value in the preferences.
     * @param key The name of the preference to modify.
     * @param jsonObject The new value for the preference.
     */
    void putJSONObject(final String key, final JSONObject jsonObject);


    /**
     * Retrieve a JSONArray, from the preferences.
     * @param key The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return the preference value if it exists, or defaultValue.
     */
    JSONArray getJSONArray(final String key, final JSONArray defaultValue);

    /**
     * See getJSONArray(final String key, final JSONArray defaultValue).
     * @throws Exception If the key does not exist or the value is not a JSONArray.
     */
    JSONArray getJSONArray(final String key) throws Exception;

    /**
     * Saves a JSONArray value in the preferences.
     * @param key The name of the preference to modify.
     * @param jsonArray The new value for the preference.
     */
    void putJSONArray(final String key, final JSONArray jsonArray);


}
