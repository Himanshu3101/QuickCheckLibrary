package com.quickcheck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

public class AuthenticationModeActivity extends AppCompatActivity {
    RadioButton radioButtonOTP, radioButtonFingerPrint;
    CustomButtonRegular buttonsubmit, buttonCancel;
    private String MandateId, MerchantKey, dDateTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_authentication_mode);
        getUiId();
        getIntentData();
    }

    private void getIntentData() {
        MandateId = getIntent().getStringExtra("MandateId");
        MerchantKey = getIntent().getStringExtra("MerchantKey");
        dDateTime = getIntent().getStringExtra("dDateTime");

    }

    private void getUiId() {
        radioButtonOTP = findViewById(R.id.radioButtonOTP);
        radioButtonFingerPrint = findViewById(R.id.radioButtonFingerPrint);
        buttonsubmit = findViewById(R.id.buttonsubmit);
        buttonCancel = findViewById(R.id.buttonCancel);
        radioButtonOTP.setChecked(true);
        radioButtonFingerPrint.setChecked(false);
    }

    public void onSubmitButtonClick(View view) {
        if (radioButtonFingerPrint.isChecked()) {
            startActivity(new Intent(this, MobileVerificationActivity.class).putExtra("AuthenticationType", "2").putExtra("MandateId", MandateId).putExtra("MerchantKey", MerchantKey).putExtra("dDateTime", dDateTime));
            finish();
            overridePendingTransition(0, 0);
        } else if (radioButtonOTP.isChecked()) {
            startActivity(new Intent(this, MobileVerificationActivity.class).putExtra("AuthenticationType", "1").putExtra("MandateId", MandateId).putExtra("MerchantKey", MerchantKey).putExtra("dDateTime", dDateTime));
            finish();
            overridePendingTransition(0, 0);
        }
    }

    public void onCancelButtonClick(View view) {
        HomeActivity.callCancelBUtton(MandateId, MerchantKey, dDateTime, "User has cancelled the eSign Transaction");
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);

    }
}
