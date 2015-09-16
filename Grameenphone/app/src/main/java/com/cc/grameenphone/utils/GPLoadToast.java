package com.cc.grameenphone.utils;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.cc.grameenphone.views.GPLoadToastView;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;


/**
 * Created by aditlal on 16/09/15.
 */
public class GPLoadToast {


    private String mText = "";
    private GPLoadToastView mView;
    private int mTranslationY = 0;
    private ViewGroup mParentView;
    private boolean mShowCalled = false;
    private boolean mInflated = false;
    private Position mPosition = Position.TOP;
    private int mWindowHeight = 0;
    private boolean mToastCanceled = false;


    public GPLoadToast(Context context, Position position) {
        mPosition = position;
        mView = new GPLoadToastView(context);
        mParentView = (ViewGroup) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        mParentView.addView(mView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewHelper.setAlpha(mView, 0);
        mParentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewHelper.setTranslationX(mView, (mParentView.getWidth() - mView.getWidth()) / 2);
                ViewHelper.setTranslationY(mView, -mView.getHeight() + mTranslationY);
                mInflated = true;
                if (!mToastCanceled && mShowCalled) show();
            }
        }, 1);
        mParentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                checkZPosition();
            }
        });
    }


    public GPLoadToast(Context context) {
        this(context, Position.BOTTOM);
    }


    public GPLoadToast setTranslationY(int pixels) {
        mTranslationY = pixels;
        return this;
    }


    public GPLoadToast setText(String message) {
        mText = message;
        mView.setText(mText);
        return this;
    }


    public GPLoadToast setTextColor(int color) {
        mView.setTextColor(color);
        return this;
    }


    public GPLoadToast setBackgroundColor(int color) {
        mView.setBackgroundColor(color);
        return this;
    }


    public GPLoadToast setProgressColor(int color) {
        mView.setProgressColor(color);
        return this;
    }


    public GPLoadToast show() {
        if (!mInflated) {
            mShowCalled = true;
            return this;
        }
        mView.show();
        ViewHelper.setTranslationX(mView, (mParentView.getWidth() - mView.getWidth()) / 2);
        ViewHelper.setAlpha(mView, 0f);
        ViewHelper.setTranslationY(mView, -mView.getHeight() + mTranslationY);
        //mView.setVisibility(View.VISIBLE);
        ViewPropertyAnimator.animate(mView).alpha(1f).translationY(25 + mTranslationY)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(300).setStartDelay(0).start();
        return this;
    }


    public void success(){
        if(!mInflated){
            mToastCanceled = true;
            return;
        }
        mView.success();
        slideUp();
    }


    public void error(){
        if (!mInflated){
            mToastCanceled = true;
            return;
        }
        mView.error();
        slideUp();
    }

    private void hide() {
        ViewPropertyAnimator.animate(mView).setStartDelay(1000).alpha(0f)
                .translationY(mPosition == Position.TOP ?
                        -mView.getHeight() + mTranslationY : mWindowHeight + mTranslationY)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(300).start();
    }


    public enum Position {
        TOP, BOTTOM
    }


    private void checkZPosition() {
        int pos = mParentView.indexOfChild(mView);
        int count = mParentView.getChildCount();
        if (pos != count - 1) {
            mParentView.removeView(mView);
            mParentView.requestLayout();
            mParentView.addView(mView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }


    private void slideUp() {
        ViewPropertyAnimator.animate(mView).setStartDelay(1000).alpha(0f)
                .translationY(-mView.getHeight() + mTranslationY)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(300).start();
    }

    public GPLoadToast fromBottom() {
        mPosition = Position.BOTTOM;
        return this;
    }

    public GPLoadToast fromTop() {
        mPosition = Position.TOP;
        return this;
    }
}
