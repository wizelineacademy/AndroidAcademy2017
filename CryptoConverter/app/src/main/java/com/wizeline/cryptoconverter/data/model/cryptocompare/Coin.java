package com.wizeline.cryptoconverter.data.model.cryptocompare;

import com.squareup.moshi.Json;

/**
 * Created by Miguel Villaseñor on 7/20/17.
 */
public class Coin {

    @Json(name = "Id")
    private String id;
    @Json(name = "Url")
    private String url;
    @Json(name = "ImageUrl")
    private String imageUrl;
    @Json(name = "Name")
    private String name;
    @Json(name = "CoinName")
    private String coinName;
    @Json(name = "SortOrder")
    private Integer sortOrder;

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getCoinName() {
        return coinName;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }
}
