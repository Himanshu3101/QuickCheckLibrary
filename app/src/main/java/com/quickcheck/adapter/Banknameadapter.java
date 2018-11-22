package com.quickcheck.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quickcheck.model.bank;
import com.quickcheck.R;
import com.quickcheck.model.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Banknameadapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<bank> banklist = null;
    private ArrayList<bank> arraylist;

    public Banknameadapter(Context context, ArrayList<bank> banklist) {

        mContext = context;
        this.banklist = banklist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<bank>();
        this.arraylist.addAll(banklist);
    }

    public class ViewHolder {
        TextView bankname;
    }

    @Override
    public int getCount() {
        return banklist.size();
    }

    @Override
    public bank getItem(int position) {
        return banklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.bankname_adapter, null);
            // Locate the TextViews in listview_item.xml
            holder.bankname = (TextView) view.findViewById(R.id.tv_banknameadapter);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            holder.bankname.setText(banklist.get(position).getNbank_name());

            String bankName = banklist.get(position).getNbank_name().toLowerCase(Locale.getDefault());

            if (bankName.contains(searchString)) {
                Log.e("test", bankName + " contains: " + searchString);
                int startPos = bankName.indexOf(searchString);
                int endPos = startPos + searchString.length();

                Spannable spanText = Spannable.Factory.getInstance().newSpannable(holder.bankname.getText()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                holder.bankname.setText(spanText, TextView.BufferType.SPANNABLE);
            }
            notifyDataSetChanged();
        }
        return view;
    }

    String searchString = "";

    public void filter(String charText) {
       searchString = charText;

        charText = charText.toLowerCase(Locale.getDefault());
        banklist.clear();
        if (charText.length() == 0) {
            banklist.addAll(arraylist);
        } else {
            for (bank wp : arraylist) {
                if (wp.getNbank_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    banklist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
