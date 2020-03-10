package com.example.android.photogallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.photogallery.data.FlickrPhoto;
import com.example.android.photogallery.data.Status;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FlickrPhotosAdapter.OnPhotoClickedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int NUM_PHOTO_COLUMNS = 2;

    private RecyclerView mPhotosRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;

    private FlickrPhotosAdapter mAdapter;
    private FlickrExploreViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);
        mPhotosRV = findViewById(R.id.rv_photos);

        mAdapter = new FlickrPhotosAdapter(this);
        mPhotosRV.setAdapter(mAdapter);

        mPhotosRV.setHasFixedSize(true);
        mPhotosRV.setLayoutManager(new LinearLayoutManager(this));

        mViewModel = new ViewModelProvider(this).get(FlickrExploreViewModel.class);

        mViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mPhotosRV.setVisibility(View.VISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mPhotosRV.setVisibility(View.VISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                }
            }
        });

        mViewModel.getExplorePhotos().observe(this, new Observer<List<FlickrPhoto>>() {
            @Override
            public void onChanged(@Nullable List<FlickrPhoto> photos) {
                mAdapter.updatePhotos(photos);
            }
        });

        mViewModel.loadExplorePhotos();
    }

    @Override
    public void onPhotoClicked(int idx) {
        Log.d(TAG, "photo clicked, index: " + idx);
    }
}
