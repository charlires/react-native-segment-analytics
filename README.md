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

#### Register the module in getPackages of MainActivity.java

```
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
RNSegmentIO.screen("DEMO");
```

> **Note:** remember to import it in every file you are going to use it.