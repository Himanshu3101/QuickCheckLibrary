package com.quickcheck;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class MobileVerificationActivity extends AppCompatActivity {

    private String AuthenticationType, MandateId, MerchantKey, dDateTime = "";
    EditText editTextMobileNumber;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mobile_verification);
        getIntentData();
    }

    private void getIntentData() {
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        AuthenticationType = getIntent().getStringExtra("AuthenticationType");
        MandateId = getIntent().getStringExtra("MandateId");
        MerchantKey = getIntent().getStringExtra("MerchantKey");
        dDateTime = getIntent().getStringExtra("dDateTime");

    }

    public void onSubmitButtonClick(View view) {
        if (editTextMobileNumber.getText().toString().equals("")) {
            editTextMobileNumber.setError(getString(R.string.fill_mobile_number));
        } else if (editTextMobileNumber.getText().toString().length() < 10) {
            editTextMobileNumber.setError(getString(R.string.InValidPhoneNumber));
        } else {
            startActivity(new Intent(MobileVerificationActivity.this, OTPScreenActivity.class).putExtra("PhoneNumber", editTextMobileNumber.getText().toString()).putExtra("AuthenticationType", AuthenticationType).putExtra("MandateId", MandateId).putExtra("MerchantKey", MerchantKey).putExtra("dDateTime", dDateTime));
            finish();
            overridePendingTransition(0, 0);
        }

    }


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
