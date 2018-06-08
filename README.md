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

Show/Hide/Toggle a view:
```java
{
	(...)
	hide(txtView);
	(...)
	show(txtView);
	(...)
	toggleVisibility(txtView);
	(...)
}
```

Toast:
```java
{
	(...)
	toast(ctx,"Great success!");
	(...)
}
```

Notification:
```java
{
	(...)
	notify(ctx,MainActivity.class,"Voice-mail","You have got one new message!");
	(...)
}
```

Progress dialog:
```java
{
	(...)
	openProgressDialog(ctx,"Loading data and stuff..");
	(...)
	closeProgressDialog();
	(...)
}
```

Create a notification:
```java
{
	(...)
    notify(ctx,Destination.class,"Message received!","Hi!");
    (...)
}
```

Input dialogs:
```java
{
	(...)
   	// Ask 'Yes/No'
    askBinary(ctx,"Confirmation stuff","Are you sure you wanna do this?",(input)->
    {
    	toast(ctx,"Confirmed!");
    });
    // Ask for a string
    askString(ctx,"Asking for a String...","Write a phrase",(input)->
    {
    	toast(ctx,"Inserted phrase:"+input);
    });
    // Ask for a double
    askDouble(ctx,"Asking for a double...","Write a number",(input)->
    {
    	toast(ctx,"Inserted double:"+input);
    });
}
```

(...)
