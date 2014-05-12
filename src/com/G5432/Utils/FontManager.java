package com.G5432.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-5-9
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
public class FontManager {

    public static final Typeface wawaw5(Context ctx) {
        Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/wawaw5.ttf");
        return typeface;
    }

    public static void changeFonts(View root, Typeface typeface) {
        if (root instanceof TextView) {
            ((TextView) root).setTypeface(typeface);
        } else if (root instanceof Button) {
            ((Button) root).setTypeface(typeface);
        } else if (root instanceof EditText) {
            ((EditText) root).setTypeface(typeface);
        } else if (root instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) root;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View v = vg.getChildAt(i);
                changeFonts(v, typeface);
            }
        }

    }
}