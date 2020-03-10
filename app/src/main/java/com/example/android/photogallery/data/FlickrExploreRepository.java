package com.example.android.photogallery.data;

import android.util.Log;

import com.example.android.photogallery.utils.FlickrUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.MutableLiveData;

public class FlickrExploreRepository implements FlickrExploreAsyncTask.Callback {
    private static final String TAG = FlickrExploreRepository.class.getSimpleName();
    private static final long RELOAD_AFTER_MINUTES = 10;

    private MutableLiveData<List<FlickrPhoto>> mExplorePhotos;
    private MutableLiveData<Status> mLoadingStatus;

    private Date mLoadTimestamp;

    public FlickrExploreRepository() {
        mExplorePhotos = new MutableLiveData<>();
        mExplorePhotos.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);

        mLoadTimestamp = new Date();
    }

    public MutableLiveData<List<FlickrPhoto>> getExplorePhotos() {
        return mExplorePhotos;
    }

    public MutableLiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadExplorePhotos() {
        if (shouldLoadPhotos()) {
            mLoadTimestamp = new Date();
            mExplorePhotos.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            String url = FlickrUtils.buildFlickrExploreURL();
            Log.d(TAG, "fetching explore photos with url: " + url);
            new FlickrExploreAsyncTask(url, this).execute();
        } else {
            Log.d(TAG, "using cached explore photos");
        }
    }

    private boolean shouldLoadPhotos() {
        Date currentTime = new Date();
        long millisSinceLoad = currentTime.getTime() - mLoadTimestamp.getTime();
        return mExplorePhotos.getValue() == null ||
                TimeUnit.MILLISECONDS.toMinutes(millisSinceLoad) > RELOAD_AFTER_MINUTES;
    }

    @Override
    public void onLoadFinished(List<FlickrPhoto> photos) {
        mExplorePhotos.setValue(photos);
        if (photos != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}
