/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util.io;

import android.graphics.Bitmap;

public class BitmapUtil {

    public static Bitmap empty(){
        return Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
        );
    }



}
