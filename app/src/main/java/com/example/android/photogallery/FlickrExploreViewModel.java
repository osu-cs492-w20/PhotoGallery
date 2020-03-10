package com.example.android.photogallery;

import com.example.android.photogallery.data.FlickrExploreRepository;
import com.example.android.photogallery.data.FlickrPhoto;
import com.example.android.photogallery.data.Status;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class FlickrExploreViewModel extends ViewModel {
    private LiveData<List<FlickrPhoto>> mExplorePhotos;
    private LiveData<Status> mLoadingStatus;
    private FlickrExploreRepository mRepository;

    public FlickrExploreViewModel() {
        mRepository = new FlickrExploreRepository();
        mExplorePhotos = mRepository.getExplorePhotos();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public LiveData<List<FlickrPhoto>> getExplorePhotos() {
        return mExplorePhotos;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadExplorePhotos() {
        mRepository.loadExplorePhotos();
    }
}
