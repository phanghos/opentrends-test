package org.taitasciore.android.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by roberto on 10/05/17.
 */

public class Character implements Serializable {

    private int id;
    private String name;
    private String description;
    private Image thumbnail;
    private ArrayList<Link> urls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArrayList<Link> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<Link> urls) {
        this.urls = urls;
    }
}
