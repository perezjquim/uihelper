# UIHelper

Helper class that provides UI functionalities in a more simplified way.

## Install Instructions

Build.gradle (root):
```gradle
allprojects {
    repositories {
    	(...)
        mavenCentral()
        maven{
            url  'https://oss.sonatype.org/content/repositories/snapshots/'
            name 'OSS-Sonatype'
        }
        maven { url "https://jitpack.io" }
        (...)
    }
}
```

Build.gradle (app):
```gradle
dependencies
{
    (...)
    implementation 'com.github.perezjquim:uihelper:master-SNAPSHOT'
    (...)
}
```

## Examples of use

Toast:
```java
{
	(...)
	toast(getApplicationContext(),"Great success!");
	(...)
}
```

Notification:
```java
{
	(...)
	notify(getApplicationContext(),MainActivity.class,"Voice-mail","You have got one new message!");
	(...)
}
```
(...)
