package com.cc.grameenphone.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by sudendra on 18/4/15.
 */
public class KeyboardUtil {
    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            Log.e("KeyBoardUtil", "hiddent");
        } catch (Exception e) {

            // Ignore exceptions if any
            Log.e("KeyBoardUtil", e.toString(), e);
        }
    }

    public static void hideKeyboardDialogDismiss(Context x) {

        InputMethodManager imm = (InputMethodManager) x.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            Logger.e("hideKeyboardDialogDismiss", "Cant dismiss its not active");
            //to hide it, call the method again
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public static void hideKeyboard(Context c) {
        // Check if no view has focus:
        View view = ((Activity) c).getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) c.getSystemService(c.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideKeyboard(Context c, View v) {
        // Check if no view has focus:
        if (v != null) {
            InputMethodManager inputManager = (InputMethodManager) c.getSystemService(c.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            Logger.e("hideKeyboardDialogDismiss", "Cant dismiss no view has focus");
        }
    }

    public static void showKeyboard(Context c) {
        // Check if no view has focus:
        View view = ((Activity) c).getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) c.getSystemService(c.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }


}