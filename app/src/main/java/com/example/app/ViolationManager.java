package com.example.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.Vector;

/**
 * Created by Administrator on 4/2/14.
 */
public class ViolationManager
{
    private Vector<ViolationControl> vViolations;
    private String sId_;
    Context context_;

    ViolationManager(String id, Context context, ViewGroup vg)
    {
        sId_ = id;
        context_ = context;
        File[] files = Utils.getFiles(id, Utils.FileType.REPORT);
        vViolations = new Vector<ViolationControl>(files.length);

        for(File f : files)
        {
            //////
            String p = f.getPath();
            //////
            ViolationControl vc = new ViolationControl(context);
            vc.setReport(f.getPath());
            vViolations.add(vc);
            vg.addView(vc);
        }
    }



}
