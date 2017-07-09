package com.software.dzungvu.jzunews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class WorldPage extends AppCompatActivity {

    ListView lvNews;
    NewsElements elements;
    ArrayList<NewsElements> newsElementsArrayList;
    ElementsAdapter elementsAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        addControls();
        addEvents();
    }


    private void addControls() {
        lvNews = (ListView) findViewById(R.id.lvNews);
        newsElementsArrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);


        String link = Configuration.RSS_LINK + Configuration.WORLD_LINK;
        GetRssFile task =  new GetRssFile();
        task.execute(link);


        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WorldPage.this, WebViewContent.class);
                intent.putExtra("URLNEWS", newsElementsArrayList.get(position).getLink());
                startActivity(intent);
            }
        });

    }

    private void addEvents() {

    }

    public class GetRssFile extends AsyncTask<String, Void, ArrayList<NewsElements>> {
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
            elementsAdapter = new ElementsAdapter(WorldPage.this, R.layout.item_news, newsElementsArrayList);
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

}
