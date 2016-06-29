package com.gbox.segmentanalytics;

import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;
import com.segment.analytics.Traits;


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
            Log.e("SegmentAnalyticsModule", e.getMessage());
        }

    }

    @ReactMethod
    public void identify(String userId, ReadableMap traits) {
        Analytics.with(this.getReactApplicationContext()).identify(
                userId,
                toTraits(traits),
                null
        );

    }

    @ReactMethod
    public void track(String trackText, ReadableMap properties) {

        Analytics.with(this.getReactApplicationContext()).track(
                trackText,
                this.toProperties(properties)
        );
    }

    @ReactMethod
    public void screen(String screenName, ReadableMap properties) {
        Analytics.with(this.getReactApplicationContext()).screen(
                null,
                screenName,
                this.toProperties(properties));
    }

    public Properties toProperties(@Nullable ReadableMap readableMap) {
        if (readableMap == null) {
            return null;
        }

        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        if (!iterator.hasNextKey()) {
            return null;
        }

        Properties properties = new Properties();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            ReadableType readableType = readableMap.getType(key);

            switch (readableType) {
                case Null:
                    properties.putValue(key, null);
                    break;
                case Boolean:
                    properties.putValue(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    // Can be int or double.
                    properties.putValue(key, readableMap.getDouble(key));
                    break;
                case String:
                    properties.putValue(key, readableMap.getString(key));
                    break;
                case Map:
                    properties.putValue(key, this.toProperties(readableMap.getMap(key)));
                    break;
                case Array:
                    throw new UnsupportedOperationException("Arrays aren't supported yet.");
                default:
                    throw new IllegalArgumentException("Could not convert object with key: " + key + ".");
            }
        }
        return properties;
    }

    public Traits toTraits(@Nullable ReadableMap readableMap) {
            if (readableMap == null) {
                return null;
            }

            ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
            if (!iterator.hasNextKey()) {
                return null;
            }

            Traits traits = new Traits();
            while (iterator.hasNextKey()) {
                String key = iterator.nextKey();
                ReadableType readableType = readableMap.getType(key);

                switch (readableType) {
                    case Null:
                        traits.putValue(key, null);
                        break;
                    case Boolean:
                        traits.putValue(key, readableMap.getBoolean(key));
                        break;
                    case Number:
                        // Can be int or double.
                        traits.putValue(key, readableMap.getDouble(key));
                        break;
                    case String:
                        traits.putValue(key, readableMap.getString(key));
                        break;
                    case Map:
                        traits.putValue(key, this.toTraits(readableMap.getMap(key)));
                        break;
                    case Array:
                        throw new UnsupportedOperationException("Arrays aren't supported yet.");
                    default:
                        throw new IllegalArgumentException("Could not convert object with key: " + key + ".");
                }
            }
            return traits;
        }

}
