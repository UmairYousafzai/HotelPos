package com.softvalley.hotelpos.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageUtil {

    private static ImageUtil imageUtil=null;

    public static synchronized ImageUtil getInstance()
    {
        if (imageUtil==null)
        {
            imageUtil= new ImageUtil();
        }

        return imageUtil;
    }


    public String getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP);
    }

    public Bitmap getBitmapFromBase64String(String imageString)
    {
        // decode base64 string
        byte[] bytes=Base64.decode(imageString,Base64.DEFAULT);
        // Initialize bitmap
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap;
    }

}
