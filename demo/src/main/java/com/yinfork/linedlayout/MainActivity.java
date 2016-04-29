package com.yinfork.linedlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
