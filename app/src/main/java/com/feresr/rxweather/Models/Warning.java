package com.feresr.rxweather.models;

import java.io.Serializable;

/**
 * Created by Fernando on 17/11/2015.
 */
public class Warning extends DisplayWeatherInfo implements Serializable {
    protected String text;
    protected String explanation;

    public Warning(String text, String explanation) {
        super();
        this.text = text;
        this.explanation = explanation;
    }

    public String getText() {
        return text;
    }

    public String getExplanation() {
        return explanation;
    }
}
