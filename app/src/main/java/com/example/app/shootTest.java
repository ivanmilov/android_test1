package com.example.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;


public class shootTest extends ActionBarActivity {

    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoot_test);


        GridView gridView = (GridView) findViewById(R.id.tablePhoto);

        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.shootLayout, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shoot_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_shoot_test, container, false);
            return rootView;
        }
    }

    public void openCamera(View viev)
    {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    public void addPhoto(Bitmap bmp)
    {
        GridView gv = (GridView) findViewById(R.id.tablePhoto);

        ImageView iv = new ImageView(this);
        iv.setImageBitmap(bmp);
        iv.setLayoutParams(new GridView.LayoutParams(gv.getWidth()/3, gv.getWidth()/3));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setPadding(8, 8, 8, 8);

        adapter.add(iv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this, "onActivityResult", 50).show();

        if (RESULT_OK == resultCode)
        {
            // Get Extra from the intent
            Bundle extras = data.getExtras();
            // Get the returned image from extra
            Bitmap bmp;
            try
            {
                bmp = (Bitmap) extras.get("data");
            }
            catch (Exception e)
            {
                Toast.makeText(this, "fail 1", 50).show();
                return;
            }

            addPhoto(bmp);
        }
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(String id)
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
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "TEST_IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

    public void save()
    {
        try
        {
            FileOutputStream out;
            Vector<ImageView> v = adapter.getAll();
            String id = ((EditText)findViewById(R.id.editNum)).getText().toString();

            for(ImageView iv : v)
            {
                // need to save bitmap after getting it!!!!!!!!!!!
                // and save address in adapter
                //iv.
            }

            out = new FileOutputStream(getOutputMediaFile(id));
            //bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
