package com.feresr.weather.models;

import java.io.Serializable;

/**
 * Created by Fernando on 4/11/2015.
 */
public class Alert implements Serializable {
    private String title;
    private String description;
    private String uri;
    private String expires;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
