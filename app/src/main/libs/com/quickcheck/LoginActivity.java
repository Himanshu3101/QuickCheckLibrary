package com.quickcheck;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    private TextView tvTextforgot;
    Button tvSubmit;
    private EditText edtUserid, edtPassword, edtAppId;
    private ImageView imgLogo;
    String userId, password;

    String debittype, entityname, FrequencyType, IsMandateEdit, IsRefrenceCheck, IsRefrenceEdit, IsRefrenceNumeric, IsShowMandateMode,
            IsliveInNach, Mandate, ModeOfPayment, PeriodType, ToDebit, mandateid, strImagepath;

    ArrayList<bank> banklist;
    ArrayList<sponserbank> spbanklist;
    ArrayList<Utility> utlitybanklist;

    ArrayList<EntityFrequency> FrequencyList;
    ArrayList<EntityDebitType> DebitTypeList;
    ArrayList<EntityPeriondEnableOn> PeriodsList;
    ArrayList<EntityTodebit> ToDebitList;

    boolean showPass = false;
    String userid;
    private String isEmandate, appid = "";
    private ImageView imageView_password;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        banklist = new ArrayList<>();
        spbanklist = new ArrayList<>();
        utlitybanklist = new ArrayList<>();

        FrequencyList = new ArrayList<>();
        DebitTypeList = new ArrayList<>();
        PeriodsList = new ArrayList<>();
        ToDebitList = new ArrayList<>();

        imgLogo = findViewById(R.id.img_logoimage);

        // getlogo();

        tvTextforgot = findViewById(R.id.tv_textforgot);
        tvSubmit = findViewById(R.id.tv_textsubmit);
        edtAppId = findViewById(R.id.edtAppId);
        edtUserid = findViewById(R.id.edt_userid);
        edtUserid.setNextFocusDownId(R.id.edt_password);

        edtPassword = findViewById(R.id.edt_password);
        edtPassword.setNextFocusDownId(R.id.tv_textsubmit);
        edtPassword.setCursorVisible(true);

        imageView_password = findViewById(R.id.imageView_password);
        imageView_password.setBackgroundResource(R.mipmap.hide_password);

        imageView_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!showPass) {
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageView_password.setBackgroundResource(R.mipmap.show_password);
                    edtPassword.setSelection(edtPassword.getText().toString().length());
                    showPass = true;
                } else {
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageView_password.setBackgroundResource(R.mipmap.hide_password);
                    edtPassword.setSelection(edtPassword.getText().toString().length());
                    showPass = false;
                }
            }
        });
        tvTextforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotpopup();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                String PASSWORD_PATTERN =
                        "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

                userid = edtUserid.getText().toString();
                password = edtPassword.getText().toString();
                appid = edtAppId.getText().toString();

                if (edtUserid.getText().toString().equals("")) {
                    edtUserid.setError(getString(R.string.fill_userid));
                } else if (edtAppId.getText().toString().equals("")) {
                    edtAppId.setError(getString(R.string.fillAppId));
                } else if (edtAppId.getText().toString().trim().length() < 6) {
                    edtAppId.setError(getString(R.string.appidshouldbeof6lenghth));
                } else if (edtPassword.getText().toString().equals("")) {
                    edtPassword.setError(getString(R.string.fill_paswrd));
                } else {
                    preferance.getInstance(getApplicationContext()).savepassword(edtPassword.getText().toString());
                    forlogin(userid, appid, password);
                }
            }
        });
    }

    public void forgotpopup() {                  //////  forgot popup
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.forgotdialod);

        Button btn_submitforgot = dialog.findViewById(R.id.btn_submitforgot);

        final EditText edtforgotvalue = dialog.findViewById(R.id.edt_forgot);

        btn_submitforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtforgotvalue.getText().toString().equalsIgnoreCase("")) {
                    edtforgotvalue.setError(getString(R.string.fill_userid));
                } else {
                    String str = edtforgotvalue.getText().toString();
                    forgotpass(str);
                }

            }
        });

        dialog.show();
    }

    public void forlogin(final String useridd, String appid, final String passwordd) {

        final ProgressDialog pb = ProgressDialog.show(this, "Loading", "Please wait...", false, false);
        pb.show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("emailId", useridd);
        params.put("password", passwordd);
        params.put("AppId", appid);

        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.login, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
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
                        preferance.getInstance(getApplicationContext()).saveMaxAmount(MaxAmount);
                        preferance.getInstance(getApplicationContext()).saveToDateMandatory(ISTodateMandatoryEnach);
                        storeuserSession(resid);

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("strtem", "1");
                        preferance.getInstance(getApplicationContext()).savebankArrayList(banklist, "bank");
                        preferance.getInstance(getApplicationContext()).savesponsorArrayList(spbanklist, "sponsor");
                        preferance.getInstance(getApplicationContext()).saveutilityArrayList(utlitybanklist, "utility");

                        preferance.getInstance(getApplicationContext()).saveDebitTypeList(DebitTypeList, "DebitTypeList");
                        preferance.getInstance(getApplicationContext()).savePeriodsList(PeriodsList, "PeriodsList");
                        preferance.getInstance(getApplicationContext()).saveToDebitList(ToDebitList, "ToDebitList");
                        preferance.getInstance(getApplicationContext()).saveFrequencyList(FrequencyList, "FrequencyList");

                        preferance.getInstance(getApplicationContext()).saveAppId(edtAppId.getText().toString().trim());

                        preferance.getInstance(getApplicationContext()).saveisEMandate(isEmandate);
                        preferance.getInstance(getApplicationContext()).saveisdebittype(debittype);
                        preferance.getInstance(getApplicationContext()).saveisentityname(entityname);
                        preferance.getInstance(getApplicationContext()).savefrequencytype(FrequencyType);
                        preferance.getInstance(getApplicationContext()).saveismandateedit(IsMandateEdit);
                        preferance.getInstance(getApplicationContext()).savereferencecheck(IsRefrenceCheck);
                        preferance.getInstance(getApplicationContext()).saveisreferenceedit(IsRefrenceEdit);
                        preferance.getInstance(getApplicationContext()).savereferencecheck(IsRefrenceCheck);
                        preferance.getInstance(getApplicationContext()).savereferencenumerric(IsRefrenceNumeric);
                        preferance.getInstance(getApplicationContext()).saveisshowmandatemode(IsShowMandateMode);
                        preferance.getInstance(getApplicationContext()).saveislivenach(IsliveInNach);
                        preferance.getInstance(getApplicationContext()).savemandate(Mandate);
                        preferance.getInstance(getApplicationContext()).savemodeofpayment(ModeOfPayment);
                        preferance.getInstance(getApplicationContext()).saveperiodtype(PeriodType);
                        preferance.getInstance(getApplicationContext()).savetodebit(ToDebit);
                        preferance.getInstance(getApplicationContext()).savemandateid(mandateid);
                        pb.dismiss();
                        startActivity(intent);
                        finish();

                        overridePendingTransition(0, 0);


                    } else {
                        if (resStatus.equals("Invalid AppId")) {
                            pb.dismiss();
                            Toast.makeText(LoginActivity.this, "Invalid AppId", Toast.LENGTH_SHORT).show();
                        } else {
                            pb.dismiss();  // CustomDialogForMessages.showMessageAlert(LoginActivity.this, resStatus, message);
                            sweetalert();
                        }
                        // Toast.makeText(LoginActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    pb.dismiss();
                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(LoginActivity.this, getString(R.string.failure), e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                pb.dismiss();
              //  CustomDialogForMessages.showMessageAlert(LoginActivity.this, getString(R.string.failure), e.getCause().getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        req.setRetryPolicy(new DefaultRetryPolicy(60000, 6, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
        // MyApplication.getInstance().addToReqQueue(req);
    }

    public void sweetalert() {
        Toast.makeText(getApplicationContext(), R.string.invalid_user, Toast.LENGTH_LONG).show();
        ;

    }

    public void storeuserSession(String user_id) {
        //saving the token on shared preferences
        preferance.getInstance(getApplicationContext()).saveuserLogin(user_id);
    }

    public void forgotpass(String userid) {

        final ProgressDialog pb = ProgressDialog.show(this, "Loading", "Please wait...", false, false);
        pb.show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("UserName", userid);
        String appId = preferance.getInstance(this).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.forgotpassword, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));

                    JSONObject obj2 = loginData.getJSONObject("SendEmailResult");
                    String resStatus = obj2.getString("status");
                    String message = obj2.getString("message");

                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(LoginActivity.this, getString(R.string.failure), e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(LoginActivity.this, getString(R.string.failure), e.getCause().getMessage());
            }
        });
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }

    public void getlogo() {
        String user_id = null;
        user_id = preferance.getInstance(getApplicationContext()).getUserId();

        final ProgressDialog pb = ProgressDialog.show(this, "Loading", "Please wait...", false, false);
        pb.show();

        HashMap<String, String> params = new HashMap<String, String>();
        // params.put("AppId", "100001");

        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.logoimage, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                pb.dismiss();

                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    JSONObject obj = loginData.getJSONObject("LogoImageResult");
                    strImagepath = obj.getString("filePath");

                    Picasso.get()
                            .load(strImagepath)
                            .into(imgLogo);

                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(LoginActivity.this, getString(R.string.failure), e.getMessage());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(LoginActivity.this, getString(R.string.failure), e.getCause().getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
        // MyApplication.getInstance().addToReqQueue(req);
    }

    @Override
    public void onBackPressed() {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure!")
                .setContentText("Do you want to exit?")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finish();
                        System.exit(0);
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
}
