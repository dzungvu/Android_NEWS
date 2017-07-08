package com.software.dzungvu.model;

/**
 * Created by DzungVu on 7/7/2017.
 */

public class NewsElements {

    public String title;
    public String description;
    public String pubDate;
    public String link;
    public String guid;

    public NewsElements() {
    }

    public NewsElements(String title, String description, String pubDate, String link, String guid) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
