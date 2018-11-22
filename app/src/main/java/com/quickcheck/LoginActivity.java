package com.quickcheck;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
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

public class LoginActivity extends AppCompatActivity {
    ProgressBar progressBar;
    static String debittype, entityname, FrequencyType, IsMandateEdit, IsRefrenceCheck, IsRefrenceEdit, IsRefrenceNumeric, IsShowMandateMode,
            IsliveInNach, Mandate, ModeOfPayment, PeriodType, ToDebit, mandateid, strImagepath;

    static ArrayList<bank> banklist;
    static ArrayList<sponserbank> spbanklist;
    static ArrayList<Utility> utlitybanklist;

    static ArrayList<EntityFrequency> FrequencyList;
    static ArrayList<EntityDebitType> DebitTypeList;
    static ArrayList<EntityPeriondEnableOn> PeriodsList;
    static ArrayList<EntityTodebit> ToDebitList;

    boolean showPass = false;
    String userid;
    private static String isEmandate;
    private String appid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);
        banklist = new ArrayList<>();
        spbanklist = new ArrayList<>();
        utlitybanklist = new ArrayList<>();

        FrequencyList = new ArrayList<>();
        DebitTypeList = new ArrayList<>();
        PeriodsList = new ArrayList<>();
        ToDebitList = new ArrayList<>();
        getIntentData();
    }

    String jsonData, EmailId, Password, AppId = "";

    private void getIntentData() {
        jsonData = getIntent().getStringExtra("JsonData");
        EmailId = getIntent().getStringExtra("EmailId");
        Password = getIntent().getStringExtra("Password");
        AppId = getIntent().getStringExtra("AppId");
        initCredential(EmailId, Password, AppId);
    }

    public void initCredential(String EmailId, String AppId, String Password) {
        dologin(LoginActivity.this, EmailId, AppId, Password);
    }

    public void dologin(final Activity activity, final String useridd, final String appid, final String passwordd) {

        final ProgressDialog pb = ProgressDialog.show(activity, "Loading", "Please wait...", false, false);
        pb.show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("emailId", useridd);
        params.put("password", passwordd);
        params.put("AppId", appid);
        Toast.makeText(activity, useridd + passwordd + appid, Toast.LENGTH_SHORT).show();

        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.login, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();

                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    String resStatus = loginData.getString("status");
                    String message = loginData.getString("message");
                    isEmandate = loginData.getString("IsAllowENachMobile");
                    if (resStatus.equals("success")) {

                        JSONArray jsonarraybank = loginData.getJSONArray("Bank");

                        for (int i = 0; i < jsonarraybank.length(); i++) {

                            JSONObject obj = jsonarraybank.getJSONObject(i);
                            bank data1 = new bank();

                            data1.setBank_code(obj.getString("Code"));
                            data1.setNbank_name(obj.getString("Name"));

                            banklist.add(data1);
                        }
                        JSONArray jsonarraysponser = loginData.getJSONArray("SponsorBank");

                        JSONArray jsonArrayEntityDebitType = loginData.getJSONArray("EntityDebitType");
                        JSONArray jsonArrayEntityFrequency = loginData.getJSONArray("EntityFrequency");
                        JSONArray jsonArrayEntityPeriondEnableOn = loginData.getJSONArray("EntityPeriondEnableOn");
                        JSONArray jsonArrayEntityTodebit = loginData.getJSONArray("EntityTodebit");

                        for (int i = 0; i < jsonArrayEntityDebitType.length(); i++) {
                            JSONObject obj = jsonArrayEntityDebitType.getJSONObject(i);
                            EntityDebitType data2 = new EntityDebitType();
                            data2.setDebittype(obj.getString("debittype"));
                            data2.setIsenable(obj.getString("isenable"));
                            DebitTypeList.add(data2);
                        }
                        for (int i = 0; i < jsonArrayEntityFrequency.length(); i++) {
                            JSONObject obj = jsonArrayEntityFrequency.getJSONObject(i);
                            EntityFrequency data2 = new EntityFrequency();
                            data2.setFrequency(obj.getString("frequency"));
                            data2.setIsenable(obj.getString("isenable"));
                            FrequencyList.add(data2);
                        }
                        for (int i = 0; i < jsonArrayEntityPeriondEnableOn.length(); i++) {
                            JSONObject obj = jsonArrayEntityPeriondEnableOn.getJSONObject(i);
                            EntityPeriondEnableOn data2 = new EntityPeriondEnableOn();
                            data2.setPeriodenableon(obj.getString("periodenableon"));
                            data2.setIsenable(obj.getString("isenable"));
                            PeriodsList.add(data2);
                        }
                        for (int i = 0; i < jsonArrayEntityTodebit.length(); i++) {
                            JSONObject obj = jsonArrayEntityTodebit.getJSONObject(i);
                            EntityTodebit data2 = new EntityTodebit();
                            data2.setTodebit(obj.getString("todebit"));
                            data2.setIsenable(obj.getString("isenable"));
                            ToDebitList.add(data2);
                        }


                        for (int i = 0; i < jsonarraysponser.length(); i++) {
                            JSONObject obj = jsonarraysponser.getJSONObject(i);
                            sponserbank data2 = new sponserbank();
                            data2.setSpid(obj.getString("Id"));
                            data2.setSpname(obj.getString("Name"));
                            spbanklist.add(data2);
                        }

                        JSONArray jsonarrayutility = loginData.getJSONArray("Utility");
                        for (int i = 0; i < jsonarrayutility.length(); i++) {
                            JSONObject obj = jsonarrayutility.getJSONObject(i);
                            Utility data3 = new Utility();
                            data3.setUtid(obj.getString("Id"));
                            data3.setUtname(obj.getString("Name"));
                            utlitybanklist.add(data3);
                        }

                        debittype = loginData.getString("DebitType");
                        entityname = loginData.getString("EntityName");
                        FrequencyType = loginData.getString("FrequencyType");
                        IsMandateEdit = loginData.getString("IsMandateEdit");
                        IsRefrenceCheck = loginData.getString("IsRefrenceCheck");
                        IsRefrenceEdit = loginData.getString("IsRefrenceEdit");
                        IsRefrenceNumeric = loginData.getString("IsRefrenceNumeric");
                        IsShowMandateMode = loginData.getString("IsShowMandateMode");
                        IsliveInNach = loginData.getString("IsliveInNach");
                        Mandate = loginData.getString("Mandate");
                        ModeOfPayment = loginData.getString("ModeOfPayment");
                        PeriodType = loginData.getString("PeriodType");
                        ToDebit = loginData.getString("ToDebit");
                        mandateid = loginData.getString("Id");
                        String resid = loginData.getString("userId");
                        String MaxAmount = loginData.getString("MaxAmount");
                        String ISTodateMandatoryEnach = loginData.getString("ISTodateMandatoryEnach");
                        preferance.getInstance(activity).saveMaxAmount(MaxAmount);
                        preferance.getInstance(activity).saveToDateMandatory(ISTodateMandatoryEnach);
                        preferance.getInstance(activity).saveuserLogin(resid);


                        preferance.getInstance(activity).savebankArrayList(banklist, "bank");
                        preferance.getInstance(activity).savesponsorArrayList(spbanklist, "sponsor");
                        preferance.getInstance(activity).saveutilityArrayList(utlitybanklist, "utility");

                        preferance.getInstance(activity).saveDebitTypeList(DebitTypeList, "DebitTypeList");
                        preferance.getInstance(activity).savePeriodsList(PeriodsList, "PeriodsList");
                        preferance.getInstance(activity).saveToDebitList(ToDebitList, "ToDebitList");
                        preferance.getInstance(activity).saveFrequencyList(FrequencyList, "FrequencyList");

                        preferance.getInstance(activity).saveAppId(appid.trim());

                        preferance.getInstance(activity).saveisEMandate(isEmandate);
                        preferance.getInstance(activity).saveisdebittype(debittype);
                        preferance.getInstance(activity).saveisentityname(entityname);
                        preferance.getInstance(activity).savefrequencytype(FrequencyType);
                        preferance.getInstance(activity).saveismandateedit(IsMandateEdit);
                        preferance.getInstance(activity).savereferencecheck(IsRefrenceCheck);
                        preferance.getInstance(activity).saveisreferenceedit(IsRefrenceEdit);
                        preferance.getInstance(activity).savereferencecheck(IsRefrenceCheck);
                        preferance.getInstance(activity).savereferencenumerric(IsRefrenceNumeric);
                        preferance.getInstance(activity).saveisshowmandatemode(IsShowMandateMode);
                        preferance.getInstance(activity).saveislivenach(IsliveInNach);
                        preferance.getInstance(activity).savemandate(Mandate);
                        preferance.getInstance(activity).savemodeofpayment(ModeOfPayment);
                        preferance.getInstance(activity).saveperiodtype(PeriodType);
                        preferance.getInstance(activity).savetodebit(ToDebit);
                        preferance.getInstance(activity).savemandateid(mandateid);
                        pb.dismiss();
                        callData();

                    } else {
                        if (resStatus.equals("Invalid AppId")) {
                            pb.dismiss();
                            Toast.makeText(activity, "Invalid AppId", Toast.LENGTH_SHORT).show();
                        } else {
                            pb.dismiss();
                        }
                    }
                } catch (JSONException e) {
                    pb.dismiss();
                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(activity, activity.getString(R.string.failure), e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                pb.dismiss();
                //  CustomDialogForMessages.showMessageAlert(LoginActivity.this, getString(R.string.failure), e.getCause().getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        req.setRetryPolicy(new DefaultRetryPolicy(60000, 6, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
        // MyApplication.getInstance().addToReqQueue(req);
    }

    private void callData() {
        try {
            JSONObject data = new JSONObject(jsonData);
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("mandateid", mandateid);
            intent.putExtra("referenceno", data.getString("ReferenceNumber"));
            intent.putExtra("customername", data.getString("AccountHolder"));
            intent.putExtra("Enach", data.getString(""));
            intent.putExtra("Customername2", data.getString(""));
            intent.putExtra("Email", data.getString("EmailID"));
            intent.putExtra("PhoneNumber", data.getString("PhoneNumber"));
            intent.putExtra("Reference2", data.getString(""));
            intent.putExtra("Customername3", data.getString(""));
            intent.putExtra("Isphysical", data.getString(""));
            intent.putExtra("doneEmandate", data.getString(""));
            intent.putExtra("xmlpath", data.getString(""));
            intent.putExtra("AmountRupees", data.getString("Amount"));
            intent.putExtra("BankName", data.getString("BankName"));
            intent.putExtra("Code", data.getString("IFSC"));
            intent.putExtra("DebitType", data.getString("DebitType"));
            intent.putExtra("FrequencyType", data.getString("Frequency"));
            intent.putExtra("MandateMode", data.getString(""));
            intent.putExtra("SponsorbankCode", data.getString("SponsorBank"));
            intent.putExtra("ToDebit", data.getString("ToDebit"));
            intent.putExtra("FromDate", data.getString("FromDate"));
            intent.putExtra("ToDate", data.getString("ToDate"));
            intent.putExtra("UtilityCode", data.getString("UtilityCode"));
            intent.putExtra("AcNo", data.getString("BankAccountNumber"));
            intent.putExtra("Status", data.getString(""));
            intent.putExtra("Dateofmandate", data.getString("DateOfMandate"));
            intent.putExtra("strtem", "2");
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
