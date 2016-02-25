
package com.selcukcihan.xfacej.xengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

public class BitmapLoader {
    public static Bitmap loadBitmap(Context context, String name) throws IOException {
        name = name.toLowerCase();
        if (name.endsWith(".bmp")) {
            name = name.substring(0, name.length() - 4);
        }
        return BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier(name.toLowerCase(), "drawable", context.getPackageName()));
    }
}
