
package com.feresr.weather.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Hourly extends DisplayWeatherInfo implements Serializable {

    private String summary;
    private String icon;
    @SerializedName("data")
    private List<Hour> hours = new ArrayList<Hour>();

    /**
     *
     * @return
     *     The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     *
     * @param summary
     *     The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     *
     * @return
     *     The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     *
     * @param icon
     *     The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     *
     * @return
     *     The data
     */
    public List<Hour> getData() {
        return hours;
    }

    /**
     *
     * @param data
     *     The data
     */
    public void setData(List<Hour> data) {
        this.hours = data;
    }

}
