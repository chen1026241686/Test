package com.example.customview;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author ChenYasheng
 * @date 2020/3/5
 * @Description
 */
public class Utils10 {


    public static void aa()
    {
        List<String> list=new ArrayList<>(100);

        System.out.println("list.size----->"+list.size());

        Map map=new HashMap();
        map.put("asd","123");

        Hashtable table=new Hashtable();
    }

    /**
     * 根据URI获取Bitmap
     *
     * @param context
     * @param uri
     * @return
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FileOutputStream getFileOutputStream(Context context, Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            FileOutputStream fileOutputStream = new FileOutputStream(fileDescriptor);
            parcelFileDescriptor.close();
            return fileOutputStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据图片路径返回URI
     *
     * @param context
     * @param path
     * @return
     */
    public static Uri getImageContentUri(Context context, String path) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ", new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            // 如果图片不在手机的共享图片数据库，就先把它插入。
            if (new File(path).exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, path);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 判断某个文件是否存在
     *
     * @param context
     * @param path
     * @return
     */
    public static boolean isAndroidQFileExists(Context context, String path) {
        AssetFileDescriptor afd = null;
        ContentResolver cr = context.getContentResolver();
        try {
            Uri uri = Uri.parse(path);
            afd = cr.openAssetFileDescriptor(uri, "r");
            if (afd == null) {
                return false;
            } else {
                close(afd);
            }
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            close(afd);
        }
        return true;
    }

    private static void close(AssetFileDescriptor afd) {
        if (afd != null) {
            try {
                afd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 下载文件并安装？？？
     * 注意MediaStore.Downloads.EXTERNAL_CONTENT_URI在安卓10之后才有
     *
     * @param context
     * @param sourcePath
     * @param fileName
     * @param saveDirName
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void copyToDownloadAndroidQ(Context context, String sourcePath, String fileName, String saveDirName) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, "application/vnd.android.package-archive");
        values.put(MediaStore.Downloads.RELATIVE_PATH, "Download/" + saveDirName.replaceAll("/", "") + "/");

        Uri external = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = context.getContentResolver();

        Uri insertUri = resolver.insert(external, values);
        if (insertUri == null) {
            return;
        }

        String mFilePath = insertUri.toString();

        InputStream is = null;
        OutputStream os = null;
        try {
            os = resolver.openOutputStream(insertUri);
            if (os == null) {
                return;
            }
            int read;
            File sourceFile = new File(sourcePath);
            if (sourceFile.exists()) { // 文件存在时
                is = new FileInputStream(sourceFile); // 读入原文件
                byte[] buffer = new byte[1444];
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(is);
            close(os);
        }

    }

    private static void close(Closeable os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过MediaStore保存，兼容AndroidQ，保存成功自动添加到相册数据库，无需再发送广播告诉系统插入相册
     *
     * @param context      context
     * @param sourceFile   源文件
     * @param saveFileName 保存的文件名
     * @param saveDirName  picture子目录
     * @param extension    扩展名
     * @return 成功或者失败
     */
    public static boolean saveImageWithAndroidQ(Context context, File sourceFile, String saveFileName, String saveDirName, String extension) {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DESCRIPTION, "This is an image");
        values.put(MediaStore.Images.Media.DISPLAY_NAME, saveFileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.TITLE, "Image.png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + saveDirName);

        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = context.getContentResolver();

        Uri insertUri = resolver.insert(external, values);
        BufferedInputStream inputStream = null;
        OutputStream os = null;
        boolean result = false;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
            if (insertUri != null) {
                os = resolver.openOutputStream(insertUri);
            }
            if (os != null) {
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.flush();
            }
            result = true;
        } catch (IOException e) {
            result = false;
        } finally {
            close(os);
            close(inputStream);
        }
        return result;
    }


    public static void copy(File src, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        FileInputStream istream = new FileInputStream(src);
        try {
            FileOutputStream ostream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
            try {
                copy(istream, ostream);
            } finally {
                ostream.close();
            }
        } finally {
            istream.close();
        }
    }

    public static void copy(ParcelFileDescriptor parcelFileDescriptor, File dst) throws IOException {
        FileInputStream istream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
        try {
            FileOutputStream ostream = new FileOutputStream(dst);
            try {
                copy(istream, ostream);
            } finally {
                ostream.close();
            }
        } finally {
            istream.close();
        }
    }


    public static void copy(InputStream ist, OutputStream ost) throws IOException {
        byte[] buffer = new byte[4096];
        int byteCount = 0;
        while ((byteCount = ist.read(buffer)) != -1) {  // 循环从输入流读取 buffer字节
            ost.write(buffer, 0, byteCount);        // 将读取的输入流写入到输出流
        }
    }


}
