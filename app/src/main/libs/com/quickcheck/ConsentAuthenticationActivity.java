package com.quickcheck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ConsentAuthenticationActivity extends AppCompatActivity {
    TextView textViewBusinessCode, textViewEntityName, textViewIFSC, textViewSponsorBank, textViewReferenceBank, textViewCustomerName, textViewCustomerBank, textViewAccountNumber, textViewCustomerIFSC, textViewFromDate, textViewToDate, textViewAmount, textViewPhoneNumber;
    RadioButton radioButtonIDoNOt, radioButtonByContinuing;
    private String mandateData, mandateId, dDateTime, MerchantKey, EntityId, EsignRequestId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_consent_authentication);
        getIntentData();
        getUiID();
        setIntentData();
    }

    private void setIntentData() {
        try {
            JSONObject jsonObject = new JSONObject(String.valueOf(mandateData));
            textViewBusinessCode.setText(jsonObject.getString("EntityBusinessCode"));
            textViewEntityName.setText(jsonObject.getString("EntityName"));
            textViewIFSC.setText(jsonObject.getString("IFSC1"));
            textViewSponsorBank.setText(jsonObject.getString("SponsorBankName"));
            textViewReferenceBank.setText(jsonObject.getString("Refrence1"));
            textViewCustomerName.setText(jsonObject.getString("Customer1"));
            textViewCustomerBank.setText(jsonObject.getString("customerBankname"));
            textViewAccountNumber.setText(jsonObject.getString("AcNo"));
            textViewCustomerIFSC.setText(jsonObject.getString("customerIFSC"));
            textViewFromDate.setText(jsonObject.getString("FromDate"));
            textViewToDate.setText(jsonObject.getString("TODATE"));
            textViewAmount.setText(jsonObject.getString("AmountRupees"));
            textViewPhoneNumber.setText(jsonObject.getString("PhoneNumber"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUiID() {
        textViewBusinessCode = findViewById(R.id.textViewBusinessCode);
        textViewEntityName = findViewById(R.id.textViewEntityName);
        textViewIFSC = findViewById(R.id.textViewIFSC);
        textViewSponsorBank = findViewById(R.id.textViewSponsorBank);
        textViewReferenceBank = findViewById(R.id.textViewReferenceBank);
        textViewCustomerName = findViewById(R.id.textViewCustomerName);
        textViewCustomerBank = findViewById(R.id.textViewCustomerBank);
        textViewAccountNumber = findViewById(R.id.textViewAccountNumber);
        textViewCustomerIFSC = findViewById(R.id.textViewCustomerIFSC);
        textViewFromDate = findViewById(R.id.textViewFromDate);
        textViewToDate = findViewById(R.id.textViewToDate);
        textViewAmount = findViewById(R.id.textViewAmount);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        radioButtonByContinuing = findViewById(R.id.radioButtonByContinuing);
        radioButtonIDoNOt = findViewById(R.id.radioButtonIDoNOt);
        radioButtonByContinuing.setChecked(true);
        radioButtonIDoNOt.setChecked(false);

    }

    private void getIntentData() {
        mandateData = getIntent().getStringExtra("MandateData");
        mandateId = getIntent().getStringExtra("MandateId");
        MerchantKey = getIntent().getStringExtra("MerchantKey");
        dDateTime = getIntent().getStringExtra("dDateTime");


    }

    public void onSubmitButtonClick(View view) {
        if (radioButtonByContinuing.isChecked()) {
            startActivity(new Intent(this, AuthenticationModeActivity.class).putExtra("MandateId", mandateId).putExtra("MerchantKey", MerchantKey).putExtra("dDateTime", dDateTime));
            finish();
            overridePendingTransition(0, 0);

        } else if (radioButtonIDoNOt.isChecked()) {
            HomeActivity.callCancelBUtton(mandateId, MerchantKey, dDateTime, "User has cancelled the eSign Transaction");
            onBackPressed();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HomeActivity.callCancelBUtton(mandateId, MerchantKey, dDateTime, "User has cancelled the eSign Transaction");
        finish();
        overridePendingTransition(0, 0);

    }
}
