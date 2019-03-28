package com.egov.smartqr.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lss on 2015-08-13.
 */
public class BitmapUtil {
    public static Bitmap fromPath(String path) {
        File imgFile = new File(path);
        if(imgFile.exists()) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            long option = imgFile.length() / (1024*1024);
            bmOptions.inSampleSize = (int)option + 1;
            Bitmap bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), bmOptions);
            return bmp;
        }

        return null;
    }

    public static int getImageAngle(Context context, String imagePath) {
        int rtn = -1;
        int orientation = ExifInterface.ORIENTATION_NORMAL;

        try {
            ExifInterface exif = new ExifInterface(imagePath);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        } catch (IOException e) {
        }

        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                break;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
            	rtn = 0;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rtn = 180;
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                rtn = 180;
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                rtn = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rtn = 90;
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                rtn = -90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rtn = -90;
                break;
        }

        return rtn;
    }

    public static int getOrientation(Context context, Uri photoUri) {

        String filePath = pathFromUri1(context, photoUri);
        if(filePath == null || filePath.length() <= 0) {
            filePath = pathFromUri2(context, photoUri);
        }
        if(filePath == null || filePath.length() <= 0) {
            // http://stackoverflow.com/questions/20067508/get-real-path-from-uri-android-kitkat-new-storage-access-framework
            // 위 링크 중간에 긴 소스 있음... 적용테스트
        }
        int orientation = ExifInterface.ORIENTATION_NORMAL;
        try {
            if(filePath!=null && filePath.length() > 0) {
                ExifInterface exif = new ExifInterface(filePath);
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            }
        }
        catch(Exception e) {}

        return orientation;
    }

    public static int getOrientation(Context context, String photoPath) {

        int orientation = ExifInterface.ORIENTATION_NORMAL;
        try {
            if(photoPath!=null && photoPath.length() > 0) {
                ExifInterface exif = new ExifInterface(photoPath);
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            }
        }
        catch(Exception e) {}

        return orientation;
    }

    // 킷캣 미만
    public static String pathFromUri1(Context context, Uri uri) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, new String[] { MediaStore.Images.Media.DATA }, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(column_index);
            }
        }
        catch(Exception e) {}
        finally {
            cursor.close();
        }

        return path;
    }

    // 킷캣 이상 : document개념 탑재?
    public static String pathFromUri2(Context context, Uri uri) {
        String path = null;
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[] {
                            document_id
                    }, null);
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        }
        catch(Exception e) {}
        finally {
            if(cursor != null) cursor.close();
        }

        return path;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        try{
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    return bitmap;
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    matrix.setScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }
            try {
                Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                if(bmRotated != bitmap) bitmap.recycle();
                return bmRotated;
            }
            catch (OutOfMemoryError e) {
                e.printStackTrace();
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 이미지 라운드 처리 => 라운드부분 투명
    public static Bitmap getRoundCorner(Bitmap bitmap, int pixel) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // bitmap.recycle();

        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();

        return output;
    }

    //Bitmap -> JpgFile Save
    public static void bitmap2JpgFile(Context context, Bitmap bitmap) {
        File imagesFolder = new File(DirUtil.getExStoragePublicDir(Environment.DIRECTORY_DCIM), "smartqr");
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();
        }
        String path = imagesFolder.getAbsolutePath() + "/" + DateUtil.curDate(context) + ".jpg";
        //Log.d("face_rec", "saved face is " + path);

        File file = new File(path);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(bytes.toByteArray());
            out.flush();
            out.close();

            //db등록
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(file);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap.recycle();
    }

    //Bitmap -> String
    public static String bitmap2String(Context context, Bitmap bitmap) {
        String savePath = DirUtil.getCacheDir(context) + "/img.jpg";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();

        String rtn = android.util.Base64.encodeToString(byteArray, 16);
        return rtn;
    }
}
