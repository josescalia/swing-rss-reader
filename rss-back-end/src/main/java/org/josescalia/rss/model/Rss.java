package org.josescalia.rss.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Josescalia
 * Date: 11/28/12
 * Time: 7:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "RSS_FEEDER")
public class Rss {

    private Long id;
    private String title;
    private String link;

    public Rss() {
    }

    public Rss(String title, String link) {
        this.title = title;
        this.link = link;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TITLE", length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "LINK", length = 100)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Rss{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
