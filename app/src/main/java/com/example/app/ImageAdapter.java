package com.example.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Vector;

public class ImageAdapter extends BaseAdapter
{
    private Vector<Pair<ImageView, String>> views;
    private Context context_;

    public ImageAdapter(Context c)
    {
        context_ = c;
        views = new Vector<Pair<ImageView, String>>();
    }

    public int getCount()
    {
        return views.size();
    }

    public Object getItem(int position)
    {
        if(position < views.size())
            return views.elementAt(position).first;
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(position < views.size())
        {
            convertView = views.elementAt(position).first;
        }
        return convertView;
    }

   /* public void add(ImageView view, String name)
    {
        views.add(Pair.create(view, name));

        notifyDataSetChanged();
    }*/

    public void add(Bitmap bmp, GridView gv, String name)
    {
        ImageView iv = new ImageView(context_);
        iv.setImageBitmap(bmp);
        iv.setLayoutParams(new GridView.LayoutParams(gv.getWidth()/3, gv.getWidth()/3));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setPadding(8, 8, 8, 8);

        views.add(Pair.create(iv, name));

        notifyDataSetChanged();
    }

    public Vector<Pair<ImageView, String>> getAll()
    {
        return views;
    }

    public Vector<String> getNames()
    {
        Vector<String> v = new Vector<String>();
        for(Pair<ImageView, String> p : views)
        {
            v.add(p.second);
        }
        return v;
    }

    public void clear()
    {
        views.clear();
        notifyDataSetChanged();
    }
}