package com.example.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.Collections;
import java.util.Vector;

public class ImageAdapter extends BaseAdapter
{
    private ImageView[] views;
    private int count = 0;
    private int capacity = 3;

    public ImageAdapter(Context c)
    {
        views = new ImageView[capacity];
    }

    public int getCount() {
        return count;
    }

    public Object getItem(int position)
    {
        if(position < count)
            return views[position];
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(position < count)
        {
            convertView = views[position];
        }
        return convertView;
    }

    public void add(ImageView view)
    {
        if(count >= capacity)
        {
            ImageView[] tmp = new ImageView[capacity*2];
            System.arraycopy( views, 0, tmp, 0, capacity );
            views = tmp;
            capacity *= 2;
        }

        views[count] = view;
        ++count;
        notifyDataSetChanged();
    }

    public Vector<ImageView> getAll()
    {
        Vector<ImageView> v = new Vector<ImageView>();
        Collections.addAll(v, views);
        return v;
    }
}