package com.software.dzungvu.jzunews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.software.dzungvu.adapter.ElementsAdapter;
import com.software.dzungvu.model.NewsElements;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.prefs.PreferenceChangeEvent;

public class PopupActivity extends AppCompatActivity {

    TextView pop_txt_add_favor;
    TextView pop_txt_read_news;
    private String link, description, pubDate, title, guid;
    private ArrayList<NewsElements>elementsArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        pop_txt_add_favor = (TextView) findViewById(R.id.pop_txt_add_favor);
        pop_txt_read_news = (TextView) findViewById(R.id.pop_txt_read_news);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Bundle bundle = getIntent().getExtras();
        link = bundle.getString("LINK");
        description = bundle.getString("DESCRIPTION");
        pubDate = bundle.getString("PUBDATE");
        title = bundle.getString("TITLE");
        guid = bundle.getString("GUID");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("DATAREAD", null);

        elementsArrayList = new ArrayList<>();
        if (json == null){
            Toast.makeText(this, "No item was saved", Toast.LENGTH_LONG).show();
        }else{
            Type type = new TypeToken<ArrayList<NewsElements>>(){}.getType();
            elementsArrayList = gson.fromJson(json, type);


        }

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.2));

        pop_txt_add_favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavor();
            }
        });

        pop_txt_read_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readThisNews();
            }
        });

    }

    private void addToFavor() {
        elementsArrayList.add(new NewsElements(title, description, pubDate, link, guid));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(elementsArrayList);
        editor.putString("DATAREAD", json);
        editor.commit();

        Toast.makeText(this, "Added to your read later", Toast.LENGTH_LONG).show();
        this.finish();
    }

    private void readThisNews() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
        this.finish();
    }
}
