package com.example.app;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.Vector;

/**
 * Created by Administrator on 4/1/14.
 */
public class FragmentAdapter extends BaseAdapter
{
    //private Vector<Pair<ImageView, String>> views;
    private Vector<String> views;

    public FragmentAdapter(Context c)
    {
        views = new Vector<String>();
    }

    @Override
    public int getCount()
    {
        return views.size();
    }

    @Override
    public Object getItem(int position)
    {
        if(position < views.size())
            return ViolationFragment.newInstance(views.elementAt(position));

        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(position < views.size())
            return ViolationFragment.newInstance(views.elementAt(position)).getView();
        return null;
    }

    public void add(String name)
    {
        views.add(name);

        notifyDataSetChanged();
    }

    public Vector<String> getAll()
    {
        return views;
    }

    /*public Vector<String> getNames()
    {
        Vector<String> v = new Vector<String>();
        for(Pair<ImageView, String> p : views)
        {
            v.add(p.second);
        }
        return v;
    }*/

    public void clear()
    {
        views.clear();
        notifyDataSetChanged();
    }
}