package com.software.dzungvu.jzunews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class PopupActivity extends AppCompatActivity {

    TextView pop_txt_add_favor;
    TextView pop_txt_read_news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        pop_txt_add_favor = (TextView) findViewById(R.id.pop_txt_add_favor);
        pop_txt_read_news = (TextView) findViewById(R.id.pop_txt_read_news);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.2));



    }
}
