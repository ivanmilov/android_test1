package com.example.app;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by ivan on 4/1/14.
 */
public class ViolationControl extends FrameLayout
{
    private Context context_;
    private static final String TAG = "MyActivity";
    private TextView tvDate_;

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
        }
        catch (Exception e)
        {
            e.fillInStackTrace();
            Log.e(TAG, "initControl " + e.getMessage());
        }
    }

    public void setDate(String s)
    {
        tvDate_.setText(s);
    }
}
