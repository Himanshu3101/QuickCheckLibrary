package com.quickcheck.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quickcheck.R;
import com.quickcheck.model.sponserbank;
import com.quickcheck.model.sponserbank;

import java.util.ArrayList;

public class Sponserbankdialogadapter extends BaseAdapter {
    Context context;
    ArrayList<sponserbank>spbanklist;
   // LayoutInflater inflater;
    public Sponserbankdialogadapter(Context context, ArrayList<sponserbank> spbanklist){
        this.context=context;
        this.spbanklist=spbanklist;
      //  inflater = ((Activity) context).getLayoutInflater();
    }
    @Override
    public int getCount() {
        return spbanklist.size();
    }

    @Override
    public Object getItem(int i) {
        return spbanklist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return spbanklist.indexOf(getItem(i));
    }


    private class ViewHolder {
        TextView txtbankname;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.sponserbank_adapter, null);
            holder = new ViewHolder();
            holder.txtbankname = (TextView) view.findViewById(R.id.tv_sponserbankname);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        sponserbank rowItem = (sponserbank) getItem(i);

        holder.txtbankname.setText(rowItem.getSpname());

        return view;
    }
}
