package com.example.android.photogallery.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.example.android.photogallery.BuildConfig;
import com.example.android.photogallery.data.FlickrPhoto;
import com.google.gson.Gson;

import java.util.List;

public class FlickrUtils {

    private static final String FLICKR_API_BASE_URL = "https://api.flickr.com/services/rest/";
    private static final String FLICKR_API_METHOD_PARAM = "method";
    private static final String FLICKR_API_EXPLORE_METHOD = "flickr.interestingness.getList";
    private static final String FLICKR_API_KEY_PARAM = "api_key";
    private static final String FLICKR_API_FORMAT_PARAM = "format";
    private static final String FLICKR_API_FORMAT_JSON = "json";
    private static final String FLICKR_API_NOJSONCALLBACK_PARAM = "nojsoncallback";
    private static final String FLICKR_API_NOJSONCALLBACK = "1";
    private static final String FLICKR_API_EXTRAS_PARAM = "extras";
    private static final String[] FLICKR_API_EXTRAS = {"url_l", "url_m", "owner_name"};

    private static final String FLICKR_API_KEY = BuildConfig.FLICKR_API_KEY;

    private static final Gson gson = new Gson();

    /*
     * The below two classes are used only for JSON parsing with Gson.
     */
    public static class FlickrExploreResults {
        FlickrPhotos photos;
        String stat;
    }

    public static class FlickrPhotos {
        List<FlickrPhoto> photo;
    }

    public static String buildFlickrExploreURL() {
        return Uri.parse(FLICKR_API_BASE_URL).buildUpon()
                .appendQueryParameter(FLICKR_API_METHOD_PARAM, FLICKR_API_EXPLORE_METHOD)
                .appendQueryParameter(FLICKR_API_KEY_PARAM, FLICKR_API_KEY)
                .appendQueryParameter(FLICKR_API_FORMAT_PARAM, FLICKR_API_FORMAT_JSON)
                .appendQueryParameter(FLICKR_API_NOJSONCALLBACK_PARAM, FLICKR_API_NOJSONCALLBACK)
                .appendQueryParameter(FLICKR_API_EXTRAS_PARAM, TextUtils.join(",", FLICKR_API_EXTRAS))
                .build()
                .toString();
    }

    public static List<FlickrPhoto> parseFlickrExploreResultsJSON(String exploreResultsJSON) {
        FlickrExploreResults exploreResults = gson.fromJson(exploreResultsJSON, FlickrExploreResults.class);
        List<FlickrPhoto> photos = null;
        if (exploreResults != null && exploreResults.photos != null) {
            photos = exploreResults.photos.photo;
        }
        return photos;
    }
}
