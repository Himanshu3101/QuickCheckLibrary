package com.quickcheck.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quickcheck.CustomDialogForMessages;
import com.quickcheck.HomeActivity;
import com.quickcheck.R;
import com.quickcheck.SplashScreen;
import com.quickcheck.model.Getmandate;
import com.quickcheck.model.Refrencedata;
import com.quickcheck.preference.preferance;
import com.quickcheck.webservice.Serverfuction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;

public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceAdapter.MyViewHolder> {

    ArrayList<Refrencedata> referencelist, reftemplist;
    Activity context;
    String strProcessname;
    public static String tempsuccess = "1";

    public ReferenceAdapter(Activity context, ArrayList<Refrencedata> referencelist, ArrayList<Refrencedata> reftemplist) {

        this.context = context;
        this.referencelist = referencelist;
        this.reftemplist = reftemplist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView reference_no, bankname, tv_mandateId, ifsc, customername, accountnumber, dateofmandate, creaytedby, tv_gatemandateedit, tv_new;
        ;

        public MyViewHolder(View view) {
            super(view);
            reference_no = (TextView) itemView.findViewById(R.id.tv_refrefno);
            bankname = (TextView) itemView.findViewById(R.id.tv_refbankname);
            ifsc = (TextView) itemView.findViewById(R.id.tv_refifsc);
            customername = (TextView) itemView.findViewById(R.id.tv_refcustomername);
            dateofmandate = (TextView) itemView.findViewById(R.id.tv_refdateofmandate);
            accountnumber = (TextView) itemView.findViewById(R.id.tv_refaccountnumber);
            creaytedby = (TextView) itemView.findViewById(R.id.tv_refcreatedby);
            tv_mandateId = (TextView) itemView.findViewById(R.id.tv_mandateId);
            tv_new = itemView.findViewById(R.id.tv_refcreatenew);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.referencelist_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Refrencedata data = referencelist.get(position);
        holder.reference_no.setText(data.getReferenceno());
        holder.bankname.setText(data.getRefbankname());
        holder.ifsc.setText(data.getRefifsc());
        holder.customername.setText(data.getRefcustomername());
        holder.dateofmandate.setText(data.getRefdateofmandate());
        holder.creaytedby.setText(data.getRefcreatedby());
        holder.accountnumber.setText(data.getRefaccontno());
        holder.tv_mandateId.setText(data.getRefmandateid());



    }

    @Override
    public int getItemCount() {

        return referencelist.size();

    }




}