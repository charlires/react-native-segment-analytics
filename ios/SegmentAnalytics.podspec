#
# Be sure to run `pod lib lint libdemo.podspec' to ensure this is a
# valid spec before submitting.
#
# Any lines starting with a # are optional, but their use is encouraged
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#

Pod::Spec.new do |s|
  s.name             = 'SegmentAnalytics'
  s.version          = '0.1.0'
  s.summary          = 'Segment Analytics library'

# This description is used to generate tags and improve search results.
#   * Think: What does it do? Why did you write it? What is the focus?
#   * Try to keep it short, snappy and to the point.
#   * Write the description between the DESC delimiters below.
#   * Finally, don't worry about the indent, CocoaPods strips it!

  s.description      = 'Library to use segment inside your app'
  s.homepage         = 'https://charlires.github.io'
  # s.screenshots     = 'www.example.com/screenshots_1', 'www.example.com/screenshots_2'
  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.author           = { 'Carlos Andonaegui' => 'charlires@gmail.com' }
  s.source           = { :git => 'https://github.com/charlires/react-native-segment-analytics.git', :branch => s.version.to_s }
  s.social_media_url = 'https://twitter.com/charlires'

  s.ios.deployment_target = '8.0'

  s.source_files = 'SegmentAnalytics/Classes/**/*.{h,m}'

  s.dependency 'Analytics'
end
