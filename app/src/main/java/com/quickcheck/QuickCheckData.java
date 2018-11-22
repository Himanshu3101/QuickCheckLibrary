package com.quickcheck;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quickcheck.model.Utility;
import com.quickcheck.model.bank;
import com.quickcheck.model.login.EntityDebitType;
import com.quickcheck.model.login.EntityFrequency;
import com.quickcheck.model.login.EntityPeriondEnableOn;
import com.quickcheck.model.login.EntityTodebit;
import com.quickcheck.model.sponserbank;
import com.quickcheck.preference.preferance;
import com.quickcheck.webservice.Serverfuction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class QuickCheckData {

    public static String jsonMandateData( String SponsorBank, String DebitType, String Frequency, String ToDebit,
                                  String BankAccountNumber, String BankName, String IFSC, String Amount, String UtilityCode,
                                  String MICR, String ReferenceNumber, String PhoneNumber, String EmailID, String FromDate,
                                  String ToDate, String AccountHolder, String DateOfMandate, String MandateMode) {

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("SponsorBank", SponsorBank);
            jsonObj.put("DebitType", DebitType);
            jsonObj.put("Frequency", Frequency);
            jsonObj.put("ToDebit", ToDebit);
            jsonObj.put("BankAccountNumber", BankAccountNumber);
            jsonObj.put("BankName", BankName);
            jsonObj.put("IFSC", IFSC);
            jsonObj.put("Amount", Amount);
            jsonObj.put("UtilityCode", UtilityCode);
            jsonObj.put("MICR", MICR);
            jsonObj.put("ReferenceNumber", ReferenceNumber);
            jsonObj.put("PhoneNumber", PhoneNumber);
            jsonObj.put("EmailID", EmailID);
            jsonObj.put("FromDate", FromDate);
            jsonObj.put("ToDate", ToDate);
            jsonObj.put("AccountHolder", AccountHolder);
            jsonObj.put("DateOfMandate", DateOfMandate);
            jsonObj.put("MandateMode", MandateMode);
            return jsonObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


}
