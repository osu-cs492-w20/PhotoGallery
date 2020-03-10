package com.example.android.photogallery;

import android.graphics.drawable.ColorDrawable;

public class SizedColorDrawable extends ColorDrawable {
    int mWidth = -1;
    int mHeight = -1;

    public SizedColorDrawable(int color, int width, int height) {
        super(color);
        mWidth = width;
        mHeight = height;
    }

    @Override
    public int getIntrinsicHeight() {
        return mHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }
}
