package org.josescalia.swingrss.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Josescalia
 * Date: 11/28/12
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class RssItem implements Serializable {

    private static final long serialVersionUID = -3234862089731580167L;
    private String title;
    private String link;
    private String guid;
    private String pubDate;
    private String category;
    private String description;

    public RssItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RssItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", guid='" + guid + '\'' +
                ", pubDate=" + pubDate +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
