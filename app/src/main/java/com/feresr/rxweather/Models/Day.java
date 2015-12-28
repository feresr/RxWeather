package com.feresr.rxweather.models;

import android.content.Context;

import com.feresr.rxweather.utils.IconManager;

import java.io.Serializable;


public class Day extends DisplayWeatherInfo implements Serializable {

    private Integer time;
    private String summary;
    private String icon;
    private Integer sunriseTime;
    private Integer sunsetTime;
    private Double moonPhase;
    private Double precipIntensity;
    private Double precipIntensityMax;
    private Double precipIntensityMaxTime;
    private Double precipProbability;
    private String precipType;
    private Double temperatureMin;
    private Integer temperatureMinTime;
    private Double temperatureMax;
    private Integer temperatureMaxTime;
    private Double apparentTemperatureMin;
    private Integer apparentTemperatureMinTime;
    private Double apparentTemperatureMax;
    private Integer apparentTemperatureMaxTime;
    private Double dewPoint;
    private Double humidity;
    private Double windSpeed;
    private Integer windBearing;
    private Double cloudCover;
    private Double pressure;
    private Double ozone;

    private boolean isToday = false;

    private boolean isTomorrow = false;

    private boolean isLastDayOfWeek = false;

    /**
     * @return The time
     */
    public Integer getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The icon
     */
    public String getIcon(Context context) {
        return IconManager.getIconResource(icon, context);
    }

    /**
     * @param icon The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return The sunriseTime
     */
    public Integer getSunriseTime() {
        return sunriseTime;
    }

    /**
     * @param sunriseTime The sunriseTime
     */
    public void setSunriseTime(Integer sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    /**
     * @return The sunsetTime
     */
    public Integer getSunsetTime() {
        return sunsetTime;
    }

    /**
     * @param sunsetTime The sunsetTime
     */
    public void setSunsetTime(Integer sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    /**
     * @return The moonPhase
     */
    public Double getMoonPhase() {
        return moonPhase;
    }

    /**
     * @param moonPhase The moonPhase
     */
    public void setMoonPhase(Double moonPhase) {
        this.moonPhase = moonPhase;
    }

    /**
     * @return The precipIntensity
     */
    public Double getPrecipIntensity() {
        return precipIntensity;
    }

    /**
     * @param precipIntensity The precipIntensity
     */
    public void setPrecipIntensity(Double precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    /**
     * @return The precipIntensityMax
     */
    public Double getPrecipIntensityMax() {
        return precipIntensityMax;
    }

    /**
     * @param precipIntensityMax The precipIntensityMax
     */
    public void setPrecipIntensityMax(Double precipIntensityMax) {
        this.precipIntensityMax = precipIntensityMax;
    }

    /**
     * @return The precipIntensityMaxTime
     */
    public Double getPrecipIntensityMaxTime() {
        return precipIntensityMaxTime;
    }

    /**
     * @param precipIntensityMaxTime The precipIntensityMaxTime
     */
    public void setPrecipIntensityMaxTime(Double precipIntensityMaxTime) {
        this.precipIntensityMaxTime = precipIntensityMaxTime;
    }

    /**
     * @return The precipProbability
     */
    public Double getPrecipProbability() {
        return precipProbability;
    }

    /**
     * @param precipProbability The precipProbability
     */
    public void setPrecipProbability(Double precipProbability) {
        this.precipProbability = precipProbability;
    }

    /**
     * @return The precipType
     */
    public String getPrecipType() {
        return precipType;
    }

    /**
     * @param precipType The precipType
     */
    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

    /**
     * @return The temperatureMin
     */
    public Double getTemperatureMin() {
        return temperatureMin;
    }

    /**
     * @param temperatureMin The temperatureMin
     */
    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    /**
     * @return The temperatureMinTime
     */
    public Integer getTemperatureMinTime() {
        return temperatureMinTime;
    }

    /**
     * @param temperatureMinTime The temperatureMinTime
     */
    public void setTemperatureMinTime(Integer temperatureMinTime) {
        this.temperatureMinTime = temperatureMinTime;
    }

    /**
     * @return The temperatureMax
     */
    public Double getTemperatureMax() {
        return temperatureMax;
    }

    /**
     * @param temperatureMax The temperatureMax
     */
    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    /**
     * @return The temperatureMaxTime
     */
    public Integer getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    /**
     * @param temperatureMaxTime The temperatureMaxTime
     */
    public void setTemperatureMaxTime(Integer temperatureMaxTime) {
        this.temperatureMaxTime = temperatureMaxTime;
    }

    /**
     * @return The apparentTemperatureMin
     */
    public Double getApparentTemperatureMin() {
        return apparentTemperatureMin;
    }

    /**
     * @param apparentTemperatureMin The apparentTemperatureMin
     */
    public void setApparentTemperatureMin(Double apparentTemperatureMin) {
        this.apparentTemperatureMin = apparentTemperatureMin;
    }

    /**
     * @return The apparentTemperatureMinTime
     */
    public Integer getApparentTemperatureMinTime() {
        return apparentTemperatureMinTime;
    }

    /**
     * @param apparentTemperatureMinTime The apparentTemperatureMinTime
     */
    public void setApparentTemperatureMinTime(Integer apparentTemperatureMinTime) {
        this.apparentTemperatureMinTime = apparentTemperatureMinTime;
    }

    /**
     * @return The apparentTemperatureMax
     */
    public Double getApparentTemperatureMax() {
        return apparentTemperatureMax;
    }

    /**
     * @param apparentTemperatureMax The apparentTemperatureMax
     */
    public void setApparentTemperatureMax(Double apparentTemperatureMax) {
        this.apparentTemperatureMax = apparentTemperatureMax;
    }

    /**
     * @return The apparentTemperatureMaxTime
     */
    public Integer getApparentTemperatureMaxTime() {
        return apparentTemperatureMaxTime;
    }

    /**
     * @param apparentTemperatureMaxTime The apparentTemperatureMaxTime
     */
    public void setApparentTemperatureMaxTime(Integer apparentTemperatureMaxTime) {
        this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
    }

    /**
     * @return The dewPoint
     */
    public Double getDewPoint() {
        return dewPoint;
    }

    /**
     * @param dewPoint The dewPoint
     */
    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }

    /**
     * @return The humidity
     */
    public Double getHumidity() {
        return humidity;
    }

    /**
     * @param humidity The humidity
     */
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    /**
     * @return The windSpeed
     */
    public Double getWindSpeed() {
        return windSpeed;
    }

    /**
     * @param windSpeed The windSpeed
     */
    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * @return The windBearing
     */
    public Integer getWindBearing() {
        return windBearing;
    }

    /**
     * @param windBearing The windBearing
     */
    public void setWindBearing(Integer windBearing) {
        this.windBearing = windBearing;
    }

    /**
     * @return The cloudCover
     */
    public Double getCloudCover() {
        return cloudCover;
    }

    /**
     * @param cloudCover The cloudCover
     */
    public void setCloudCover(Double cloudCover) {
        this.cloudCover = cloudCover;
    }

    /**
     * @return The pressure
     */
    public Double getPressure() {
        return pressure;
    }

    /**
     * @param pressure The pressure
     */
    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    /**
     * @return The ozone
     */
    public Double getOzone() {
        return ozone;
    }

    /**
     * @param ozone The ozone
     */
    public void setOzone(Double ozone) {
        this.ozone = ozone;
    }

    public int getColor(Context context) {
        return IconManager.getColorResource(icon, context);
    }

    public boolean isToday() {
        return isToday;
    }

    public void setIsToday(boolean isToday) {
        this.isToday = isToday;
    }

    public boolean isTomorrow() {
        return isTomorrow;
    }

    public void setIsTomorrow(boolean isTomorrow) {
        this.isTomorrow = isTomorrow;
    }

    public boolean isLastDayOfWeek() {
        return isLastDayOfWeek;
    }

    public void setIsLastDayOfWeek(boolean isLastDayOfWeek) {
        this.isLastDayOfWeek = isLastDayOfWeek;
    }
}
