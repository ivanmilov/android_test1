package com.example.app;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.hardware.Camera;
import android.widget.Toast;
import android.app.FragmentManager;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MyActivity";
    public final static String EXTRA_MESSAGE = "com.example.app.MESSAGE";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    FragmentAdapter fragmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // получаем экземпляр FragmentTransaction
        fragmentManager = getFragmentManager();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
/*        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);*/
        switch (item.getItemId())
        {
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void openShootActivity(View view)
    {
        Intent intent = new Intent(this, shootTest.class);
        startActivity(intent);
    }

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
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
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "TEST_IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

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

            ImageView iv;
            iv = (ImageView) findViewById(R.id.imageView1);
            iv.setImageBitmap(bmp);
            FileOutputStream out;
            try
            {
                out = new FileOutputStream(getOutputMediaFile(MEDIA_TYPE_IMAGE));
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }


            Toast.makeText(this, "OK", 50).show();
        }
    }

    private boolean checkCameraHardware(Context context)
    {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }



    private Uri fileUri;

    public void openCamera1(View viev)
    {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        //startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        startActivityForResult(intent, 1);

        Toast.makeText(this, "started", 50).show();


/*        Camera camera;
        Camera.open();

        SurfaceView preview;
        SurfaceHolder surfaceHolder;

        surfaceHolder = preview.getHolder();*/
    }

    Vector<ViolationFragment> vfs;
    static int count = 0;
    int added = -1;
    public void addFragment(View v)
    {
        try {

            LinearLayout sv = (LinearLayout)findViewById(R.id.linearLayout);
            ViolationControl vc = new ViolationControl(this);
            vc.setDate("date " + count++);
            sv.addView(vc);


          /* // if(added < 0)
            {
                vfs = new Vector<ViolationFragment>();
                for (int i = 0; i < 5; ++i)
                    vfs.add(new ViolationFragment());

               // fragmentTransaction = fragmentManager.beginTransaction();
                for (ViolationFragment vf : vfs)
                {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.container, vf);
                    Bundle b = new Bundle();
                    b.putString("key", "aaa" + count++);
                    vf.setArguments(b);
                    fragmentTransaction.commit();
                }

                //fragmentTransaction.commit();
                added++;
            }
            /*else
                for(ViolationFragment vf : vfs)
                {
                    vf.setText("text "+ added++);
                    vf.notifyAll();
                }
*/

            /*String s = "aaa" + count++;
            // добавляем фрагмент
            ViolationFragment myFragment = new ViolationFragment();
            fragmentTransaction.add(R.id.container, myFragment);
            fragmentTransaction.commit();
            ((TextView) findViewById(R.id.violationDate)).setText(s);*/
        }
        catch (Exception e){
            e.fillInStackTrace();
            Log.d(TAG, "addFragment " + e.getMessage());
        }
    }
}
