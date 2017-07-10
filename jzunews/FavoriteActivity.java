package com.software.dzungvu.jzunews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.software.dzungvu.adapter.ElementsAdapter;
import com.software.dzungvu.model.NewsElements;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    ListView lvFavorNews;
    ElementsAdapter adapter;
    ArrayList<NewsElements> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        this.setTitle("Save News");

        arrayList = new ArrayList<>();
        lvFavorNews = (ListView) findViewById(R.id.lvFavorNews);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("DATAREAD", null);
        if (json == null){
            Toast.makeText(this, "No item was saved", Toast.LENGTH_LONG).show();
        }else{
            Type type = new TypeToken<ArrayList<NewsElements>>(){}.getType();
            arrayList = gson.fromJson(json, type);
            adapter = new ElementsAdapter(this, R.layout.item_news, arrayList);
            lvFavorNews.setAdapter(adapter);

        }

        lvFavorNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavoriteActivity.this, WebViewContent.class);
                intent.putExtra("URLNEWS", arrayList.get(position).getLink());
                startActivity(intent);
            }
        });

        lvFavorNews.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return true;
            }
        });

    }

}
