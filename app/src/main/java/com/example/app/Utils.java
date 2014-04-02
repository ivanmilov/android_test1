package com.example.app;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 4/2/14.
 */
public class Utils
{
    private static final String TAG = "Utils";

    public enum FileType {REPORT, IMG}

    /** Create a Dir with name ID */
    public static String getOutputPathForId(String id)
    {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(TAG, "failed to create directory " + mediaStorageDir.getPath());
                return null;
            }
        }

        return mediaStorageDir.getPath() + File.separator + id;
    }

    /** Create report file with current time name in ID directory*/
    public static File getOutputReportFile(String id)
    {
        //create id folder
        File idDir = new File(Utils.getOutputPathForId(id));

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(idDir.getPath() + File.separator + "TEST_REPORT_" + timeStamp + ".txt");

        return mediaFile;
    }

    /** Create a File for saving an image or video */
    public static File getOutputMediaFile(String id, String name)
    {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(TAG, "failed to create directory " + mediaStorageDir.getPath());
                return null;
            }
        }

        //create id folder
        File idDir = new File(mediaStorageDir.getPath() + File.separator + id);
        if (! idDir.exists()){
            if (! idDir.mkdirs()){
                Log.d(TAG, "failed to create directory " + idDir.getPath());
                return null;
            }
        }

        // Create a media file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(idDir.getPath() + File.separator + "TEST_IMG_" + name + ".jpg");

        return mediaFile;
    }

    /** retrieve array of files of typy TYPE from dir ID*/
    public static File[] getFiles(String id, final FileType type)
    {
        File dir = new File(getOutputPathForId(id));
        File[] files = dir.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                String ending;
                switch (type)
                {
                    case REPORT: ending = ".txt"; break;
                    case IMG: ending = ".jpg"; break;
                    default: return false;
                }
                return (pathname.getName().endsWith(ending));
            }
        });
        return files;
    }

    private static String convertStreamToString(InputStream is) throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    /** read file to String*/
    public static String readToString (String filePath) throws Exception
    {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }
}
