package com.software.dzungvu.jzunews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout llNews, llWorld, llBussiness, llEntertaiment, llSport, llMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addControls();
        addEvents();
    }

    private void addControls() {
        llNews = (LinearLayout) findViewById(R.id.llNews);
        llWorld = (LinearLayout) findViewById(R.id.llWorld);
        llBussiness = (LinearLayout) findViewById(R.id.llBussiness);
        llEntertaiment = (LinearLayout) findViewById(R.id.llEntertaiment);
        llSport = (LinearLayout) findViewById(R.id.llSport);
        llMore = (LinearLayout) findViewById(R.id.llMore);

    }

    private void addEvents() {
        llNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNewsPage();
            }
        });

        llWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoWorldPage();
            }
        });

        llBussiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoBussinessPage();
            }
        });

        llEntertaiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoEntertaimentPage();
            }
        });

        llSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSportPage();
            }
        });

        llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMorePage();
            }
        });

    }

    private void gotoNewsPage() {

        Intent intent = new Intent(this, NewsPage.class);
        startActivity(intent);

    }

    private void gotoWorldPage() {

        Intent intent = new Intent(this, WorldPage.class);
        startActivity(intent);

    }

    private void gotoBussinessPage() {

        Intent intent = new Intent(this, BussinessPage.class);
        startActivity(intent);

    }

    private void gotoEntertaimentPage() {

        Intent intent = new Intent(this, EntertaimentPage.class);
        startActivity(intent);

    }

    private void gotoSportPage() {

        Intent intent = new Intent(this, SportPage.class);
        startActivity(intent);

    }

    private void gotoMorePage () {
        Intent intent = new Intent(this, MorePage.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuFavouriteNews:
                Intent intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                return true;
            case R.id.mnuSetting:
                Toast.makeText(this, "S", Toast.LENGTH_LONG).show();
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }
}
