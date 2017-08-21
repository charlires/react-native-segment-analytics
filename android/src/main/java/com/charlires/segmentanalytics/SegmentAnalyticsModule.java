package com.charlires.segmentanalytics;

import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;
import com.segment.analytics.Traits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class SegmentAnalyticsModule extends ReactContextBaseJavaModule {

    public SegmentAnalyticsModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SegmentAnalytics";
    }

    @ReactMethod
    public void setup(String configKey) {
        try {
            Analytics analytics = new Analytics.Builder(this.getReactApplicationContext(), configKey)
                    .trackApplicationLifecycleEvents() // Enable this to record certain application events automatically!
                    .recordScreenViews() // Enable this to record screen views automatically!
                    .build();
            Analytics.setSingletonInstance(analytics);
        } catch (Exception e) {
            Log.e("SegmentAnalyticsModule", "Failed to setup. " + e.getMessage());
        }
    }

    @ReactMethod
    public void identify(String userId, ReadableMap traits) {
        try {
            Analytics.with(this.getReactApplicationContext()).identify(
                userId,
                toTraits(traits),
                null
            );
        } catch (Exception e) {
            Log.e("SegmentAnalyticsModule", "Failed to identify " + userId + ". " + e.getMessage());
        }
    }

    @ReactMethod
    public void track(String trackText, ReadableMap properties) {
        try {
            Analytics.with(this.getReactApplicationContext()).track(
                trackText,
                this.toProperties(properties)
            );
        } catch (Exception e) {
            Log.e("SegmentAnalyticsModule", "Failed to track " + trackText + ". " + e.getMessage());
        }
    }

    @ReactMethod
    public void screen(String screenName, ReadableMap properties) {
        try {
            Analytics.with(this.getReactApplicationContext()).screen(
                null,
                screenName,
                this.toProperties(properties));
        } catch (Exception e) {
            Log.e("SegmentAnalyticsModule", "Failed to screen " + screenName + ". " + e.getMessage());
        }
    }

    @ReactMethod
    public void alias(String newId) {
        try {
            Analytics.with(this.getReactApplicationContext()).alias(
                newId,
                null
            );
        } catch (Exception e) {
            Log.e("SegmentAnalyticsModule", "Failed to alias " + newId + ". " + e.getMessage());
        }
    }

    private boolean nullOrEmpty(@Nullable ReadableMap readableMap) {
        return readableMap == null || !readableMap.keySetIterator().hasNextKey();
    }

    public Properties toProperties(@Nullable ReadableMap readableMap) {
        if (this.nullOrEmpty(readableMap)) {
            return null;
        }

        Properties properties = new Properties();
        properties.putAll(this.toMap(readableMap));

        return properties;
    }

    public Traits toTraits(@Nullable ReadableMap readableMap) {
        if (this.nullOrEmpty(readableMap)) {
            return null;
        }

        Traits traits = new Traits();
        traits.putAll(this.toMap(readableMap));

        return traits;
    }

    /**
     * Transforms ReadableMap to java.util.Map object
     * @param readableMap should not be null or empty
     * @return Map object of readableMap
     */
    private Map<String, Object> toMap (ReadableMap readableMap) {
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();

        Map<String, Object> map = new HashMap<>();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            ReadableType readableType = readableMap.getType(key);

            switch (readableType) {
                case Null:
                    map.put(key, null);
                    break;
                case Boolean:
                    map.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    // Can be int or double.
                    map.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    map.put(key, readableMap.getString(key));
                    break;
                case Map:
                    map.put(key, this.toMap(readableMap.getMap(key)));
                    break;
                case Array:
                    map.put(key, this.toArray(readableMap.getArray(key)));
                    break;
                default:
                    throw new IllegalArgumentException("Could not convert object with key: " + key + ".");
            }
        }
        return map;
    }

    private List<Object> toArray(@Nullable ReadableArray readableArray) {
        if (readableArray == null) {
            return null;
        }

        List<Object> list = new ArrayList<>();

        for (int i = 0; i < readableArray.size(); i++) {
            switch (readableArray.getType(i)) {
                case Null:
                    list.add(null);
                    break;
                case Boolean:
                    list.add(readableArray.getBoolean(i));
                    break;
                case Number:
                    list.add(readableArray.getDouble(i));
                    break;
                case String:
                    list.add(readableArray.getString(i));
                    break;
                case Map:
                    list.add(this.toMap(readableArray.getMap(i)));
                    break;
                case Array:
                    list.add(this.toArray(readableArray.getArray(i)));
                    break;
                default:
                    throw new IllegalArgumentException("Could not convert object at index: " + i + ".");
            }
        }
        return list;
    }
}
