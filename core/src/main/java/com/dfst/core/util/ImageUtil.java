package com.dfst.core.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yanfei on 2016-11-22.
 */
public class ImageUtil {
    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    public static Bitmap compressImage(Context context, Uri uri, int width, int height) {
        Bitmap bitmap = null;
        try {
            InputStream input = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
            onlyBoundsOptions.inJustDecodeBounds = true;
            onlyBoundsOptions.inDither = true;//optional
            onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
            input.close();
            int originalWidth = onlyBoundsOptions.outWidth;
            int originalHeight = onlyBoundsOptions.outHeight;
            if ((originalWidth == -1) || (originalHeight == -1))
                return null;

            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int wScale = originalWidth / width;
            int hScale = originalHeight / height;

            if (wScale <= 0)
                wScale = 1;
            if (wScale <=0)
                hScale = 1;
            //比例压缩
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = wScale > hScale ? wScale : hScale;//设置缩放比例
            bitmapOptions.inDither = true;//optional
            bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
            input = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
            input.close();

            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bitmap;
        }
        //return compressImage(context, bitmap, 100);//再进行质量压缩
    }

    public static Bitmap compressImage(String imagepath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        BitmapFactory.decodeFile(imagepath, onlyBoundsOptions);
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;

        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int wScale = originalWidth / width;
        int hScale = originalHeight / height;

        if (wScale <= 0)
            wScale = 1;
        if (wScale <=0)
            hScale = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = wScale > hScale ? wScale : hScale;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        bitmap = BitmapFactory.decodeFile(imagepath, bitmapOptions);
        return bitmap;
        //return compressImage(context, bitmap, 100);//再进行质量压缩
    }

    /**
     * 压缩bitmap
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap, int width, int height) {
        // 获取这个图片的宽和高
        float originWidth = bitmap.getWidth();
        float originHeight = bitmap.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = width / originWidth;
        float scaleHeight = height / originHeight;
        float scale = scaleWidth > scaleHeight ? scaleHeight : scaleWidth;
        // 缩放图片动作
        matrix.postScale(scale, scale);
        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, (int) originWidth,
                (int) originHeight, matrix, true);
        return result;
    }


    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image, int maxLength) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = maxLength;
        while (baos.toByteArray().length / 1024 > maxLength) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            Log.i("test", baos.toByteArray().length / 1024 + "");
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
