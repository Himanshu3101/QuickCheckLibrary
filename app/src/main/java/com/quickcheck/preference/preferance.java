package com.quickcheck.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quickcheck.model.Utility;
import com.quickcheck.model.bank;
import com.quickcheck.model.login.EntityDebitType;
import com.quickcheck.model.login.EntityFrequency;
import com.quickcheck.model.login.EntityPeriondEnableOn;
import com.quickcheck.model.login.EntityTodebit;
import com.quickcheck.model.sponserbank;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class preferance {

    private static final String SHARED_PREF_NAME = "zipnach";
    private static final String CHECKSUM = "checksum";
    private static final String USER_ID = "user_id";
    private static final String MANDATE_ID = "mandate_id";
    private static final String MANDATE_ID_TEMP = "mandate_id_temp";
    private static final String IS_MANDATE_EDIT = "is_mandate_edit";
    private static final String IS_REFERENCE_CHECK = "is_reference_check";
    private static final String IS_REFERENCE_EDIT = "is_reference_edit";
    private static final String IS_REFERENCE_NUMERIC = "is_reference_numaric";
    private static final String IS_SHOWMANDATE_MODE = "is_showmandate_mode";
    private static final String IS_LIVE_NACH = "is_live_nach";
    private static final String IS_DEBIT_TYPE = "is_debit_type";
    private static final String IS_ENTITY_NAME = "is_entity_name";
    private static final String FREQUENCY_TYPE = "frequency_type";
    private static final String MANDATE = "mandate";
    private static final String MODE_OF_PAYMENT = "mode_of_payment";
    private static final String PERIOD_TYPE = "period_type";
    private static final String TO_DEBIT = "to_debit";
    private static final String SAVE_PASSWORD = "save_password";

    private static preferance mInstance;
    private static Context mCtx;
    private static String MAX_AMOUNT = "MAX_AMOUNT";
    private String MANDATE_ID_NEW = "mandate_id_new";
    private String IS_EMANDATE = "IS_EMANDATE";
    private String IS_TODATE_MANDATORY = "IS_TODATE_MANDATORY";
    private String APPID = "APPID";

    public preferance(Context context) {
        mCtx = context;
    }

    public static synchronized preferance getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new preferance(context);
        }
        return mInstance;
    }

    public boolean saveMandateID(String mandateid) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MANDATE_ID_NEW, mandateid);
        editor.apply();
        return true;
    }

    public String getMandateID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MANDATE_ID_NEW, "");
    }

    public boolean savepassword(String password) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_PASSWORD, password);
        editor.apply();
        return true;
    }

    public void savebankArrayList(ArrayList<bank> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<bank> getbankArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<bank>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    ///////sponsorlist
    public void savesponsorArrayList(ArrayList<sponserbank> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<sponserbank> getsponsorArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<sponserbank>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    ///////utility

    public boolean savetodebit(String isdebittype) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TO_DEBIT, isdebittype);
        editor.apply();
        return true;
    }

    public boolean saveisdebittype(String isdebittype) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_DEBIT_TYPE, isdebittype);
        editor.apply();
        return true;
    }

    public boolean saveMaxAmount(String maxAmount) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MAX_AMOUNT, maxAmount);
        editor.apply();
        return true;
    }

    public boolean saveisentityname(String isentityname) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_ENTITY_NAME, isentityname);
        editor.apply();
        return true;
    }

    public boolean savefrequencytype(String frequencytype) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FREQUENCY_TYPE, frequencytype);
        editor.apply();
        return true;
    }

    public boolean savemodeofpayment(String modeofpayment) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MODE_OF_PAYMENT, modeofpayment);
        editor.apply();
        return true;
    }

    public boolean savemandate(String mandate) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MANDATE, mandate);
        editor.apply();
        return true;
    }

    public boolean saveperiodtype(String periodtype) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(periodtype, periodtype);
        editor.apply();
        return true;
    }

    public boolean saveuserLogin(String user_id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, user_id);
        editor.apply();
        return true;
    }

    public boolean savemandateidtempid(String mandateid) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MANDATE_ID_TEMP, mandateid);

        editor.apply();
        return true;
    }

    public boolean savemandateid(String mandateid) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MANDATE_ID, mandateid);

        editor.apply();
        return true;
    }

    public boolean saveislivenach(String livenach) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_LIVE_NACH, livenach);

        editor.apply();
        return true;
    }

    public boolean saveisshowmandatemode(String showmandatemode) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_SHOWMANDATE_MODE, showmandatemode);

        editor.apply();
        return true;
    }


    public boolean savereferencenumerric(String referencenumeric) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_REFERENCE_NUMERIC, referencenumeric);

        editor.apply();
        return true;
    }

    public boolean saveisreferenceedit(String refrenceedit) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_REFERENCE_EDIT, refrenceedit);

        editor.apply();
        return true;
    }

    public boolean savereferencecheck(String refrencecheck) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_REFERENCE_CHECK, refrencecheck);

        editor.apply();
        return true;
    }

    public boolean saveismandateedit(String mandateedit) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_MANDATE_EDIT, mandateedit);

        editor.apply();
        return true;
    }

    public String getIsmandetedit() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_MANDATE_EDIT, null);
    }

    public String getMaxAmount() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MAX_AMOUNT, "");
    }

    public String getUserId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID, "");
    }

    public String getStudentId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID, "");
    }

    public String get_mandate() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MANDATE, "");
    }

    public String get_mandate_id() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MANDATE_ID, "");
    }

    public String get_mandate_id_temp() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MANDATE_ID_TEMP, "");
    }


    public String get_todebit() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TO_DEBIT, "");
    }

    public String get_debittype() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_DEBIT_TYPE, "");
    }

    public String get_entityname() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_ENTITY_NAME, "");
    }

    public String get_frequencytype() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(FREQUENCY_TYPE, "");
    }

    public String get_ismandateedit() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_MANDATE_EDIT, "");
    }

    public String get_referencecheck() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_REFERENCE_CHECK, "");
    }

    public String get_referenceedit() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_REFERENCE_EDIT, "");
    }

    public String get_referencenumaric() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_REFERENCE_NUMERIC, "");
    }

    public String get_showmandatemode() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_SHOWMANDATE_MODE, "");
    }

    public String get_islivenach() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_LIVE_NACH, "");
    }

    public String get_modeofpayment() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MODE_OF_PAYMENT, "");
    }

    public String get_periodtype() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PERIOD_TYPE, "");
    }

    public String get_password() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_PASSWORD, "");
    }

    public void clearuserSession() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(USER_ID).commit();

        Log.e("SharedPrefManager", "session cleared...");
    }

    public void saveutilityArrayList(ArrayList<Utility> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<Utility> getutilityArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Utility>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


    public void saveDebitTypeList(ArrayList<EntityDebitType> debitTypeList, String debitTypeList1) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(debitTypeList);
        editor.putString(debitTypeList1, json);
        editor.apply();
    }

    public ArrayList<EntityDebitType> getDebitTypeList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<EntityDebitType>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


    public void savePeriodsList(ArrayList<EntityPeriondEnableOn> debitTypeList, String debitTypeList1) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(debitTypeList);
        editor.putString(debitTypeList1, json);
        editor.apply();
    }

    public ArrayList<EntityPeriondEnableOn> getPeriodsList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<EntityPeriondEnableOn>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void saveToDebitList(ArrayList<EntityTodebit> debitTypeList, String debitTypeList1) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(debitTypeList);
        editor.putString(debitTypeList1, json);
        editor.apply();
    }

    public ArrayList<EntityTodebit> getToDebitList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<EntityTodebit>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


    public void saveFrequencyList(ArrayList<EntityFrequency> debitTypeList, String debitTypeList1) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(debitTypeList);
        editor.putString(debitTypeList1, json);
        editor.apply();
    }

    public ArrayList<EntityFrequency> getFrequencyList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<EntityFrequency>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


    public boolean saveisEMandate(String isdebittype) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_EMANDATE, isdebittype);
        editor.apply();
        return true;
    }

    public String get_isEMandate() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_EMANDATE, "");
    }


    public boolean saveToDateMandatory(String isTodateMandatoryEnach) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_TODATE_MANDATORY, isTodateMandatoryEnach);
        editor.apply();
        return true;
    }

    public String getToDateMandatory() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_TODATE_MANDATORY, "");
    }

    public boolean saveAppId(String appId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APPID, appId);
        editor.apply();
        return true;
    }

    public String getAPPID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(APPID, "");
    }
}
