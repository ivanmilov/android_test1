package com.example.app;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ivan on 4/1/14.
 */
public class ViolationControl extends FrameLayout
{
    private Context context_;
    private static final String TAG = "MyActivity";
    private TextView tvDate_;
    private TextView tvAddress_;
    private TextView tvType_;
    private GridView gvGrid_;

    public ViolationControl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initControl();
        context_ = context;
    }

    public ViolationControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl();
        context_ = context;
    }

    public ViolationControl(Context context) {
        super(context);
        initControl();
        context_ = context;
    }

    private void initControl()
    {
        try {
            LayoutInflater li = ((Activity) getContext()).getLayoutInflater();//((Activity)context_).getLayoutInflater();

            li.inflate(R.layout.violation_control, this);

            tvDate_ = (TextView)findViewById(R.id.violationDate);
            tvAddress_ = (TextView)findViewById(R.id.violationAddr);
            tvType_ = (TextView)findViewById(R.id.violationType);
            gvGrid_ = (GridView)findViewById(R.id.violationImgs);
        }
        catch (Exception e)
        {
            e.fillInStackTrace();
            Log.e(TAG, "initControl " + e.getMessage());
        }
    }

    /**  set path to report*/
    public void setReport(String path)
    {
        String jsonStr;
        try{
            context_ = getContext();

            jsonStr = Utils.readToString(path);
            JSONObject j = new JSONObject(jsonStr);
            String date = j.getString(context_.getString(R.string.report_date));
            String type = j.getString( context_.getString(R.string.report_type));
            String address = j.getString( context_.getString(R.string.report_address));
            String id = j.getString( context_.getString(R.string.report_id));
            JSONArray jArr = j.getJSONArray(context_.getString(R.string.report_img));


            tvDate_.setText(date);
            tvAddress_.setText(address);
            tvType_.setText(type);

        } catch (Exception e)
        {
            e.fillInStackTrace();
            Log.e(TAG, "setReport " + e.getMessage());
            return;
        }
    }

}
