package com.cc.grameenphone.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;

import com.cc.grameenphone.R;

import java.lang.reflect.Field;

/**
 * Created by rajkiran on 14/09/15.
 */
public class MySearchView {
    public static SearchView getSearchView(Context context, String strHint) {
        SearchView searchView = new SearchView(context);
        searchView.setQueryHint(strHint);
        searchView.setFocusable(true);
        searchView.setFocusableInTouchMode(true);
        searchView.requestFocus();
        searchView.requestFocusFromTouch();
       /* int closeButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView searchBtnClose = (ImageView) searchView.findViewById(closeButtonId);
        searchBtnClose.setImageResource(R.drawable.ic_search_white);
*/
        SearchView.SearchAutoComplete searchText = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        Drawable leftDrawable = context.getResources().getDrawable(R.drawable.ic_search);
        searchText.setCompoundDrawables(leftDrawable, null, null, null);
        searchText.setThreshold(1);
        searchText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchText.setTextColor(context.getResources().getColor(R.color.white));
        searchText.setHintTextColor(context.getResources().getColor(R.color.white));
        // tintWidget(context, searchText, R.color.white);
        try {
            Field searchField = SearchView.class.getDeclaredField("mCloseButton");
            searchField.setAccessible(true);
            ImageView closeBtn = (ImageView) searchField.get(searchView);
            closeBtn.setImageResource(R.drawable.ic_men_close_white);

           /* Field searchButtonField = SearchView.class.getDeclaredField("mSearchHintIcon");
            searchButtonField.setAccessible(true);
            TintImageView searchButton = (TintImageView) searchButtonField.get(searchView);
            searchButton.setImageResource(R.drawable.ic_search);*/
            /*ImageView searchButton = (ImageView) searchView.findViewById(R.id.search_button);
            searchButton.setImageResource(R.drawable.ic_search);*/

        } catch (NoSuchFieldException e) {
            Log.e("SearchView", e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e("SearchView", e.getMessage(), e);
        }

        return searchView;
    }



    /*private static void setFont(Context _context, SearchAutoComplete searchText) {
        Typeface tf = null;
        Typeface currentType = searchText.getTypeface();
        if (currentType == null) {
            tf = JumboApplication.fontNormal;
        }
        else {
            int currentStyle = currentType.getStyle();
            if (currentStyle == Typeface.BOLD) {
                tf = MyApplication.fontBold;
            }
            else if (currentStyle == Typeface.BOLD_ITALIC) {
                tf = MyApplication.fontBoldItalic;
            }
            else if (currentStyle == Typeface.ITALIC) {
                tf = MyApplication.fontItalic;
            }
            else {
                tf = MyApplication.fontNormal;
            }
        }
        searchText.setTypeface(tf);
    }*/

    public static SearchView getSearchView(Context context, int strHintRes) {
        return getSearchView(context, context.getString(strHintRes));
    }

    public static void tintWidget(Context context, View view, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(view.getBackground());
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
        view.setBackgroundDrawable(wrappedDrawable);
    }
}
