package com.feresr.rxweather.models;

import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CityWeather {

    private Double latitude;
    private Double longitude;
    private String timezone;
    private Double offset;
    private Currently currently;
    private Hourly hourly;
    private Daily daily;
    private Flags flags;
    private long fetchTime;
    private List<Alert> alerts;

    public long getFetchTime() {
        return fetchTime;
    }
    public void setFetchTime(long fetchTime) {
        this.fetchTime = fetchTime;
    }

    /**
     * @return The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return The timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * @param timezone The timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * @return The offset
     */
    public Double getOffset() {
        return offset;
    }

    /**
     * @param offset The offset
     */
    public void setOffset(Double offset) {
        this.offset = offset;
    }

    /**
     * @return The currently
     */
    public Currently getCurrently() {
        return currently;
    }

    /**
     * @param currently The currently
     */
    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    /**
     * @return The hourly
     */
    public Hourly getHourly() {
        return hourly;
    }

    /**
     * @param hourly The hourly
     */
    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    /**
     * @return The daily
     */
    public Daily getDaily() {
        return daily;
    }

    /**
     * @param daily The daily
     */
    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    /**
     * @return The flags
     */
    public Flags getFlags() {
        return flags;
    }

    /**
     * @param flags The flags
     */
    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    /**
     * @return Alerts The flags
     */
    public List<Alert> getAlerts() {
        return alerts;
    }

    /**
     * @param alerts The Alerts
     */
    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

}
