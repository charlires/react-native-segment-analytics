## react-native-segment-analytics

### Install
 
```
$ npm install react-native-segment-analytics --save
```

### IOS (CocoaPods)

```
pod 'Analytics'
pod 'React', :path => '../node_modules/react-native'
pod 'SegmentAnalytics', :path => '../node_modules/react-native-segment-analytics/ios'
```

#### Install the Cocoapods dependencies:

```
$ pod install
```

##### Headsup

Make sure that you have `libAnalytics.a` and `libSegmentAnalytics.a` in your *Linked Frameworks and Libraries* 
You may have to import them manually.

![image](https://cloud.githubusercontent.com/assets/1058646/16497002/71f1f6ce-3eba-11e6-9a0d-6e2900d1afc2.png)

### Android

#### Add in android/settings.gradle

```
include ':react-native-segment-analytics'
project(':react-native-segment-analytics').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-segment-analytics/android')
```

#### Add in android/app/build.gradle

```
dependencies {
    ......
    compile project(':react-native-segment-analytics')

}
```

**If you're on RN > 29 apply these changes in `MainApplication.java`, otherwise in `MainActivity.java`**

```java
import com.charlires.segmentanalytics.SegmentAnalyticsPackage;
...
@Override
protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
        new MainReactPackage(),
        new SegmentAnalyticsPackage() // <----- Register the module
    );
}
```

### Usage

#### Import the module
```
import RNSegmentIO from 'react-native-segment-analytics';
```

#### Set it up just ones inside your project 

```
RNSegmentIO.setup("YOUR-SEGMENT-KEY");
```

#### Use it

```
RNSegmentIO.setup(configKey: string)

RNSegmentIO.identify(userId: string, traits: Object)

RNSegmentIO.track(trackText: string, properties: Object)

RNSegmentIO.screen(screenName: string, properties: Object)

RNSegmentIO.alias(newId: string)
```

> **Note:** remember to import it in every file you are going to use it.
