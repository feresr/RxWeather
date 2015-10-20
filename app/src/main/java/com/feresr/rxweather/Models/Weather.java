package com.feresr.rxweather.models;

import android.content.Context;

import com.feresr.rxweather.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Weather {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private String icon;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The main
     */
    public String getMain() {
        return main;
    }

    /**
     * @param main The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The icon
     */
    public String getIcon(Context context) {
        switch (id) {
            case 200:
            case 201:
            case 202:
            case 230:
            case 231:
            case 232:
                return context.getString(R.string.thunderstorms); //thunderstorms
            case 210:
            case 211:
            case 212:
            case 221:
                return context.getString(R.string.lighting); //lighting
            case 300:
            case 301:
            case 321:
            case 500:
                return context.getString(R.string.sprinkle); //sprinkle
            case 302:
            case 310:
            case 511:
            case 611:
            case 612:
            case 615:
            case 616:
            case 620:
                return context.getString(R.string.rain_mix); //rain-mix
            case 311:
            case 312:
            case 314:
            case 501:
            case 502:
            case 503:
            case 504:
                return context.getString(R.string.rain); //rain
            case 313:
            case 521:
            case 522:
            case 701:
                return context.getString(R.string.showers); //showers
            case 531:
            case 961:
            case 960:
            case 959:
            case 958:
                return context.getString(R.string.storm_showers); //stormshowers
            case 600:
            case 601:
            case 621:
            case 622:
                return context.getString(R.string.snow); //snow
            case 602:
                return context.getString(R.string.sleet); //sleet
            case 711:
                return context.getString(R.string.smoke); //smoke
            case 721:
                return context.getString(R.string.day_haze); //day-haze
            case 731:
            case 761:
            case 762:
                return context.getString(R.string.dust); //dust;
            case 741:
                return context.getString(R.string.fog); //fog
            case 771:
                return context.getString(R.string.cloudy_goust); //cloudy goust;
            case 781:
            case 900:
                return context.getString(R.string.tornado); //tornado;
            case 800:
                return context.getString(R.string.day_sunny);
            case 801:
            case 802:
            case 803:
                return context.getString(R.string.day_cloudy_gusts);
            case 804:
                return context.getString(R.string.day_sunny_overcast);
            case 901:
            case 962:
                return context.getString(R.string.hurricane);
            case 903:
                return context.getString(R.string.snowflake_cold);
            case 904:
                return context.getString(R.string.hot);
            case 906:
                return context.getString(R.string.day_hail);
            case 957:
            case 905:
                return context.getString(R.string.strong_wind);
            default:
                return context.getString(R.string.default_icon);

        }
    }

    /**
     * @param icon The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

}
