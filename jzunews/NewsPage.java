package com.software.dzungvu.jzunews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.software.dzungvu.adapter.ElementsAdapter;
import com.software.dzungvu.configuration.Configuration;
import com.software.dzungvu.model.NewsElements;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsPage extends AppCompatActivity {

    private ListView lvNews;
    private NewsElements elements;
    private ArrayList<NewsElements>newsElementsArrayList;
    private ElementsAdapter elementsAdapter;
    private LinearLayout llNews;
    private Toolbar toolbar;




    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);
        this.setTitle("News Page");

        addControls();
        addEvents();
    }


    private void addControls() {
        lvNews = (ListView) findViewById(R.id.lvNews);
        llNews = (LinearLayout) findViewById(R.id.llNews);
        newsElementsArrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.newsToolBar);
        setSupportActionBar(toolbar);



        String link = Configuration.RSS_LINK + Configuration.NEWS_LINK;
        GetRssFile task =  new GetRssFile();
        task.execute(link);


        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewsPage.this, WebViewContent.class);
                intent.putExtra("URLNEWS", newsElementsArrayList.get(position).getLink());
                startActivity(intent);
            }
        });


        lvNews.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(NewsPage.this, PopupActivity.class));

                return true;
            }
        });





    }


    private void addEvents() {


    }


    public class GetRssFile extends AsyncTask<String, Void, ArrayList<NewsElements>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Loading data");
            progressDialog.setMessage("Please wait!");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<NewsElements> newsElementses) {
            super.onPostExecute(newsElementses);
            progressDialog.dismiss();
            elementsAdapter = new ElementsAdapter(NewsPage.this, R.layout.item_news, newsElementsArrayList);
            lvNews.setAdapter(elementsAdapter);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<NewsElements> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF-8");

                int eventType = xpp.getEventType();
                elements = new NewsElements();
                while (eventType != XmlPullParser.END_DOCUMENT){
                    if (eventType == XmlPullParser.START_TAG){
                        if (xpp.getName().equalsIgnoreCase("title")){
                            elements.setTitle(xpp.nextText().toString());
                        }
                        if (xpp.getName().equalsIgnoreCase("description")){
                            elements.setDescription(xpp.nextText().toString());
                        }
                        if (xpp.getName().equalsIgnoreCase("pubDate")){
                            elements.setPubDate(xpp.nextText().toString());
                        }
                        if (xpp.getName().equalsIgnoreCase("link")){
                            elements.setLink(xpp.nextText().toString());
                        }
                        if (xpp.getName().equalsIgnoreCase("guid")){
                            elements.setGuid(xpp.nextText().toString());
                        }
                    }
                    if (elements.getTitle()!=null && elements.getDescription()!=null
                            && elements.getPubDate() != null && elements.getLink() != null
                            && elements.getGuid() != null){
                        newsElementsArrayList.add(new NewsElements(
                                elements.getTitle().toString()
                                , elements.getDescription().toString()
                                , elements.getPubDate().toString()
                                , elements.getLink().toString()
                                , elements.getGuid().toString()));
                        elements = new NewsElements();
                    }
                    eventType = xpp.next();
                }
                return newsElementsArrayList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    public static InputStream getInputStream(URL url) {

        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_news_menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuBack:
                this.finish();
                return true;
            case R.id.mnu_news_grid_view:
                Toast.makeText(this, "Grid view", Toast.LENGTH_LONG).show();
                return true;
            case R.id.mnu_news_list_view:
                Toast.makeText(this, "List view", Toast.LENGTH_LONG).show();
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }


}
