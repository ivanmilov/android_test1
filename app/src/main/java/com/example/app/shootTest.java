package com.example.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;


public class shootTest extends ActionBarActivity {

    ImageAdapter adapter;
    private static final String TAG = "MyActivity";
    private TextView tvAddr;
    private TextView tvId;
    private TextView tvCount;
    private Button btShowViolation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoot_test);

        tvAddr = (TextView)findViewById(R.id.editAddr);
        tvId = (TextView)findViewById(R.id.editNum);
        tvCount = (TextView)findViewById(R.id.textViolations);
        btShowViolation = (Button)findViewById(R.id.btnShowViolations);

        GridView gridView = (GridView) findViewById(R.id.tablePhoto);

        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                {
                    Toast.makeText(shootTest.this, "" + position, Toast.LENGTH_SHORT).show();
                }
            });

        if (savedInstanceState == null)
        {
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

       /* ImageView iv = new ImageView(this);
        iv.setImageBitmap(bmp);
        iv.setLayoutParams(new GridView.LayoutParams(gv.getWidth()/3, gv.getWidth()/3));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setPadding(8, 8, 8, 8);*/

        adapter.add(bmp, gv, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
        //adapter.add(iv, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this, "onActivityResult", 50).show();
        Log.v(TAG, "onActivityResult");
        if (RESULT_OK == resultCode)
        {
            // Get Extra from the intent
            Bundle extras = data.getExtras();
            // Get the returned image from extra
            Bitmap bmp;
            try
            {
                bmp = (Bitmap) extras.get("data");
                Log.v(TAG, "onActivityResult get bmp");
            }
            catch (Exception e)
            {
                Toast.makeText(this, "fail 1", 50).show();
                Log.v(TAG, "onActivityResult fail");
                return;
            }

            addPhoto(bmp);
        }
    }

    public void onIdClick(View view)
    {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();
            final View v = inflater.inflate(R.layout.alert_input_car_id, null);
            final EditText e1 = (EditText) v.findViewById(R.id._1st);
            final EditText e2 = (EditText) v.findViewById(R.id._2nd);
            final EditText e3 = (EditText) v.findViewById(R.id._3d);
            final EditText e4 = (EditText) v.findViewById(R.id._4th);

            String s = tvId.getText().toString();
            if(!s.isEmpty())
            {
                e1.setText(s.substring(0,1));
                e2.setText(s.substring(1,4));
                e3.setText(s.substring(4,6));
                e4.setText(s.substring(6));
            }

            e1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() >= 1)
                        e2.requestFocus();
                }
            });
            e2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() >= 3)
                        e3.requestFocus();
                }
            });
            e3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void afterTextChanged(Editable editable) {

                    if (editable.length() >= 2)
                        e4.requestFocus();
                }
            });

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(v)  //inflater.inflate(R.layout.alert_input_car_id, null))
                    // Add action buttons
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            String s = e1.getText().toString() +
                                    e2.getText().toString() +
                                    e3.getText().toString() +
                                    e4.getText().toString();
                            ((EditText) findViewById(R.id.editNum)).setText(s);
                            refreshViolations(s);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // LoginDialogFragment.this.getDialog().cancel();
                        }
                    });

            builder.show();
        }
        catch (Exception e)
        {
            Log.d(TAG, "onIdClick" + e.getMessage());
        }
    }

    public void save(View viev)
    {
        Log.v(TAG, "onSave");
        try
        {
            Vector<Pair<ImageView, String>> v = adapter.getAll();
            String id = ((EditText)findViewById(R.id.editNum)).getText().toString();
            String addr = ((EditText)findViewById(R.id.editAddr)).getText().toString();
            String type = ((Spinner)findViewById(R.id.TYPE)).getSelectedItem().toString();
            // check id && addr
            if(id.isEmpty() || addr.isEmpty())
            {
                String reason = "";
                if(id.isEmpty())
                    reason = "номер!";
                if(addr.isEmpty())
                    reason = "адрес!";
                final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("нужно ввести " + reason);
                dlgAlert.setCancelable(true);
                dlgAlert.setPositiveButton("Ok", null);// new DialogInterface.OnClickListener() {  public void onClick(DialogInterface dialog, int which) { /*dismiss the dialog */}  });
                dlgAlert.create().show();
                return;
            }

            // check imgs
            Vector<String> vImgs = adapter.getNames();
            if(vImgs.isEmpty())
            {
                final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Не хватает фото!");
                dlgAlert.setCancelable(true);
                dlgAlert.setPositiveButton("Ok", null);// new DialogInterface.OnClickListener() {  public void onClick(DialogInterface dialog, int which) { /*dismiss the dialog */}  });
                dlgAlert.create().show();
                return;
            }

            // save imgs
            for(Pair<ImageView, String> iv : v)
            {
                FileOutputStream out;
                out = new FileOutputStream(Utils.getOutputMediaFile(id, iv.second));
                Bitmap bmp = ((BitmapDrawable)iv.first.getDrawable()).getBitmap();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.close();
            }

            // save report
            JSONObject j = new JSONObject();

            j.put(getString(R.string.report_id), id);
            j.put(getString(R.string.report_date), new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
            j.put(getString(R.string.report_type), type);
            j.put(getString(R.string.report_address), addr);

            JSONArray imgs = new JSONArray();
            for(String s : vImgs)
                imgs.put(s);

            j.put(getString(R.string.report_img), imgs);

            FileWriter file = new FileWriter(Utils.getOutputReportFile(id));
            file.write(j.toString());
            file.flush();
            file.close();
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.v(TAG, "onSave fail: "+ e.getMessage());
            return ;
        }
        clear();
    }

    private void clear()
    {
        adapter.clear();
        tvAddr.setText("");
        tvCount.setText(R.string.TextViolationsCaption);
        btShowViolation.setText(R.string.TextViolationsCaption);
    }

    private void refreshViolations(String id)
    {
        int count = getViolationsCount(id);
        findViewById(R.id.btnShowViolations).setEnabled(count > 0);

        try {
            tvCount.setText(R.string.TextViolationsCaption);
            String text = tvCount.getText().toString();
            tvCount.setText(text + count);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.v(TAG, "refreshViolations fail: "+ e.getMessage());
        }
    }

    private int getViolationsCount(String id)
    {
        File dir = new File(Utils.getOutputPathForId(id));
        File[] toBeDeleted = dir.listFiles(new FileFilter()
        {
            public boolean accept(File pathname)
            {
                return (pathname.getName().endsWith(".txt"));
            }
        });
        if(toBeDeleted != null)
            return toBeDeleted.length;
        return 0;
    }
}
