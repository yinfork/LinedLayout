# LinedLayout
The Android EditText with line animation


## Screenshot
![](https://github.com/yinfork/LinedLayout/blob/master/demo.gif)

----

![](https://github.com/yinfork/LinedLayout/blob/master/demo2.gif)

## Usage

build.gradle:
	
	compile project(':linedlayout')
		
xml:
		
	<com.yinfork.linedlayout.LinedRelativeLayout
        android:id="@+id/layout_edit"
        android:layout_width="350dp"
        android:layout_height="match_parent"
        android:layout_marginLeft="20dp"
        android:layout_marginTop="20dp">

        <EditText
            android:id="@+id/edit1"
            android:layout_width="300dp"
            android:layout_height="40dp"
            android:background="@null"
            android:hint="@string/username"
            android:imeOptions="actionNext"
            android:singleLine="true"
            android:textColorHint="@color/gray" />

        <EditText
            android:id="@+id/edit2"
            android:layout_width="300dp"
            android:layout_height="40dp"
            android:layout_below="@id/edit1"
            android:layout_marginTop="30dp"
            android:background="@null"
            android:hint="@string/email"
            android:imeOptions="actionNext"
            android:singleLine="true"
            android:textColorHint="@color/gray" />
    </com.yinfork.linedlayout.LinedRelativeLayout>
		
		
java

	LinedRelativeLayout layout = (LinedRelativeLayout) findViewById(R.id.layout_edit);

    if(null != layout) {
        layout.setIgnoreFirstFocus(true);
        layout.setLineWidth(3);
        layout.setLineColor(getResources().getColor(R.color.colorPrimary));
        layout.setBendLength(70);
        layout.setLinePaddingBottom(5);
        layout.setLinePaddingLeft(0);
        layout.setLinePaddingRight(0);
        layout.setAnimDuration(300);
    }		
		
		
## Download

[Download Demo](https://github.com/yinfork/LinedLayout/blob/master/apk/demo.apk)
 		

## Contact & Help

Please fell free to contact me if there is any problem when using the library.

- **email**: yinfork@foxmail.com	