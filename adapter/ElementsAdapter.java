package com.software.dzungvu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.software.dzungvu.jzunews.R;
import com.software.dzungvu.model.NewsElements;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DzungVu on 7/8/2017.
 */

public class ElementsAdapter extends ArrayAdapter<NewsElements> {

    Activity context;
    int resource;
    List<NewsElements> objects;

    public ElementsAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<NewsElements> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View v = inflater.inflate(this.resource, null);

        ImageView imgNews = (ImageView) v.findViewById(R.id.imgNews);
        TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
        TextView txtPubDate = (TextView) v.findViewById(R.id.txtPubDate);
        LinearLayout llItem = (LinearLayout) v.findViewById(R.id.llItem);

        final NewsElements newsElements = this.objects.get(position);
        txtTitle.setText(newsElements.getTitle());
        txtPubDate.setText(newsElements.getPubDate());
        String imgLink;
        imgLink = newsElements.getDescription();
        if (imgLink.contains("src=")){
            imgLink = imgLink.substring(imgLink.indexOf("src=")+5, imgLink.indexOf("\" ></a>"));
            Picasso.with(context).load(imgLink).into(imgNews);
        }


        return v;


    }

}
