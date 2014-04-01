package com.example.app;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 4/1/14.
 */
public class ViolationFragment extends Fragment
{
    private static final String TAG = "MyActivity";
    private TextView tv;
    private String s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_violation, container, false);

        Bundle bundle=getArguments();

        try
        {
            s = bundle.getString("key", "000000");
            tv = (TextView) getActivity().findViewById(R.id.violationDate);
            tv.setText(s);
        }catch (Exception e)
        {
            e.fillInStackTrace();
            Log.e(TAG, "onCreateView " + e.getMessage());
        }

        return v;
    }

    public static ViolationFragment newInstance(String text)
    {

        ViolationFragment f = new ViolationFragment();
        Bundle b = new Bundle();
        b.putString("key", text);

        f.setArguments(b);

        return f;
    }
}
