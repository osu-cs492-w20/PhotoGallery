package com.example.android.photogallery.data;

import android.os.AsyncTask;

import com.example.android.photogallery.utils.FlickrUtils;
import com.example.android.photogallery.utils.NetworkUtils;

import java.io.IOException;
import java.util.List;

public class FlickrExploreAsyncTask extends AsyncTask<Void, Void, String> {
    private String mURL;
    private Callback mCallback;

    public interface Callback {
        void onLoadFinished(List<FlickrPhoto> photos);
    }

    public FlickrExploreAsyncTask(String url, Callback callback) {
        mURL = url;
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (mURL != null) {
            String results = null;
            try {
                results = NetworkUtils.doHTTPGet(mURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        List<FlickrPhoto> photos = null;
        if (s != null) {
            photos = FlickrUtils.parseFlickrExploreResultsJSON(s);
        }
        mCallback.onLoadFinished(photos);
    }
}
