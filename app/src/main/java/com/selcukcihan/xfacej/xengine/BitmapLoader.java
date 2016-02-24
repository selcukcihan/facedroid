
package com.selcukcihan.xfacej.xengine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

public class BitmapLoader {
    public static Bitmap loadBitmap(String file) throws IOException {
        return BitmapFactory.decodeFile(file);
    }
}
