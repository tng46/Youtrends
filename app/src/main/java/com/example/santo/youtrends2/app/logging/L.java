package com.example.santo.youtrends2.app.logging;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Santo on 28/04/2015.
 */
public class L {
    public static void m(String message) {

    }

    public static void t(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
