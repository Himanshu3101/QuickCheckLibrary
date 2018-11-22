package com.quickcheck.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quickcheck.R;
import com.quickcheck.model.Utility;
import com.quickcheck.model.sponserbank;

import java.util.ArrayList;

public class Utilitydialogadapter extends BaseAdapter {

    Context context;
    ArrayList<Utility> utilitylist;

    // LayoutInflater inflater;
    public Utilitydialogadapter(Context context, ArrayList<Utility> utilitylist){
        this.context=context;
        this.utilitylist=utilitylist;
        //  inflater = ((Activity) context).getLayoutInflater();
    }
    @Override
    public int getCount() {
        return utilitylist.size();
    }

    @Override
    public Object getItem(int i) {
        return utilitylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return utilitylist.indexOf(getItem(i));
    }

    private class ViewHolder {
        TextView txtutilityname;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.utility_adapter, null);
            holder = new ViewHolder();
            holder.txtutilityname = (TextView) view.findViewById(R.id.tv_utilityname);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Utility rowItem = (Utility) getItem(i);

        holder.txtutilityname.setText(rowItem.getUtname());

        return view;
    }
}

