package com.ois.onistech.gmb.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

public class Utility {

    public static void hideProJava(View v, Activity c)
    {
        v.setVisibility(View.GONE);
        c.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void showProJava(View v, Activity c)
    {
        v.setVisibility(View.VISIBLE);
        c.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
