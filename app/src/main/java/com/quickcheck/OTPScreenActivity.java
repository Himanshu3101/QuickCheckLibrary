package com.quickcheck;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.nsdl.egov.esignaar.NsdlEsignActivity;
import com.quickcheck.preference.preferance;
import com.quickcheck.webservice.Serverfuction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class OTPScreenActivity extends AppCompatActivity {
    PinView firstPinView;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1, REQUEST_CODE = 101;
    private String phoneNumber, MandateId, AuthenticationType, UserID, NSDXMLPath, MerchantKey, dDateTime, EntityId, EsignRequestId = "";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otpscreen);
        checkReadSmsPermisssion();
        getUiID();
        getIntentData();
        getOTPFromServer(phoneNumber);

    }

    private void getOTPFromServer(String mobileNumber) {
        final ProgressDialog pb = ProgressDialog.show(this, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("MobileNo", mobileNumber);
        String appId=preferance.getInstance(this).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.SendOTP, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    JSONObject jsonObject = loginData.getJSONObject("SendOTPResult");
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("Success")) {

                    } else {
                        CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, status, message);
                    }

                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, getString(R.string.failure), e.getMessage());

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, getString(R.string.failure), e.getCause().getMessage());
                pb.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);

    }

    private void getIntentData() {
        phoneNumber = getIntent().getStringExtra("PhoneNumber");
        AuthenticationType = getIntent().getStringExtra("AuthenticationType");
        MandateId = getIntent().getStringExtra("MandateId");
        UserID = preferance.getInstance(OTPScreenActivity.this).getStudentId();
        MerchantKey = getIntent().getStringExtra("MerchantKey");
        dDateTime = getIntent().getStringExtra("dDateTime");

    }

    private void getUiID() {
        firstPinView = findViewById(R.id.firstPinView);
    }

    private boolean checkReadSmsPermisssion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                return false;
            } else return true;
        }
        return true;
    }


    public void onValidateOtpButtonClick(View view) {
        if (firstPinView.getText().toString().length() == 0) {
            firstPinView.setError("Fill OTP");
            firstPinView.requestFocus();
        } else {
            ValidateOTP(firstPinView.getText().toString());
        }
    }

    private void ValidateOTP(String otp) {
        final ProgressDialog pb = ProgressDialog.show(this, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("MobileNo", phoneNumber);
        params.put("OTP", otp);
        params.put("MandateId", MandateId);
        params.put("AuthType", AuthenticationType);
        params.put("UserID", UserID);
        String appId=preferance.getInstance(this).getAPPID();
        params.put("AppID", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.ValidateOTP, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    JSONObject jsonObject = loginData.getJSONObject("ValidateOTPResult");
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    String NSDXML = jsonObject.getString("NSDXML");
                    String ProdType = jsonObject.getString("ProdType");
                    NSDXMLPath = jsonObject.getString("NSDXMLPath");
                    EntityId = jsonObject.getString("EntityId");
                    EsignRequestId = jsonObject.getString("EsignRequestId");
                    if (status.equals("failure")) {
                        CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, status, message);
                    } else if (status.equals("Success")) {
                        Intent appStartIntent = new Intent(OTPScreenActivity.this, NsdlEsignActivity.class); //MainActivity should be the ASP Activity
                        appStartIntent.putExtra("msg", NSDXML); // msg contains esign request xml from ASP.
                        appStartIntent.putExtra("env", ProdType); //Possible values PREPROD or PROD (case sensitive).
                        appStartIntent.putExtra("returnUrl", "com.zipnach"); // your package name where esign
                        startActivityForResult(appStartIntent, REQUEST_CODE);
                        overridePendingTransition(0, 0);
                    }
                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, getString(R.string.failure), e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, getString(R.string.failure), e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
               // CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, getString(R.string.failure), e.getCause().getMessage());
                pb.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        req.setRetryPolicy(new DefaultRetryPolicy(20000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String eSignResponse = data.getStringExtra("signedResponse");
                String newResponse = eSignResponse.replace("\"", "\'");
                HomeActivity.callReturnNSDLResponse(NSDXMLPath, newResponse, MandateId, UserID, MerchantKey, dDateTime, EntityId, EsignRequestId);
                onBackPressed();
            } else {
            }
        }
    }


    public void onResendButtonClick(View view) {
        final ProgressDialog pb = ProgressDialog.show(this, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("MobileNo", phoneNumber);
        String appId=preferance.getInstance(this).getAPPID();
        params.put("AppId", appId);

        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.ReSendOTP, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));

                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, getString(R.string.failure), e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, getString(R.string.failure), e.getCause().getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);


    }

    public void onVoiceOTPButtonClick(View view) {
        final ProgressDialog pb = ProgressDialog.show(this, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("MobileNo", phoneNumber);
        String appId=preferance.getInstance(this).getAPPID();
        params.put("AppId", appId);

        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.VoiceOTP, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));

                } catch (JSONException e) {

                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, getString(R.string.failure), e.getMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, getString(R.string.failure), e.getCause().getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                if (message.length() < 6) {
                    CustomDialogForMessages.showMessageAlert(OTPScreenActivity.this, "Wrong OTP", "Enter OTP Manually");
                } else if (message.length() == 6) {
                    firstPinView.setText(message);
                }

            }
        }
    };

    public void onCancelButtonClick(View view) {
        HomeActivity.callCancelBUtton(MandateId, MerchantKey, dDateTime, "User has cancelled the eSign Transaction");
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);

    }

}
