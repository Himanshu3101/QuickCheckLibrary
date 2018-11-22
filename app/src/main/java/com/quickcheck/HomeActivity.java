package com.quickcheck;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.squareup.picasso.Picasso;
import com.quickcheck.adapter.Banknameadapter;
import com.quickcheck.adapter.ReferenceAdapter;
import com.quickcheck.adapter.Sponserbankdialogadapter;
import com.quickcheck.adapter.Utilitydialogadapter;
import com.quickcheck.model.Refrencedata;
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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.view.Gravity.RIGHT;

public class HomeActivity extends AppCompatActivity {
    static int REQUEST_CODE = 100;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    private static boolean alltrue = true;
    private static boolean isEnach = false;
    private static boolean isPhysical = false;

    ArrayList<bank> banklist;
    static ArrayList<sponserbank> spbanklist;
    static ArrayList<Utility> utlitybanklist;
    ArrayList<Refrencedata> reflist, reftemplist;
    ImageButton img_edit, img_power, imageNew;
    private ProgressDialog mProgressDialog;
    public static Dialog dialogrefrence, dialogsuccess, dialogEnash;
    public static int count = 1, mYear, mMonth, mDay;
    static String successref = "1";
    static EditText edtaccountnumber, edtIfsc, edtmicr, edtamount, edtrefference, edtreference2, edtphonenumber, edtemail, edtprimaryholder, edtnameaccholder, edtnameaccholder3;
    static TextView spin_utilitybank, textViewTitle, textView_amount, tv_sponsorbank, tvbankname, edtauthorize, utilitycode, edtmandate, edtfromdate, edttodate, tv_mandatemodename;
    static Button tv_submitmandate, tv_upload, tv_Enach, tv_physical, tv_download;
    static AppCompatCheckBox chcancel;
    static RadioGroup radiotodebit, rgbshowmandate, rgbfrequency, rgbdebittype;
    static AppCompatRadioButton rb_none, rb_firstemipdc, rb_npdl, rb_fullpdc, rb_halfyearly, rb_aspresented, rb_sbnrodebit, rb_cbnre, rbSb, rbCa, rbcc, rbother, rbmonthly, rbquiterly, rbyearly, rbfixammount, rbmaxammount;
    static Spinner spin_sponsorbank;
    public static String debittype, Isphysical, entityname, FrequencyType, IsMandateEdit, IsRefrenceCheck, IsRefrenceEdit, IsRefrenceNumeric, IsShowMandateMode,
            IsliveInNach, Mandate, ModeOfPayment, PeriodType, ToDebit, strmandate, strProcessname = "null", strtem = "", strisreferenceedit, strimagepath, userId, mandateId2,
            strdateofmandate, doneEmandate, xmlpath, Enach, Customername3, Reference2, PhoneNumber, Email, Customername2, newString, FromDate, Todate, strsponsorbank, strutilitycode, strauthorize, strtodebit, strbankaccountnumber, strbankname, strifscc, strmicr, stramount, strfrequency, strdebittype, strreference1, strreference2, strphonenyumber, stremail, strfromdate, strtodate, straccholder1, straccholder2, straccholder3, strmandatemode, status, strsponsorname, strutility, strmydate, strmandatevalue = "";
    boolean addNew = false, mandateModeChecked = false;
    public static ImageView imageViewNew;
    private AsyncTask mMyTask;
    static Activity homeActivity;
    private static Dialog dialogFinalsuccess;
    public static String mandateId = "";
    ArrayList<EntityFrequency> FrequencyList;
    ArrayList<EntityDebitType> DebitTypeList;
    ArrayList<EntityPeriondEnableOn> PeriodsList;
    ArrayList<EntityTodebit> ToDebitList;
    private ColorStateList colorStateList;
    public static String isEMandate = "";
    Bundle savedInstanceState2 = null;
    String modeValue = "None";
    private static String toDateMandatory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        userId = preferance.getInstance(HomeActivity.this).getStudentId();
        isEMandate = preferance.getInstance(HomeActivity.this).get_isEMandate();
        toDateMandatory = preferance.getInstance(HomeActivity.this).getToDateMandatory();
        isEnach = false;
        isPhysical = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
        reflist = new ArrayList<>();
        reftemplist = new ArrayList<>();
        FrequencyList = new ArrayList<>();
        DebitTypeList = new ArrayList<>();
        PeriodsList = new ArrayList<>();
        ToDebitList = new ArrayList<>();


        homeActivity = this;

        getUiID();
        getPreferenceAndIntentData();
        setPreferenceAndIntentData();
        setEmailLisners();
        mandateModeChecked = rgbshowmandate.getVisibility() != View.VISIBLE;
        tabButtonCLick();
        getAndSetDataAndCLick();
        if (textViewTitle.getText().toString().equalsIgnoreCase(getString(R.string.editMandateForm))) {
            edtfromdate.setText(FromDate);
            edttodate.setText(Todate);
            edttodate.setError(null, null);
            if (Todate == null) {
                Todate = "";
            }
            if (Todate.equalsIgnoreCase("") || Todate == null) {
                chcancel.setChecked(true);
            } else {
                chcancel.setChecked(false);
                edttodate.setError(null, null);
            }
        } else {
            edttodate.setText("");
            edtfromdate.setText("");
        }
        if (strmandatemode.equals("None")) {
            rb_none.setChecked(true);
            modeValue = "None";
        } else if (strmandatemode.equals("E")) {
            rb_firstemipdc.setChecked(true);
            modeValue = "E";
        } else if (strmandatemode.equals("N")) {
            rb_npdl.setChecked(true);
            modeValue = "N";
        } else if (strmandatemode.equals("F")) {
            rb_fullpdc.setChecked(true);
            modeValue = "F";
        }
        if (strtem.equals("2")) {
            count++;
            edtrefference.setEnabled(false);
            tv_submitmandate.setText(homeActivity.getString(R.string.update));
        } else if (strtem.equals("4")) {
            count++;
            edtrefference.setEnabled(true);
        } else {
            count = 1;
            edtrefference.setEnabled(true);
        }

    }

    private void onCLickEvent() {
        if (strtem.equals("2")) {
            edtaccountnumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            edtIfsc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            edtmicr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            edtamount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            edtrefference.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            edtreference2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            edtphonenumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            edtemail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            edtprimaryholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            edtnameaccholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            edtnameaccholder3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });

            spin_sponsorbank.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    return false;
                }
            });


            edtmandate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            radiotodebit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            tvbankname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });


            rgbfrequency.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rgbdebittype.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            chcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });

            rbSb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rbCa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rbcc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rbother.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rbmonthly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rbquiterly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rbyearly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rbfixammount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rb_aspresented.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rb_halfyearly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rb_cbnre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rb_sbnrodebit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rb_none.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rb_firstemipdc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rb_npdl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            rb_fullpdc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });

            edtfromdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
            edttodate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                }
            });
        }
    }

    private void getAndSetDataAndCLick() {

        edtIfsc.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                String s = et.toString();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    edtIfsc.setText(s);
                }
                edtIfsc.setSelection(edtIfsc.getText().length());
            }
        });
        edtprimaryholder.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                String s = et.toString();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    edtprimaryholder.setText(s);
                }
                edtprimaryholder.setSelection(edtprimaryholder.getText().length());
            }
        });
        edtnameaccholder.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                String s = et.toString();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    edtnameaccholder.setText(s);
                }
                edtnameaccholder.setSelection(edtnameaccholder.getText().length());
            }
        });
        edtnameaccholder3.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                String s = et.toString();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    edtnameaccholder3.setText(s);
                }
                edtnameaccholder3.setSelection(edtnameaccholder3.getText().length());
            }
        });
        edtamount.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                try {
                    if (et.toString().equalsIgnoreCase(""))
                        textView_amount.setVisibility(View.GONE);
                    else textView_amount.setVisibility(View.VISIBLE);

                    long amount = Long.parseLong(et.toString());
                    if (amount > 10000000) {
                        edtamount.setError("Amount should not be greater than 1 Crore");
                    } else {
                        edtamount.setError(null, null);
                        Long s = Long.parseLong(et.toString());
                        newString = EnglishNumberToWords.convert(s);
                        textView_amount.setText(String.format("%s Only", newString));
                    }

                } catch (Exception e) {
                    textView_amount.setText(String.format("%s Only", newString));
                }
            }
        });

        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploaddialog(mandateId);

            }
        });

        tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!strimagepath.equals("")) {
                    new DownloadTask(homeActivity, strimagepath);
                } else {
                    Toast.makeText(HomeActivity.this, "file not found", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ///////set radio button
        rgbdebittype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()

        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radiodebittype = findViewById(i);
                String str = radiodebittype.getText().toString();
                if (str.equals("Fixed Amount")) {
                    strdebittype = "f";
                } else if (str.equals("Maximum Amount")) {
                    strdebittype = "m";
                }
            }
        });

        rgbfrequency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()

        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radiofrequency = findViewById(i);
                String str = radiofrequency.getText().toString();

                if (str.equals("Monthly")) {
                    strfrequency = "m";
                } else if (str.equals(getString(R.string.quarterly))) {
                    strfrequency = "q";
                } else if (str.equals("Half-Yearly")) {
                    strfrequency = "h";
                } else if (str.equals("Yearly")) {
                    strfrequency = "y";
                } else if (str.equals(getString(R.string.quarterly))) {
                    strfrequency = "a";
                }

            }
        });

        radiotodebit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()

        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radiotodebit = findViewById(i);
                String str = radiotodebit.getText().toString();

                switch (str) {
                    case "SB":
                        strtodebit = "sb";
                        break;
                    case "CA":
                        strtodebit = "ca";
                        break;
                    case "CC":
                        strtodebit = "cc";
                        break;
                    case "SB-NRE":
                        strtodebit = "re";
                        break;
                    case "SB-NRO":
                        strtodebit = "rd";
                        break;
                    case "Other":
                        strtodebit = "ot";
                        break;
                }
            }
        });
        if (strmandatemode == null) {
            strmandatemode = "";
        }

        if (strmandatemode.equalsIgnoreCase("") || strmandatemode.equalsIgnoreCase("None")) {
            rb_none.setChecked(true);
            mandateModeChecked = false;
        } else if (strmandatemode.equals("First EMI PDC+NACH")) {
            rb_firstemipdc.setChecked(true);
            mandateModeChecked = true;

        } else if (strmandatemode.equals("NDPC")) {
            rb_npdl.setChecked(true);
            mandateModeChecked = true;

        } else if (strmandatemode.equals("FULL PDC")) {
            rb_fullpdc.setChecked(true);
            mandateModeChecked = true;

        }
        rgbshowmandate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()

        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radiomandate = findViewById(i);
                mandateModeChecked = i != R.id.rb_none;
                strmandatemode = radiomandate.getText().toString();
                if (strmandatemode.equals("None")) {
                    rb_none.setChecked(true);
                    modeValue = "None";
                    mandateModeChecked = false;

                } else if (strmandatemode.equals("First EMI PDC + NACH")) {
                    rb_firstemipdc.setChecked(true);
                    modeValue = "E";
                    mandateModeChecked = true;

                } else if (strmandatemode.equals("NPDC")) {
                    rb_npdl.setChecked(true);
                    modeValue = "N";
                    mandateModeChecked = true;

                } else if (strmandatemode.equals("FULL PDC")) {
                    rb_fullpdc.setChecked(true);
                    modeValue = "F";
                    mandateModeChecked = true;

                }
            }
        });
        if (strmandatemode == null || strmandatemode.equals("")) {
            strmandatemode = "";
            if (strmandatemode.equals("None")) {
                rb_none.setChecked(true);
                modeValue = "None";
            } else if (strmandatemode.equals("First EMI PDC + NACH")) {
                rb_firstemipdc.setChecked(true);
                modeValue = "E";
            } else if (strmandatemode.equals("NPDC")) {
                rb_npdl.setChecked(true);
                modeValue = "N";
            } else if (strmandatemode.equals("FULL PDC")) {
                rb_fullpdc.setChecked(true);
                modeValue = "F";
            }
        }


        if (IsShowMandateMode != null) {
            if (IsShowMandateMode.equals("True") && IsShowMandateMode != null && !IsShowMandateMode.isEmpty() && !IsShowMandateMode.equals("null")) {
                rgbshowmandate.setVisibility(View.VISIBLE);
                tv_mandatemodename.setVisibility(View.VISIBLE);
            } else {
                rgbshowmandate.setVisibility(View.GONE);
                tv_mandatemodename.setVisibility(View.GONE);
            }

        }

        if (strfrequency == null || strfrequency.equals("")) {
            strfrequency = "";
            if (FrequencyType.equals("Monthly")) {
                rbmonthly.setChecked(true);
                strfrequency = "m";
            } else if (FrequencyType.equals(getString(R.string.quarterly))) {
                rbquiterly.setChecked(true);
                strfrequency = "q";
            } else if (FrequencyType.equals("Yearly")) {
                rbyearly.setChecked(true);
                strfrequency = "y";
            } else if (FrequencyType.equals("Half-Yearly")) {
                rb_halfyearly.setChecked(true);
                strfrequency = "h";
            } else if (FrequencyType.equals(getString(R.string.as_amp_when_presented))) {
                rb_halfyearly.setChecked(true);
                strfrequency = "a";
            }
        }
        if (strfrequency.equals("Monthly")) {
            rbmonthly.setChecked(true);
            strfrequency = "m";
        } else if (strfrequency.equals(getString(R.string.quarterly))) {
            rbquiterly.setChecked(true);
            strfrequency = "q";
        } else if (strfrequency.equals("Yearly")) {
            rbyearly.setChecked(true);
            strfrequency = "y";
        } else if (strfrequency.equals("Half-Yearly")) {
            rb_halfyearly.setChecked(true);
            strfrequency = "h";
        } else if (strfrequency.equals(getString(R.string.as_amp_when_presented))) {
            rb_halfyearly.setChecked(true);
            strfrequency = "a";
        }


        if (strtodebit == null || strtodebit.equals("")) {
            strtodebit = "";
            switch (ToDebit) {
                case "SB":
                    rbSb.setChecked(true);
                    strtodebit = "sb";
                    break;
                case "CA":
                    rbCa.setChecked(true);
                    strtodebit = "ca";
                    break;
                case "CC":
                    rbcc.setChecked(true);
                    strtodebit = "cc";
                    break;
                case "SB-NRE":
                    rb_cbnre.setChecked(true);
                    strtodebit = "re";
                case "NRO":
                    rb_sbnrodebit.setChecked(true);
                    strtodebit = "rd";
                    break;
                case "Other":
                    rbother.setChecked(true);
                    strtodebit = "ot";
                    break;
            }
        }
        switch (strtodebit)

        {
            case "SB":
                rbSb.setChecked(true);
                strtodebit = "sb";
                break;
            case "CA":
                rbCa.setChecked(true);
                strtodebit = "ca";
                break;
            case "CC":
                rbcc.setChecked(true);
                strtodebit = "cc";
                break;
            case "SB-NRE":
                rb_cbnre.setChecked(true);
                strtodebit = "re";
                break;
            case "NRO":
                rb_sbnrodebit.setChecked(true);
                strtodebit = "rd";
                break;
            case "Other":
                rbother.setChecked(true);
                strtodebit = "ot";
                break;
        }
        if (strfrequency == null) {
            strfrequency = "";
        }
        if (strfrequency.equals("Monthly")) {
            rbmonthly.setChecked(true);
            strfrequency = "m";
        } else if (strfrequency.equals(
                getString(R.string.quarterly))) {
            rbquiterly.setChecked(true);
            strfrequency = "q";
        } else if (strfrequency.equals("Yearly")) {
            rbyearly.setChecked(true);
            strfrequency = "y";
        } else if (strfrequency.equals("Half-Yearly")) {
            rb_halfyearly.setChecked(true);
            strfrequency = "h";
        } else if (strfrequency.equals(
                getString(R.string.as_amp_when_presented))) {
            rb_halfyearly.setChecked(true);
            strfrequency = "a";
        }
        if (strdebittype == null) {
            strdebittype = "";
            if (debittype.equals("Maximum Amount")) {
                rbmaxammount.setChecked(true);
                strdebittype = "m";
            } else if (debittype.equals("Fixed Amount")) {
                rbfixammount.setChecked(true);
                strdebittype = "f";
            }
        }


        if (strdebittype.equals("Maximum Amount")) {
            rbmaxammount.setChecked(true);
            strdebittype = "m";
        } else if (strdebittype.equals(("Fixed Amount"))) {
            rbfixammount.setChecked(true);
            strdebittype = "f";
        }
        edtauthorize.setText(entityname);
        chcancel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    edttodate.setText("");
                    edttodate.setError("", null);
                } else {
                    edttodate.setError(homeActivity.getString(R.string.fillDate));
                }

            }
        });

        edtIfsc.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = edtIfsc.getText().toString().toLowerCase(Locale.getDefault());
                edtmicr.setEnabled(false);
                if (text.length() < 1) {
                    edtmicr.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }
        });

        edtmicr.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = edtmicr.getText().toString().toLowerCase(Locale.getDefault());
                edtIfsc.setEnabled(false);

                if (text.length() < 1) {
                    edtIfsc.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

        edttodate.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                chcancel.setChecked(false);
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                Calendar cc = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final String getCurrentDateTime = sdf.format(cc.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                strmydate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                Date date2 = null;
                                Date date1 = null;
                                try {
                                    date1 = sdf.parse(changeDateFormatToServerFormat(edtfromdate.getText().toString()));
                                    date2 = sdf.parse(strmydate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (edtfromdate.getText().toString().equals("") || edtfromdate.getText().toString() == null) {
                                    edttodate.setText(changeDateFormatToUserFormat(strmydate));
                                    edttodate.setError(null, null);
                                } else {
                                    if (date1.compareTo(date2) < 0) {
                                        edttodate.setText(changeDateFormatToUserFormat(strmydate));
                                        edttodate.setError(null, null);
                                    } else {
                                        edttodate.setError("To Date should be greater than From Date");
                                    }
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }

        });
        edtfromdate.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                Calendar cc = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final String getCurrentDateTime = sdf.format(cc.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                strmydate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                Date date2 = null;
                                Date date1 = null;
                                try {
                                    date1 = sdf.parse(changeDateFormatToServerFormat(edttodate.getText().toString()));
                                    date2 = sdf.parse(strmydate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (edttodate.getText().toString().equals("") || edttodate.getText().toString() == null) {
                                    edtfromdate.setText(changeDateFormatToUserFormat(strmydate));
                                    edtfromdate.setError(null, null);
                                } else {
                                    if (date1.compareTo(date2) < 0) {
                                        edtfromdate.setError("From Date should be lesser than To Date");
                                    } else {
                                        edtfromdate.setText(changeDateFormatToUserFormat(strmydate));
                                        edtfromdate.setError(null, null);
                                    }
                                }


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        edtmandate.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                Calendar cc = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


                final String getCurrentDateTime = sdf.format(cc.getTime());
                final String getMyTime = "05/19/2016 ";

                // Date date1=null,date2=null;

                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                strmydate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                Date date2 = null;
                                Date date1 = null;
                                try {
                                    date1 = sdf.parse(getCurrentDateTime);
                                    date2 = sdf.parse(strmydate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (date1.compareTo(date2) < 0) {
                                    edtmandate.setText(changeDateFormatToUserFormat(strmydate));
                                } else {
                                    edtmandate.setText(changeDateFormatToUserFormat(strmydate));
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        Sponserbankdialogadapter adapter = new Sponserbankdialogadapter(HomeActivity.this, spbanklist);

        spin_sponsorbank.setAdapter(adapter);

        spin_sponsorbank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strsponsorbank = spbanklist.get(i).getSpname();
                spin_utilitybank.setText(spbanklist.get(i).getSpid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvbankname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtIfsc.getError() != null) {
                    edtIfsc.setError(null, null);
                }
                banknamedialog();

            }
        });
        tv_physical.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (tv_Enach.getVisibility() == View.VISIBLE) {
                    showAlertForPhysicalTwoButton(getString(R.string.proceed_with_physical));
                } else {
                    getPhysicalData(mandateId);
                }
            }
        });

        mandateId2 = preferance.getInstance(HomeActivity.this).

                get_mandate_id();
        tv_Enach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_physical.getVisibility() == View.VISIBLE) {
                    showAlertForTwoButton(getString(R.string.proceed_with_enach));
                } else {
                    String maxAmount = preferance.getInstance(homeActivity).getMaxAmount();
                    if (maxAmount == null) {
                        maxAmount = "";
                    }
                    Float maxAmoutFloat = Float.parseFloat(maxAmount);
                    Float editTextAMout = Float.parseFloat(edtamount.getText().toString());
                    if (editTextAMout <= maxAmoutFloat) {
                        if (edtnameaccholder.getText().toString().equals("")) {
                            if (edtnameaccholder3.getText().toString().equals("")) {
                                if (!edttodate.getText().toString().equalsIgnoreCase("")) {
                                    getMandateData(mandateId);
                                } else {
                                    if (toDateMandatory.equals("False")) {
                                        getMandateData(mandateId);
                                    } else {
                                        showEnashAlertDialog(mandateId, "True");
                                    }
                                }
                            }
                        } else {
                            showEnashAlertDialog(mandateId, "True");
                        }
                    } else {
                        showEnashAlertDialog(mandateId, "True");
                    }
                }
            }
        });

        if (!chcancel.isEnabled()) {
            chcancel.setChecked(false);
            edttodate.setError("", null);
        } else {
            chcancel.setChecked(true);
        }
    }


    private void showAlertForPhysicalTwoButton(String message) {
        AlertDialog alertDialog = null;
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
        alertDialog = new AlertDialog.Builder(
                homeActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();

        alertDialog.setMessage(message);

        // Setting Icon to Dialog

        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                getPhysicalData(mandateId);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void showAlertForTwoButton(String message) {
        AlertDialog alertDialog = null;
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
        alertDialog = new AlertDialog.Builder(
                homeActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();

        alertDialog.setMessage(message);

        // Setting Icon to Dialog

        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String maxAmount = preferance.getInstance(homeActivity).getMaxAmount();
                if (maxAmount == null) {
                    maxAmount = "";
                }
                Float maxAmoutFloat = Float.parseFloat(maxAmount);
                Float editTextAMout = Float.parseFloat(edtamount.getText().toString());
                if (editTextAMout <= maxAmoutFloat) {
                    if (edtnameaccholder.getText().toString().equals("")) {
                        if (edtnameaccholder3.getText().toString().equals("")) {
                            if (!edttodate.getText().toString().equalsIgnoreCase("")) {
                                getMandateData(mandateId);
                            } else {
                                showEnashAlertDialog(mandateId, "True");
                            }
                        }
                    } else {
                        showEnashAlertDialog(mandateId, "True");
                    }
                } else {
                    showEnashAlertDialog(mandateId, "True");
                }
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void tabButtonCLick() {
        imageViewNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                intent.putExtra("strtem", "4");
                intent.putExtra("mandateid", "");
                intent.putExtra("AddNew", true);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);

            }
        });


        img_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menupop();

            }
        });

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GetmandateActivity.activity == null) {
                    Intent intent = new Intent(HomeActivity.this, GetmandateActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else {
                    GetmandateActivity.getmandate(GetmandateActivity.count);
                    finish();
                    overridePendingTransition(0, 0);
                }

                // finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri contentURI = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bitmap.recycle();
                String final_path = Base64.encodeToString(byteArray, Base64.DEFAULT);
                byte[] imageBytes = Base64.decode(final_path, Base64.DEFAULT);
                String path = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                uploadimage(path, mandateId);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(homeActivity, "Failed!", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void openSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    private void setEmailLisners() {
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        edtemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!edtemail.getText().toString().matches(emailPattern) && edtemail.getText().toString().length() > 0) {
                    if (!hasFocus) {
                        edtemail.setText("");
                        edtemail.setError(null, null);
                    }
                }

            }
        });
        edtemail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!edtemail.getText().toString().matches(emailPattern) && s.length() > 0) {
                    edtemail.setError("Invalid Email");

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    @SuppressLint({"RestrictedApi", "ResourceType"})
    private void setPreferenceAndIntentData() {
        edtnameaccholder.setText(Customername2);
        edtnameaccholder3.setText(Customername3);
        edtreference2.setText(Reference2);
        edtphonenumber.setText(PhoneNumber);
        edtemail.setText(Email);
        edtmicr.setText(strmicr);

        edtprimaryholder.setText(straccholder1);
        edtrefference.setText(strreference1);
        if (stramount == null) {
            stramount = "";
        }
        edtamount.setText(stramount);
        tv_download.setVisibility(View.GONE);

        if (Enach == null) {
            Enach = "";
        }
        if (xmlpath == null) {
            xmlpath = "";
        }


/// TO check Debit Type Enable Disable
        setEnabledOrDisabled();

        if (isEMandate.equals("False")) {
            tv_Enach.setVisibility(View.GONE);
        } else {
            if (doneEmandate.equalsIgnoreCase("True")) {
                if (xmlpath.equalsIgnoreCase("")) {
                    textViewTitle.setText(getString(R.string.editMandateForm));
                    tv_submitmandate.setText(getString(R.string.edit));
                    tv_upload.setVisibility(View.GONE);
                    tv_download.setVisibility(View.GONE);
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.VISIBLE);
                } else {
                    tv_submitmandate.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                    tv_download.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_physical.setVisibility(View.GONE);
                    textViewTitle.setText(getString(R.string.mandate_form));
                    disabledAllVIews();
                }
            }
        }

        if (addNew) {
            textViewTitle.setText(getString(R.string.add_new_mandate));
            img_edit.setVisibility(View.VISIBLE);
            img_power.setVisibility(View.VISIBLE);
        } else {
            textViewTitle.setText(getString(R.string.mandate_form));
            img_edit.setVisibility(View.VISIBLE);
            img_power.setVisibility(View.VISIBLE);
        }
        if (strtem == null) {
            strtem = "";
        }
        if (strtem.equalsIgnoreCase("2")) {
            textViewTitle.setText(getString(R.string.editMandateForm));
            img_edit.setVisibility(View.VISIBLE);
            tv_submitmandate.setText(getString(R.string.update));
            img_power.setVisibility(View.VISIBLE);

        }
        //  edttodate.setText(Todate);

        if (textViewTitle.getText().toString().equalsIgnoreCase(getString(R.string.editMandateForm))) {
            edtfromdate.setText(FromDate);
            edttodate.setText(Todate);
            if (Todate == null) {
                Todate = "";
            }
            if (Todate.equalsIgnoreCase("") || Todate == null) {
                chcancel.setChecked(true);
            } else {
                chcancel.setChecked(false);
            }
        } else {
            edttodate.setText("");
            edtfromdate.setText("");
        }

        imageViewNew.setVisibility(View.GONE);
        if (strtem.equals("2")) {
            tvbankname.setText(strbankname);
        } else {
            tvbankname.setText(banklist.get(0).getNbank_name());
        }
        if (successref != null) {
            if (successref.equals("2")) {
                tv_upload.setVisibility(View.VISIBLE);
                tv_submitmandate.setText(getString(R.string.edit));
                disabledAllVIews();
            }
        }
        edtIfsc.setText(strifscc);
        edtaccountnumber.setText(strbankaccountnumber);

        strdateofmandate = edtmandate.getText().toString();

        Calendar cc = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        final String getCurrentDateTime = sdf.format(cc.getTime());

        edtmandate.setText(getCurrentDateTime);
        debittype = preferance.getInstance(HomeActivity.this).get_debittype();
        entityname = preferance.getInstance(HomeActivity.this).get_entityname();
        FrequencyType = preferance.getInstance(HomeActivity.this).get_frequencytype();
        IsMandateEdit = preferance.getInstance(HomeActivity.this).get_ismandateedit();
        IsRefrenceCheck = preferance.getInstance(HomeActivity.this).get_referencecheck();
        IsRefrenceEdit = preferance.getInstance(HomeActivity.this).get_referenceedit();
        IsRefrenceNumeric = preferance.getInstance(HomeActivity.this).get_referencenumaric();
        IsShowMandateMode = preferance.getInstance(HomeActivity.this).get_showmandatemode();
        IsliveInNach = preferance.getInstance(HomeActivity.this).get_islivenach();
        Mandate = preferance.getInstance(HomeActivity.this).get_mandate();
        ModeOfPayment = preferance.getInstance(HomeActivity.this).get_modeofpayment();
        PeriodType = preferance.getInstance(HomeActivity.this).get_periodtype();
        ToDebit = preferance.getInstance(HomeActivity.this).get_todebit();

        if (IsRefrenceNumeric.equals("True")) {
            edtrefference.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            edtrefference.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        if (mandateId == null) {
            mandateId = "";
        }


        if (status.equals("Account Validated")) {
            if (isEMandate.equals("False")) {
                tv_physical.setVisibility(View.VISIBLE);
                tv_Enach.setVisibility(View.GONE);
                tv_download.setVisibility(View.GONE);
                tv_upload.setVisibility(View.GONE);
                tv_submitmandate.setText(homeActivity.getString(R.string.update));

            } else {
                if (isEnach == true) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.VISIBLE);
                    tv_download.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);

                } else if (isPhysical == true) {
                    tv_physical.setVisibility(View.VISIBLE);
                    tv_Enach.setVisibility(View.GONE);
                    tv_download.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                } else {
                    tv_physical.setVisibility(View.VISIBLE);
                    tv_Enach.setVisibility(View.VISIBLE);
                    tv_download.setVisibility(View.GONE);
                    tv_upload.setVisibility(View.GONE);
                }
                tv_submitmandate.setText(homeActivity.getString(R.string.update));

            }

        }
        try {
            String amountInInt = stramount.replace(".00", "");
            long amount = Long.parseLong(amountInInt);
            if (amount > 10000000) {
                textView_amount.setVisibility(View.GONE);
                edtamount.setError("Amount should not be greater than 1 Crore");
            } else {
                textView_amount.setVisibility(View.VISIBLE);
                edtamount.setError(null, null);
                newString = EnglishNumberToWords.convert(amount);
                textView_amount.setText(String.format("%s Only", newString));
            }

        } catch (Exception e) {
            textView_amount.setVisibility(View.GONE);
            textView_amount.setText(String.format("%s Only", newString));
        }

    }

    @SuppressLint("RestrictedApi")
    private void setEnabledOrDisabled() {

        if (Build.VERSION.SDK_INT >= 21) {
            colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled}, //disabled
                            new int[]{android.R.attr.state_enabled},//enabled
                            new int[]{-android.R.attr.state_checked}//unchecked

                    },
                    new int[]{
                            R.color.LTGRAY //disabled
                            , R.color.colorblack //enabled
                            , R.color.LTGRAY}//unchecked

            );
        }
        /// TO check DebitTypeList  Enable Disable

        if (DebitTypeList.get(0).getIsenable().equals("True")) {
            rbfixammount.setEnabled(true);
        } else {
            rbfixammount.setEnabled(false);
            rbfixammount.setSupportButtonTintList(colorStateList);//set the color tint list
            rbfixammount.setTextColor(getResources().getColor(R.color.darker_gray));
        }
        if (DebitTypeList.get(1).getIsenable().equals("True")) {
            rbmaxammount.setEnabled(true);
        } else {
            rbmaxammount.setEnabled(false);
            rbmaxammount.setSupportButtonTintList(colorStateList);//set the color tint list
            rbmaxammount.setTextColor(getResources().getColor(R.color.darker_gray));
        }

        /// TO check Frequency  Enable Disable

        if (FrequencyList.get(0).getIsenable().equals("True")) {
            rbmonthly.setEnabled(true);
        } else {
            rbmonthly.setEnabled(false);
            rbmonthly.setSupportButtonTintList(colorStateList);//set the color tint list
            rbmonthly.setTextColor(getResources().getColor(R.color.darker_gray));
        }
        if (FrequencyList.get(1).getIsenable().equals("True")) {
            rbquiterly.setEnabled(true);
        } else {
            rbquiterly.setEnabled(false);
            rbquiterly.setSupportButtonTintList(colorStateList);//set the color tint list
            rbquiterly.setTextColor(getResources().getColor(R.color.darker_gray));
        }
        if (FrequencyList.get(2).getIsenable().equals("True")) {
            rb_halfyearly.setEnabled(true);
        } else {
            rb_halfyearly.setEnabled(false);
            rb_halfyearly.setSupportButtonTintList(colorStateList);//set the color tint list
            rb_halfyearly.setTextColor(getResources().getColor(R.color.darker_gray));
        }
        if (FrequencyList.get(3).getIsenable().equals("True")) {
            rbyearly.setEnabled(true);
        } else {
            rbyearly.setEnabled(false);
            rbyearly.setSupportButtonTintList(colorStateList);//set the color tint list
            rbyearly.setTextColor(getResources().getColor(R.color.darker_gray));
        }
        if (FrequencyList.get(4).getIsenable().equals("True")) {
            rb_aspresented.setEnabled(true);
        } else {
            rb_aspresented.setEnabled(false);
            rb_aspresented.setSupportButtonTintList(colorStateList);//set the color tint list
            rb_aspresented.setTextColor(getResources().getColor(R.color.darker_gray));
        }

        //TO check PeriodsList  Enable Disable

        if (PeriodsList.get(0).getIsenable().equals("True")) {
            edttodate.setEnabled(true);
        } else {
            edttodate.setEnabled(false);
            edttodate.setBackground(getResources().getDrawable(R.drawable.disable_gray));//set the color tint list
        }
        if (PeriodsList.get(1).getIsenable().equals("True")) {
            chcancel.setEnabled(true);
            if (edttodate.getText().toString().equals("")) {
                chcancel.setChecked(true);
            } else {
                chcancel.setChecked(false);
            }
        } else {
            chcancel.setEnabled(false);
            chcancel.setChecked(false);
            chcancel.setSupportButtonTintList(colorStateList);//set the color tint list
            chcancel.setTextColor(getResources().getColor(R.color.darker_gray));
        }


        //TO check ToDebitList  Enable Disable
        if (ToDebitList.get(0).getIsenable().equals("True")) {
            rbSb.setEnabled(true);
        } else {
            rbSb.setEnabled(false);
            rbSb.setSupportButtonTintList(colorStateList);//set the color tint list
            rbSb.setTextColor(getResources().getColor(R.color.darker_gray));
        }
        if (ToDebitList.get(1).getIsenable().equals("True")) {
            rbCa.setEnabled(true);
        } else {
            rbCa.setEnabled(false);
            rbCa.setSupportButtonTintList(colorStateList);//set the color tint list
            rbCa.setTextColor(getResources().getColor(R.color.darker_gray));
        }
        if (ToDebitList.get(2).getIsenable().equals("True")) {
            rbcc.setEnabled(true);
        } else {
            rbcc.setEnabled(false);
            rbcc.setSupportButtonTintList(colorStateList);//set the color tint list
            rbcc.setTextColor(getResources().getColor(R.color.darker_gray));
        }
        if (ToDebitList.get(3).getIsenable().equals("True")) {
            rb_cbnre.setEnabled(true);
        } else {
            rb_cbnre.setEnabled(false);
            rb_cbnre.setSupportButtonTintList(colorStateList);//set the color tint list
            rb_cbnre.setTextColor(getResources().getColor(R.color.darker_gray));
        }
        if (ToDebitList.get(4).getIsenable().equals("True")) {
            rb_sbnrodebit.setEnabled(true);
        } else {
            rb_sbnrodebit.setEnabled(false);
            rb_sbnrodebit.setSupportButtonTintList(colorStateList);//set the color tint list
            rb_sbnrodebit.setTextColor(getResources().getColor(R.color.darker_gray));
        }
        if (ToDebitList.get(5).getIsenable().equals("True")) {
            rbother.setEnabled(true);
        } else {
            rbother.setEnabled(false);
            rbother.setSupportButtonTintList(colorStateList);//set the color tint list
            rbother.setTextColor(getResources().getColor(R.color.darker_gray));
        }
    }

    public static String changeDateFormatToUserFormat(String dateToChange) {
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat inputFormat;
        inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat outputFormat;
        outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateToChange);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String changeDateFormatToServerFormat(String dateToChange) {
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        String str = null;
        if (dateToChange == "") {
            return str = "";
        } else {
            try {
                date = inputFormat.parse(dateToChange);
                str = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return str;
        }
    }


    private void getPreferenceAndIntentData() {
        textViewTitle = findViewById(R.id.textViewTitle);
        banklist = preferance.getInstance(getApplicationContext()).getbankArrayList("bank");
        spbanklist = preferance.getInstance(getApplicationContext()).getsponsorArrayList("sponsor");
        utlitybanklist = preferance.getInstance(getApplicationContext()).getutilityArrayList("utility");

        DebitTypeList = preferance.getInstance(getApplicationContext()).getDebitTypeList("DebitTypeList");
        PeriodsList = preferance.getInstance(getApplicationContext()).getPeriodsList("PeriodsList");
        ToDebitList = preferance.getInstance(getApplicationContext()).getToDebitList("ToDebitList");
        FrequencyList = preferance.getInstance(getApplicationContext()).getFrequencyList("FrequencyList");


        Intent intent = getIntent();
        status = intent.getStringExtra("Status");
        if (status == null) {
            status = "";
        }
        addNew = intent.getBooleanExtra("AddNew", false);
        String Dateofmandate = intent.getStringExtra("Dateofmandate");
        edtmandate.setText(Dateofmandate);
        Enach = intent.getStringExtra("Enach");
        xmlpath = intent.getStringExtra("xmlpath");

        doneEmandate = intent.getStringExtra("doneEmandate");
        if (doneEmandate == null) {
            doneEmandate = "";
        }
        if (doneEmandate.equalsIgnoreCase("True")) {
            isEnach = true;
        } else {
            isEnach = false;
        }

        Isphysical = intent.getStringExtra("Isphysical");
        if (Isphysical == null) {
            Isphysical = "";
        }

        if (Isphysical.equalsIgnoreCase("True")) {
            isPhysical = true;
        } else {
            isPhysical = false;
        }
        if (isPhysical == true) {
            tv_physical.setVisibility(View.VISIBLE);
            tv_Enach.setVisibility(View.GONE);
        }
        if (isEnach == true) {
            if (isEMandate.equals("False")) {
                tv_physical.setVisibility(View.VISIBLE);
            } else {
                tv_physical.setVisibility(View.GONE);
                if (isEMandate.equals("False")) {
                    tv_Enach.setVisibility(View.GONE);
                } else {
                    tv_Enach.setVisibility(View.VISIBLE);
                }
            }
        }
        mandateId = intent.getStringExtra("mandateid");
        strreference1 = intent.getStringExtra("referenceno");
        straccholder1 = intent.getStringExtra("customername");
        stramount = intent.getStringExtra("AmountRupees");
        strbankname = intent.getStringExtra("BankName");
        strifscc = intent.getStringExtra("Code");
        if (strifscc == null) {
            strifscc = "";
        }
        if (strifscc.matches("^[0-9]+$")) {
            strmicr = strifscc;
            strifscc = "";
        } else {
            strmicr = "";
        }
        strdebittype = intent.getStringExtra("DebitType");
        strfrequency = intent.getStringExtra("FrequencyType");
        strmandatemode = intent.getStringExtra("MandateMode");
        strsponsorbank = intent.getStringExtra("SponsorbankCode");
        strtodebit = intent.getStringExtra("ToDebit");
        strutility = intent.getStringExtra("UtilityCode");
        strbankaccountnumber = intent.getStringExtra("AcNo");
        successref = intent.getStringExtra("successref"); ///change
        strtem = intent.getStringExtra("strtem");
        FromDate = intent.getStringExtra("FromDate");
        Todate = intent.getStringExtra("ToDate");
        Customername3 = intent.getStringExtra("Customername3");
        Reference2 = intent.getStringExtra("Reference2");
        PhoneNumber = intent.getStringExtra("PhoneNumber"); ///change
        Email = intent.getStringExtra("Email");
        Customername2 = intent.getStringExtra("Customername2");
    }

    @SuppressLint("CutPasteId")
    private void getUiID() {
        img_power = findViewById(R.id.img_power);
        textView_amount = findViewById(R.id.textView_amount);

        img_edit = findViewById(R.id.img_edit);
        // imageNew = findViewById(R.id.imageViewNew);
        radiotodebit = findViewById(R.id.rdg_debit);
        rgbfrequency = findViewById(R.id.rdg_frequency);

        spin_sponsorbank = findViewById(R.id.spin_sponsorbank);
        spin_utilitybank = findViewById(R.id.spin_utilitybank);

        rb_none = findViewById(R.id.rb_none);
        rb_firstemipdc = findViewById(R.id.rb_firstemipdc);
        rb_npdl = findViewById(R.id.rb_npdl);
        rb_fullpdc = findViewById(R.id.rb_fullpdc);

        rbSb = findViewById(R.id.rb_sb);
        rbcc = findViewById(R.id.rb_cc);
        rbCa = findViewById(R.id.rb_ca);
        rbother = findViewById(R.id.rb_otherdebit);
        rbmonthly = findViewById(R.id.rb_monthly);
        rbquiterly = findViewById(R.id.rb_quetrly);
        rbyearly = findViewById(R.id.rb_yearly);
        rbfixammount = findViewById(R.id.rb_fixidammount);
        rbmaxammount = findViewById(R.id.rb_maximumamount);
        rb_halfyearly = findViewById(R.id.rb_halfyearly);
        rb_aspresented = findViewById(R.id.rb_aspresented);

        rb_sbnrodebit = findViewById(R.id.rb_sbnrodebit);
        rb_cbnre = findViewById(R.id.rb_cbnre);
        tv_Enach = findViewById(R.id.tv_Enach);
        tv_physical = findViewById(R.id.tv_physical);

        tv_mandatemodename = findViewById(R.id.tv_mandatemodename);

        rgbdebittype = findViewById(R.id.rdg_debittype);
        rgbshowmandate = findViewById(R.id.rdg_mandatemode);
        edtmandate = findViewById(R.id.edt_dateofmandate);
        // utilitycode=findViewById(R.id.edt_utilitycode);
        edtauthorize = findViewById(R.id.edt_authorize);
        edtaccountnumber = findViewById(R.id.edt_bankaccnumber);
        edtIfsc = findViewById(R.id.edt_ifsc);
        edtmicr = findViewById(R.id.edt_micr);
        edtamount = findViewById(R.id.edt_amount);
        edtrefference = findViewById(R.id.edt_reference1);
        edtreference2 = findViewById(R.id.edt_reference2);
        edtphonenumber = findViewById(R.id.edt_phonenumber);
        edtemail = findViewById(R.id.edt_email);
        edtfromdate = findViewById(R.id.edt_fromdate);
        edttodate = findViewById(R.id.edt_todate);

        edtprimaryholder = findViewById(R.id.edt_primaryaccholder);
        edtnameaccholder = findViewById(R.id.edt_nameaccholder);
        edtnameaccholder3 = findViewById(R.id.edt_nameaccholder3);
        tv_submitmandate = findViewById(R.id.tv_submitmandate);
        tv_upload = findViewById(R.id.tv_upload);
        chcancel = findViewById(R.id.ch_cancel);
        imageViewNew = findViewById(R.id.imageViewNew);
        // tv_sponsorbank=findViewById(R.id.tv_sponsorbank);
        tvbankname = findViewById(R.id.tv_bankname);
        tv_submitmandate = findViewById(R.id.tv_submitmandate);

        tv_download = findViewById(R.id.tv_download);
    }

    @SuppressLint("RtlHardcoded")
    public void menupop() {
        TextView tvlogout, tvchangepassword;
        Dialog dialogLogout = new Dialog(HomeActivity.this);
        dialogLogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogout.setContentView(R.layout.menulayout);

        tvlogout = dialogLogout.findViewById(R.id.tv_menulogout);
        tvchangepassword = dialogLogout.findViewById(R.id.tv_menuchangepassword);
        Window window = dialogLogout.getWindow();

        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP | RIGHT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.y = 90;
        wlp.x = 10;
        window.setAttributes(wlp);

        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Log Out!")
                        .setContentText("Are you sure?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                preferance.getInstance(getApplicationContext()).clearuserSession();
                                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(0, 0);
                                if (GetmandateActivity.activity != null)
                                    GetmandateActivity.activity.finish();
                                overridePendingTransition(0, 0);

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
        });

        tvchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changepassword();
            }
        });
        if (dialogLogout != null) {
            dialogLogout.show();
        }
    }

    public void changepassword() {
        final String userid = preferance.getInstance(getApplicationContext()).getStudentId();
        final String oldPassword = preferance.getInstance(getApplicationContext()).get_password();

        final EditText edtoldpassword, edtnewpassword, edtconfirmpassword;
        Button tvChangepass;

        Dialog dialogChangePassword = new Dialog(HomeActivity.this);
        dialogChangePassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChangePassword.setContentView(R.layout.changepassword);

        Window window = dialogChangePassword.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        edtoldpassword = dialogChangePassword.findViewById(R.id.edt_oldpassword);
        edtnewpassword = dialogChangePassword.findViewById(R.id.edt_oldnewpassword);
        edtconfirmpassword = dialogChangePassword.findViewById(R.id.edt_confirmpassword);

        tvChangepass = dialogChangePassword.findViewById(R.id.tv_changepassword);

        tvChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String stroldpassword, newpassword, confirmpassword;

                stroldpassword = edtoldpassword.getText().toString();
                newpassword = edtnewpassword.getText().toString();
                confirmpassword = edtconfirmpassword.getText().toString();

                if (stroldpassword.equals("")) {
                    edtoldpassword.setError("fill old password");
                } else if (!stroldpassword.equals(oldPassword)) {
                    edtoldpassword.setError("wrong password");
                } else if (newpassword.equals("")) {
                    edtnewpassword.setError("fill password");
                } else if (confirmpassword.equals("")) {
                    edtconfirmpassword.setError("fill confirm password");
                } else if (!newpassword.equals(confirmpassword)) {
                    edtconfirmpassword.setError("password dosn't match");
                } else {
                    changepassword(userid, newpassword);
                }
            }

        });
        dialogChangePassword.show();
    }

    public void changepassword(String UserId, String Password) {

        final ProgressDialog pb = ProgressDialog.show(this, "Loading", "Please wait...", false, false);
        pb.show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("UserId", UserId);
        params.put("Password", Password);
        String appId = preferance.getInstance(homeActivity).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.changepassword, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                pb.dismiss();
                try {

                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    CustomDialogForMessages.showMessageAlert(HomeActivity.this, loginData.getString("status"), loginData.getString("message"));

                } catch (JSONException e) {

                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(HomeActivity.this, getString(R.string.failure), e.getCause().getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
        // MyApplication.getInstance().addToReqQueue(req);
    }


    @Override
    public void onBackPressed() {
        if (count == 1 || count == 0) {
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
        } else {
            count--;
            finish();
            overridePendingTransition(0, 0);

        }
//
    }

    public void sponserbankdialog() {

        Sponserbankdialogadapter adapter = new Sponserbankdialogadapter(HomeActivity.this, spbanklist);
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sponserbankdialog);

        ListView listsponser = dialog.findViewById(R.id.li_sponserbanklist);

        listsponser.setAdapter(adapter);

        listsponser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                strsponsorbank = spbanklist.get(i).getSpname();

                dialog.dismiss();

            }
        });

        dialog.show();
    }


    public void banknamedialog() {
        banklist.clear();
        banklist = preferance.getInstance(getApplicationContext()).getbankArrayList("bank");
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bankname_dialog);
        dialog.getWindow().setBackgroundDrawableResource(R.color.white);
        final ListView listsponser = dialog.findViewById(R.id.li_banknamelist);

        listsponser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                strbankname = banklist.get(i).getNbank_name();
                if (tvbankname.getText().toString().trim().length() > 0) {
                    tvbankname.setText("");
                    tvbankname.setText(strbankname);
                }
                tvbankname.setText("");
                tvbankname.setText(strbankname);
                dialog.dismiss();

            }
        });

        final EditText edtname = dialog.findViewById(R.id.edt_searchbankname);
        final Banknameadapter adapter = new Banknameadapter(HomeActivity.this, banklist);

        listsponser.setAdapter(adapter);

        edtname.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = edtname.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
        dialog.show();
    }

    public void savemandate() {
        if (mandateId == null || mandateId.equalsIgnoreCase("null")) {
            mandateId = "";
        }

        final ProgressDialog pb = ProgressDialog.show(this, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mandateId", mandateId);
        params.put("SponsorCode", strsponsorbank);
        params.put("UtilityCode", strutility);
        params.put("DebitType", strdebittype);
        params.put("Frequency", strfrequency);
        params.put("UserId", userId);
        params.put("ToDebit", strtodebit);
        params.put("AcNo", strbankaccountnumber);
        params.put("BankName", strbankname);
        params.put("IFSC", strifscc);
        params.put("MICR", strmicr);
        params.put("AmountRupees", stramount);
        params.put("Refrence1", strreference1);
        params.put("Refrence2", strreference2);
        params.put("PhNumber", strphonenyumber);
        params.put("EmailId", stremail);
        params.put("From", strfromdate);
        params.put("To", strtodate);
        params.put("Customer1", straccholder1);
        params.put("Customer2", straccholder2);
        params.put("Customer3", straccholder3);
        params.put("DateOnMandate", strdateofmandate);
        params.put("MandateMode", modeValue);
        String appId = preferance.getInstance(homeActivity).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.valmandate, new JSONObject(
                params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();

                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    String status = loginData.getString("status");
                    String message = loginData.getString("message");
                    String IsliveInNach = loginData.getString("IsliveInNach");
                    if (mandateId.trim().equals("")) {
                        mandateId = loginData.getString("Id");
                    }
                    if (status.equals("Success")) {
                        sucessdialogForNameMisMatch(straccholder1, message, IsliveInNach, mandateId);
                    } else if (status.equals("FinalSuccess")) {
                        sucessdialogForNameMatched(straccholder1, message, IsliveInNach, mandateId);
                    } else if (status.equals("Refrence")) {
                        if (IsRefrenceCheck.equals("False")) {
                            if (IsRefrenceEdit.equals("True")) {
                                JSONArray jsonarrary = loginData.getJSONArray("Mandate");
                                reflist.clear();
                                for (int i = 0; i < jsonarrary.length(); i++) {
                                    Refrencedata data = new Refrencedata();
                                    JSONObject obj1 = jsonarrary.getJSONObject(i);
                                    data.setReferenceno(obj1.getString("Refrence1"));
                                    data.setRefbankname(obj1.getString("BankName"));
                                    data.setRefifsc(obj1.getString("Code"));
                                    data.setRefaccontno(obj1.getString("AcNo"));
                                    data.setRefcustomername(obj1.getString("Customer1"));
                                    data.setRefdateofmandate(obj1.getString("DateOnMandate"));
                                    data.setRefcreatedby(obj1.getString("UserName"));
                                    data.setRefmandateid(obj1.getString("mandateid"));
                                    data.setRefammountruppe(obj1.getString("AmountRupees"));
                                    data.setRefcustomer1(obj1.getString("Customer1"));
                                    data.setRefcustomer2(obj1.getString("Customer2"));
                                    data.setRefcustomer3(obj1.getString("Customer3"));
                                    data.setRefdiebittype(obj1.getString("DebitType"));
                                    data.setRefemail(obj1.getString("EmailId"));
                                    data.setReffrequency(obj1.getString("FrequencyType"));
                                    data.setReffrom(obj1.getString("FromDate"));
                                    data.setRefmandatemode(obj1.getString("MandateMode"));
                                    data.setRefphnenumber(obj1.getString("PhoneNumber"));
                                    data.setRefsponsorcode(obj1.getString("SponsorbankCode"));
                                    data.setReftodebit(obj1.getString("ToDebit"));
                                    data.setRefto(obj1.getString("Todate"));
                                    data.setRefutilitycode(obj1.getString("UtilityCode"));

                                    reflist.add(data);
                                }
                                referencelistdialog();
                            } else {
                                String strwarning = "This Reference no is already Exist.Contact to Admin.";
                                CustomDialogForMessages.showMessageAlert(HomeActivity.this, getString(R.string.warning), strwarning);

                            }
                        } else if (IsRefrenceCheck.equals("True")) {
                            showalertmg(status, message);
                        }

                    } else if (status.equals("failure")) {
                        if (message.equalsIgnoreCase("IFSC InValid")) {
                            edtIfsc.setError(message);
                            edtIfsc.requestFocus();
                        }
                        CustomDialogForMessages.showMessageAlert(HomeActivity.this, status, message);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
                pb.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        req.setRetryPolicy(new DefaultRetryPolicy(100000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }


    static String isliveInNach2;


    public static void sucessdialogForNameMatched(String straccholder1, final String message,
                                                  final String isliveInNach, final String newMandateid) {
        isliveInNach2 = isliveInNach;
        TextView tvnameasform, tvLengthform, tvNameasbank, tvLengthasbank;
        Button buttonProceed;
        ImageView imageViewClose;
        dialogFinalsuccess = new Dialog(homeActivity);
        dialogFinalsuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFinalsuccess.setContentView(R.layout.final_success_dialog);

        Window window = dialogFinalsuccess.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvnameasform = dialogFinalsuccess.findViewById(R.id.tv_nameasform);
        tvLengthform = dialogFinalsuccess.findViewById(R.id.tv_charecternumber);
        tvNameasbank = dialogFinalsuccess.findViewById(R.id.tv_nameasperbank);
        tvLengthasbank = dialogFinalsuccess.findViewById(R.id.tv_numberbankcharecters);
        buttonProceed = dialogFinalsuccess.findViewById(R.id.buttonProceed);
        imageViewClose = dialogFinalsuccess.findViewById(R.id.imageViewClose);

        final String str = String.valueOf(straccholder1.length());
        String str2 = String.valueOf(message.length());
        tvnameasform.setText(straccholder1);
        tvLengthform.setText(str);
        tvNameasbank.setText(message);
        tvLengthasbank.setText(str2);
        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isliveInNach.equals("True")) {
                    if (isEMandate.equals("False")) {
                        tv_physical.setVisibility(View.VISIBLE);
                        tv_submitmandate.setText(R.string.edit);
                        imageViewNew.setVisibility(View.VISIBLE);
                        disabledAllVIews();
                        dialogFinalsuccess.dismiss();
                    } else {
                        String maxAmount = preferance.getInstance(homeActivity).getMaxAmount();
                        if (maxAmount == null) {
                            maxAmount = "";
                        }
                        Float maxAmoutFloat = Float.parseFloat(maxAmount);
                        Float editTextAMout = Float.parseFloat(edtamount.getText().toString());
                        if (editTextAMout <= maxAmoutFloat) {
                            if (edtnameaccholder.getText().toString().equals("")) {
                                if (edtnameaccholder3.getText().toString().equals("")) {
                                    if (!edttodate.getText().toString().equalsIgnoreCase("")) {
                                        if (isEnach == true) {
                                            getMandateData(newMandateid);
                                        } else {
                                            showEnashAlertDialog(newMandateid, "True");
                                        }
                                    } else {
                                        showEnashAlertDialog(newMandateid, "True");
                                    }
                                }
                            } else {
                                showEnashAlertDialog(newMandateid, "True");
                            }
                        } else {
                            showEnashAlertDialog(newMandateid, isliveInNach);
                        }
                        dialogFinalsuccess.dismiss();
                    }
                } else if (isliveInNach.equals("False")) {
                    Toast.makeText(homeActivity, "Bank is not Live on ENACH", Toast.LENGTH_SHORT).show();
                    tv_upload.setVisibility(View.VISIBLE);
                    tv_submitmandate.setText(R.string.edit);
                    imageViewNew.setVisibility(View.VISIBLE);
                    disabledAllVIews();
                    dialogFinalsuccess.dismiss();

                }

            }
        });
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFinalsuccess.dismiss();
            }
        });

        dialogFinalsuccess.show();

    }

    public void showalertmg(String status, String msg) {
        if (msg.equalsIgnoreCase("Refrence no is already exists")) {
            edtrefference.setError("msg");
            edtrefference.requestFocus();
        }
        CustomDialogForMessages.showMessageAlert(HomeActivity.this, status, msg);

    }

    protected void checkPermission() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                HomeActivity.this, Manifest.permission.READ_CONTACTS)
                + ContextCompat.checkSelfPermission(
                HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage("Read Contacts and Write External" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                HomeActivity.this,
                                new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                ActivityCompat.requestPermissions(
                        HomeActivity.this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                if (
                        (grantResults.length > 0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                        ) {

                } else {

                }
            }
        }
    }


    public void referencelistdialog() {
        dialogrefrence = new Dialog(HomeActivity.this);
        dialogrefrence.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogrefrence.setContentView(R.layout.reference_dialog);
        dialogrefrence.setCanceledOnTouchOutside(false);
        RecyclerView rycref = dialogrefrence.findViewById(R.id.reyc_reflist);
        TextView tv_refcreatenew = dialogrefrence.findViewById(R.id.tv_refcreatenew);

        ImageButton img_referencecross = dialogrefrence.findViewById(R.id.img_referencecross);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rycref.setLayoutManager(mLayoutManager);
        rycref.setItemAnimator(new DefaultItemAnimator());

        ReferenceAdapter adapter = new ReferenceAdapter(HomeActivity.this, reflist, reftemplist);
        rycref.setAdapter(adapter);
        img_referencecross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogrefrence.dismiss();
            }
        });

        tv_refcreatenew.setVisibility(View.VISIBLE);
        tv_refcreatenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savemandateNew(0, "");
                HomeActivity.dialogrefrence.dismiss();
            }
        });
        dialogrefrence.show();
    }

    public void savemandateNew(int position, String mandateidd) {
        final String userId = preferance.getInstance(homeActivity).getStudentId();
        Refrencedata data = reftemplist.get(position);
        String mandateid = mandateidd;
        if (mandateid.equals("null")) {
            mandateid = "";
        }

        String SponsorCode = data.getRefsponsorcode();
        if (SponsorCode.equals("null")) {
            SponsorCode = "";
        }
        String UtilityCode = data.getRefutilitycode();
        if (UtilityCode.equals("null")) {
            UtilityCode = "";
        }
        String DebitType = data.getRefdiebittype();
        if (DebitType.equals("null")) {
            DebitType = "";
        }
        String Frequency = data.getReffrequency();
        if (Frequency.equals("null")) {
            Frequency = "";
        }

        String ToDebit = data.getReftodebit();
        if (ToDebit.equals("null")) {
            ToDebit = "";
        }
        String AcNo = data.getRefaccontno();
        if (AcNo.equals("null")) {
            AcNo = "";
        }
        String BankName = data.getRefbankname();
        if (BankName.equals("null")) {
            BankName = "";
        }
        String IFSC = data.getRefifsc();
        if (IFSC.equals("null")) {
            IFSC = "";
        }
        String MICR = data.getRefmicr();
        if (MICR.equals("null")) {
            MICR = "";
        }
        String AmountRupees = data.getRefammountruppe();
        if (AmountRupees.equals("null")) {
            AmountRupees = "";
        }
        String Refrence1 = data.getRefrefrence1();
        if (Refrence1.equals("null")) {
            Refrence1 = "";
        }
        String Refrence2 = data.getRefreference2();
        if (Refrence2.equals("null")) {
            Refrence2 = "";
        }
        String PhNumber = data.getRefphnenumber();
        if (PhNumber.equals("null")) {
            PhNumber = "";
        }
        String EmailId = data.getRefemail();
        if (EmailId.equals("null")) {
            EmailId = "";
        }
        String From = data.getReffrom();
        if (From.equals("null")) {
            From = "";
        }
        String To = data.getRefto();
        if (To.equals("null")) {
            To = "";
        }
        String Customer1 = data.getRefcustomer1();
        if (Customer1.equals("null")) {
            Customer1 = "";
        }
        String Customer2 = data.getRefcustomer2();
        if (Customer2.equals("null")) {
            Customer2 = "";
        }
        String Customer3 = data.getRefcustomer3();
        if (Customer3.equals("null")) {
            Customer3 = "";
        }
        String DateOnMandate = data.getRefdateofmandate();
        if (DateOnMandate.equals("null")) {
            DateOnMandate = "";
        }
        final ProgressDialog pb = ProgressDialog.show(homeActivity, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mandateId", mandateid);
        params.put("SponsorCode", SponsorCode);
        params.put("UtilityCode", UtilityCode);
        params.put("DebitType", DebitType);
        params.put("Frequency", Frequency);
        params.put("UserId", userId);
        params.put("ToDebit", ToDebit);
        params.put("AcNo", AcNo);
        params.put("BankName", BankName);
        params.put("IFSC", IFSC);
        params.put("MICR", MICR);
        params.put("AmountRupees", AmountRupees);
        params.put("Refrence1", Refrence1);
        params.put("Refrence2", Refrence2);
        params.put("PhNumber", PhNumber);
        params.put("EmailId", EmailId);
        params.put("From", From);
        params.put("To", To);
        params.put("Customer1", Customer1);
        params.put("Customer2", Customer2);
        params.put("Customer3", Customer3);
        params.put("DateOnMandate", DateOnMandate);
        params.put("MandateMode", "");
        String appId = preferance.getInstance(homeActivity).getAPPID();
        params.put("AppId", appId);
        final String finalCustomer = Customer1;
        final String finalMandateid = mandateid;
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.valaccontfinal, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));

                    String status = loginData.getString("status");
                    String strmsg = loginData.getString("message");
                    String IsliveInNach = loginData.getString("IsliveInNach");
                    HomeActivity.mandateId = loginData.getString("Id");

                    if (status.equals("Success")) {
                        HomeActivity.sucessdialogForNameMisMatch(finalCustomer, strmsg, IsliveInNach, finalMandateid);
                    } else if (status.equals("FinalSuccess")) {
                        HomeActivity.sucessdialogForNameMatched(finalCustomer, strmsg, IsliveInNach, finalMandateid);
                    } else {
                        CustomDialogForMessages.showMessageAlert(homeActivity, status, strmsg);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(homeActivity, getString(R.string.failure), e.getCause().getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                    Toast.makeText(homeActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(homeActivity, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(homeActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(homeActivity, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(homeActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
                pb.dismiss();
            }
        });
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(homeActivity);
        requestQueue.add(req);
    }

    public static void sucessdialogForNameMisMatch(final String straccholder1, final String message,
                                                   final String isliveInNach, final String mandateID) {
        isliveInNach2 = isliveInNach;

        TextView tvnameasform, tvLengthform, tvNameasbank, tvLengthasbank, tv_savemandateform;
        LinearLayout lyNAmegreater, lyNameequaltwenty, lynameequals;
        final RadioGroup rgbProcessstatus;
        dialogsuccess = new Dialog(homeActivity);
        dialogsuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogsuccess.setContentView(R.layout.sucessdialog);

        Window window = dialogsuccess.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        tvnameasform = dialogsuccess.findViewById(R.id.tv_nameasform);
        tvLengthform = dialogsuccess.findViewById(R.id.tv_charecternumber);
        tvNameasbank = dialogsuccess.findViewById(R.id.tv_nameasperbank);
        tvLengthasbank = dialogsuccess.findViewById(R.id.tv_numberbankcharecters);
        tv_savemandateform = dialogsuccess.findViewById(R.id.tv_savemandateform);

        lyNAmegreater = dialogsuccess.findViewById(R.id.ly_greatertwenty);
        lyNameequaltwenty = dialogsuccess.findViewById(R.id.ly_banknameequal);
        lynameequals = dialogsuccess.findViewById(R.id.ly_nameequals);

        rgbProcessstatus = dialogsuccess.findViewById(R.id.rdg_proceed);

        rgbProcessstatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radiodebittype = radioGroup.findViewById(i);

                String str = radiodebittype.getText().toString();
                if (str.equals("Mandate Name")) {
                    strProcessname = "M";
                } else if (str.equals("Bank Name")) {
                    strProcessname = "B";
                }
            }
        });

        final String str = String.valueOf(straccholder1.length());
        String str2 = String.valueOf(message.length());
        tvnameasform.setText(straccholder1);
        tvLengthform.setText(str);
        tvNameasbank.setText(message);
        tvLengthasbank.setText(str2);

        int namelength = straccholder1.length();
        if (namelength > 20) {
            lyNAmegreater.setVisibility(View.VISIBLE);
        } else {
            lyNAmegreater.setVisibility(View.GONE);
        }
        tv_savemandateform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!strProcessname.equals("null")) {
                    if (strProcessname.equals("B")) {
                        mandateupdate(message, strProcessname, isliveInNach, mandateID);
                    } else if (strProcessname.equals("M")) {
                        mandateupdate(straccholder1, strProcessname, isliveInNach, mandateID);
                    }
                } else {
                    Toast.makeText(homeActivity, "select mandate process", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogsuccess.show();
    }

    public static void mandateupdate(final String straccholder1, String type, final String isliveInNach,
                                     final String mandateId) {
        final ProgressDialog pb = ProgressDialog.show(homeActivity, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mandateid", mandateId);
        params.put("UserId", userId);
        params.put("Type", type);
        String appId = preferance.getInstance(homeActivity).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.mandateupdate, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    JSONObject obj2 = loginData.getJSONObject("UploadNameAsperResult");
                    String status = obj2.getString("status");
                    String message = obj2.getString("message");

                    if (status.equals("Success")) {
                        dialogsuccess.dismiss();
                        edtprimaryholder.setText(straccholder1);
                        if (isliveInNach.equals("True")) {
                            if (isEMandate.equals("True")) {
                                showEnashAlertDialog(mandateId, isliveInNach);
                            } else {
                                Toast.makeText(homeActivity, message, Toast.LENGTH_SHORT).show();
                                tv_physical.setVisibility(View.VISIBLE);
                                tv_submitmandate.setText(R.string.edit);
                                imageViewNew.setVisibility(View.VISIBLE);
                                disabledAllVIews();
                            }
                        } else if (isliveInNach.equals("False")) {
                            Toast.makeText(homeActivity, message, Toast.LENGTH_SHORT).show();
                            tv_upload.setVisibility(View.VISIBLE);
                            tv_submitmandate.setText(R.string.edit);
                            imageViewNew.setVisibility(View.VISIBLE);
                            disabledAllVIews();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getCause().getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                    Toast.makeText(homeActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(homeActivity, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(homeActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(homeActivity, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(homeActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
                pb.dismiss();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(homeActivity);
        requestQueue.add(req);
    }

    private static void disabledAllVIews() {
        edtaccountnumber.setEnabled(false);
        edtIfsc.setEnabled(false);
        edtmicr.setEnabled(false);
        edtamount.setEnabled(false);
        edtrefference.setEnabled(false);
        edtreference2.setEnabled(false);
        edtphonenumber.setEnabled(false);
        edtemail.setEnabled(false);
        edtprimaryholder.setEnabled(false);
        edtnameaccholder.setEnabled(false);
        edtnameaccholder3.setEnabled(false);
        spin_sponsorbank.setEnabled(false);
        edtmandate.setEnabled(false);
        radiotodebit.setEnabled(false);
        tvbankname.setEnabled(false);
        spin_utilitybank.setEnabled(false);
        rgbfrequency.setEnabled(false);
        rgbdebittype.setEnabled(false);
        chcancel.setEnabled(false);

        rbSb.setEnabled(false);
        rbCa.setEnabled(false);
        rbcc.setEnabled(false);
        rbother.setEnabled(false);
        rbmonthly.setEnabled(false);
        rbquiterly.setEnabled(false);
        rbyearly.setEnabled(false);
        rbfixammount.setEnabled(false);
        rb_aspresented.setEnabled(false);
        rb_halfyearly.setEnabled(false);
        rb_cbnre.setEnabled(false);
        rb_sbnrodebit.setEnabled(false);
        rb_none.setEnabled(false);
        rb_firstemipdc.setEnabled(false);
        rb_npdl.setEnabled(false);
        rb_fullpdc.setEnabled(false);

        edtfromdate.setEnabled(false);
        edttodate.setEnabled(false);

    }

    private void enabledAllVIews() {
        edtaccountnumber.setEnabled(true);
        edtIfsc.setEnabled(true);
        edtmicr.setEnabled(true);
        edtamount.setEnabled(true);
        edtrefference.setEnabled(true);
        edtreference2.setEnabled(true);
        edtphonenumber.setEnabled(true);
        edtemail.setEnabled(true);
        edtprimaryholder.setEnabled(true);
        edtnameaccholder.setEnabled(true);
        edtnameaccholder3.setEnabled(true);
        spin_sponsorbank.setEnabled(true);
        edtmandate.setEnabled(true);
        radiotodebit.setEnabled(true);
        tvbankname.setEnabled(true);
        spin_utilitybank.setEnabled(true);
        rgbfrequency.setEnabled(true);
        rgbdebittype.setEnabled(true);
        chcancel.setEnabled(true);

        rbSb.setEnabled(true);
        rbCa.setEnabled(true);
        rbcc.setEnabled(true);
        rbother.setEnabled(true);
        rbmonthly.setEnabled(true);
        rbquiterly.setEnabled(true);
        rbyearly.setEnabled(true);
        rbfixammount.setEnabled(true);
        rb_aspresented.setEnabled(true);
        rb_halfyearly.setEnabled(true);
        rb_cbnre.setEnabled(true);
        rb_sbnrodebit.setEnabled(true);

        rb_none.setEnabled(true);
        rb_firstemipdc.setEnabled(true);
        rb_npdl.setEnabled(true);
        rb_fullpdc.setEnabled(true);

        edtfromdate.setEnabled(true);
        edttodate.setEnabled(true);
    }

    private static void showEnashAlertDialog(final String mandateId, String isliveInNach) {
        final boolean allTrue[] = new boolean[6];
        Button buttonPhysical, buttonEnach;
        TextView imageViewTodateEdit;
        ImageView imageViewBankLiveOnENACH, imageViewUtilityCode, imageViewSponsorBank, imageViewClose, imageViewAmountMax, imageViewTodateSsMandatory, imageViewSingleSignatory;
        dialogEnash = new Dialog(homeActivity);
        dialogEnash.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnash.setContentView(R.layout.enash_dialog);
        buttonEnach = dialogEnash.findViewById(R.id.butonEnach);
        buttonPhysical = dialogEnash.findViewById(R.id.buttonPhysical);
        imageViewBankLiveOnENACH = dialogEnash.findViewById(R.id.imageViewBankLiveOnENACH);
        imageViewAmountMax = dialogEnash.findViewById(R.id.imageViewAmountMax);
        imageViewTodateEdit = dialogEnash.findViewById(R.id.imageViewTodateEdit);
        imageViewSingleSignatory = dialogEnash.findViewById(R.id.imageViewSingleSignatory);
        imageViewTodateSsMandatory = dialogEnash.findViewById(R.id.imageViewTodateSsMandatory);
        imageViewClose = dialogEnash.findViewById(R.id.imageViewClose);
        imageViewSponsorBank = dialogEnash.findViewById(R.id.imageViewSponsorBank);
        imageViewUtilityCode = dialogEnash.findViewById(R.id.imageViewUtilityCode);
        String maxAmount = preferance.getInstance(homeActivity).getMaxAmount();
        if (maxAmount == null) {
            maxAmount = "";
        }
        Float maxAmoutFloat = Float.parseFloat(maxAmount);
        Float editTextAMout = Float.parseFloat(edtamount.getText().toString());
        // strsponsorbank = String.valueOf(spbanklist.get(0).toString());
        //  strutilitycode = String.valueOf(utlitybanklist.get(0).toString());

        // Toast.makeText(homeActivity, strutilitycode, Toast.LENGTH_LONG).show();
        if (!strsponsorbank.equals("")) {
            imageViewSponsorBank.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.tick_icon));
            allTrue[4] = true;
        } else if (strsponsorbank.equals("")) {
            imageViewSponsorBank.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.cross_icon));
            allTrue[4] = false;
        }

        if (!strutility.equals("")) {
            imageViewUtilityCode.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.tick_icon));
            allTrue[5] = true;
        } else if (strutility.equals("")) {
            imageViewUtilityCode.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.cross_icon));
            allTrue[5] = false;
        }


        if (isliveInNach.equals("True")) {
            imageViewBankLiveOnENACH.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.tick_icon));
            allTrue[0] = true;
        } else if (isliveInNach.equals("False")) {
            imageViewBankLiveOnENACH.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.cross_icon));
            allTrue[0] = false;
        }
        if (editTextAMout <= maxAmoutFloat) {
            imageViewAmountMax.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.tick_icon));
            allTrue[1] = true;
        } else {
            imageViewAmountMax.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.cross_icon));
            allTrue[1] = false;
        }
        if (edtnameaccholder.getText().toString().length() > 0 || edtnameaccholder3.getText().toString().length() > 0) {
            imageViewSingleSignatory.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.cross_icon));
            allTrue[2] = false;
        } else {
            imageViewSingleSignatory.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.tick_icon));
            allTrue[2] = true;
        }

        if (edttodate.getText().toString().equalsIgnoreCase("")) {
            imageViewTodateSsMandatory.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.cross_icon));
            allTrue[3] = false;
            imageViewTodateEdit.setEnabled(true);
        } else {
            imageViewTodateSsMandatory.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.tick_icon));
            allTrue[3] = true;
            imageViewTodateEdit.setVisibility(View.INVISIBLE);
        }
        if (toDateMandatory.equals("True")) {
            imageViewTodateSsMandatory.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.tick_icon));
            allTrue[3] = true;
            imageViewTodateEdit.setVisibility(View.INVISIBLE);
        } else {
            imageViewTodateSsMandatory.setImageDrawable(homeActivity.getResources().getDrawable(R.mipmap.cross_icon));
            allTrue[3] = false;
            imageViewTodateEdit.setVisibility(View.VISIBLE);
            imageViewTodateEdit.setEnabled(true);
        }
        Window window = dialogEnash.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonPhysical.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                getPhysicalData(mandateId);

            }
        });
        if (allTrue[0] == false || allTrue[1] == false || allTrue[2] == false || allTrue[3] == false || allTrue[4] == false || allTrue[5] == false) {
            buttonEnach.setEnabled(false);
            buttonEnach.setBackground(homeActivity.getResources().getDrawable(R.drawable.disable_button));
            buttonPhysical.setEnabled(true);
            buttonPhysical.setBackground(homeActivity.getResources().getDrawable(R.drawable.button_ripple_effect));
        } else {
            if (isEnach == true) {
                buttonPhysical.setEnabled(false);
                buttonPhysical.setBackground(homeActivity.getResources().getDrawable(R.drawable.disable_button));
                buttonEnach.setEnabled(true);
                buttonEnach.setBackground(homeActivity.getResources().getDrawable(R.drawable.button_ripple_effect));
            } else if (isPhysical == true) {
                buttonEnach.setEnabled(false);
                buttonEnach.setBackground(homeActivity.getResources().getDrawable(R.drawable.disable_button));
                buttonPhysical.setEnabled(true);
                buttonPhysical.setBackground(homeActivity.getResources().getDrawable(R.drawable.button_ripple_effect));
            } else if (isEnach == false || isPhysical == false) {
                buttonPhysical.setEnabled(true);
                buttonPhysical.setBackground(homeActivity.getResources().getDrawable(R.drawable.button_ripple_effect));
                buttonEnach.setEnabled(true);
                buttonEnach.setBackground(homeActivity.getResources().getDrawable(R.drawable.button_ripple_effect));
            }
        }
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disabledAllVIews();
                if (isEnach == true) {
                    tv_physical.setVisibility(View.GONE);
                    tv_Enach.setVisibility(View.VISIBLE);
                } else if (isPhysical == true) {
                    tv_physical.setVisibility(View.VISIBLE);
                    tv_Enach.setVisibility(View.GONE);
                } else {
                    tv_physical.setVisibility(View.VISIBLE);
                    tv_Enach.setVisibility(View.VISIBLE);
                }

                tv_submitmandate.setText(homeActivity.getString(R.string.edit));
                dialogEnash.dismiss();
            }
        });

        buttonEnach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEnach = true;
                getMandateData(mandateId);
                dialogEnash.dismiss();
            }
        });
        imageViewTodateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDateForEnachDialog(R.string.todateisMandatory, mandateId);
                dialogEnash.dismiss();
            }
        });
        dialogEnash.setCanceledOnTouchOutside(false);
        dialogEnash.show();
    }

    private static void getPhysicalData(String newMandateid) {
        final ProgressDialog pb = ProgressDialog.show(homeActivity, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("MandateId", newMandateid);
        params.put("userId ", userId);
        String appId = preferance.getInstance(homeActivity).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.UpdateIsPhysical, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONObject jsonObject1 = jsonObject.getJSONObject("UpdateIsPhysicalResult");
                    String status = jsonObject1.getString("status");
                    String message = jsonObject1.getString("message");
                    String Error = jsonObject1.getString("Error");
                    if (status.equalsIgnoreCase("Success")) {
                        isPhysical = true;
                        if (dialogEnash != null) {
                            dialogEnash.dismiss();
                        }
                        tv_upload.setVisibility(View.VISIBLE);
                        tv_submitmandate.setText(R.string.edit);
                        tv_Enach.setVisibility(View.GONE);
                        tv_physical.setVisibility(View.GONE);
                        imageViewNew.setVisibility(View.VISIBLE);
                        disabledAllVIews();
                    } else {
                        if (isEMandate.equals("False")) {
                            isPhysical = true;
                            if (dialogEnash != null) {
                                dialogEnash.dismiss();
                            }
                            tv_upload.setVisibility(View.GONE);
                            tv_submitmandate.setText(R.string.edit);
                            tv_physical.setVisibility(View.VISIBLE);
                            imageViewNew.setVisibility(View.VISIBLE);
                            disabledAllVIews();
                        } else {
                            isPhysical = true;
                            isEnach = true;
                            if (dialogEnash != null) {
                                dialogEnash.dismiss();
                            }
                            tv_upload.setVisibility(View.GONE);
                            tv_submitmandate.setText(R.string.edit);
                            tv_Enach.setVisibility(View.VISIBLE);
                            tv_physical.setVisibility(View.VISIBLE);
                            imageViewNew.setVisibility(View.VISIBLE);
                            disabledAllVIews();
                        }

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getMessage());

                } catch (Exception e) {
                    CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getMessage());

                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getCause().getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(homeActivity);
        req.setRetryPolicy(new DefaultRetryPolicy(60000, 6, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }


    private static void getMandateData(final String newMandateid) {
        final ProgressDialog pb = ProgressDialog.show(homeActivity, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("MandateId", newMandateid);
        String appId = preferance.getInstance(homeActivity).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.GetXml, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    JSONObject jsonObject = loginData.getJSONObject("GetXmlDataResult");
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Mandate");
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);

                        if (jsonArray.length() > 0) {
                            String MerchantKey = jsonObject1.getString("MerchantKey");
                            String dDateTime = jsonObject1.getString("dDateTime");
                            homeActivity.startActivity(new Intent(homeActivity, ConsentAuthenticationActivity.class).putExtra("MandateData", jsonArray.get(0).toString()).putExtra("MandateId", newMandateid).putExtra("MerchantKey", MerchantKey).putExtra("dDateTime", dDateTime));
                            homeActivity.overridePendingTransition(0, 0);

                        } else {
                            CustomDialogForMessages.showMessageAlert(homeActivity, "Failure", "Mandate Data Not Found");

                        }
                    } else {
                        CustomDialogForMessages.showMessageAlert(homeActivity, status, message);
                    }

                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(homeActivity, "Failure", "Mandate Data Not Found");

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), error.getCause().getMessage());
                pb.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(homeActivity);
        req.setRetryPolicy(new DefaultRetryPolicy(60000, 6, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(req);
    }

    @SuppressLint("ClickableViewAccessibility")
    private static void addToDateForEnachDialog(int todateisMandatory, final String mandateId) {
        final Dialog dialogAddToDate = new Dialog(homeActivity);
        dialogAddToDate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddToDate.setContentView(R.layout.to_date_dialog);
        dialogAddToDate.setCanceledOnTouchOutside(false);
        Window window = dialogAddToDate.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textViewToDate = dialogAddToDate.findViewById(R.id.textViewToDate);
        final TextView textViewTitle = dialogAddToDate.findViewById(R.id.textViewTitle);
        Button buttonSave = dialogAddToDate.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewToDate.getText().toString().equalsIgnoreCase("")) {
                    textViewToDate.setError(homeActivity.getString(R.string.fillDate));
                    textViewToDate.requestFocus();
                } else if (textViewToDate.getError() != null) {
                    textViewToDate.setError(homeActivity.getString(R.string.fillCorrectDate));
                    textViewToDate.requestFocus();
                } else {
                    saveToDODateTOServer(userId, mandateId, changeDateFormatToServerFormat(textViewToDate.getText().toString()), dialogAddToDate);
                }
            }
        });
        textViewTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (textViewTitle.getRight() - textViewTitle.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        dialogAddToDate.dismiss();
                        showEnashAlertDialog(mandateId, isliveInNach2);
                        return true;
                    }
                }
                return true;
            }
        });
        textViewToDate.setText("");
        textViewToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                Calendar cc = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final String getCurrentDateTime = sdf.format(cc.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(homeActivity,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                strmydate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                Date date2 = null;
                                Date date1 = null;
                                try {
                                    date1 = sdf.parse(changeDateFormatToServerFormat(edtfromdate.getText().toString()));
                                    date2 = sdf.parse(strmydate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (edtfromdate.getText().toString().equals("") || edtfromdate.getText().toString() == null) {
                                    textViewToDate.setText(changeDateFormatToUserFormat(strmydate));
                                    textViewToDate.setError(null, null);
                                } else {
                                    if (date1.compareTo(date2) < 0) {
                                        textViewToDate.setText(changeDateFormatToUserFormat(strmydate));
                                        textViewToDate.setError(null, null);
                                    } else {
                                        textViewToDate.setError("To Date should be greater than From Date");
                                    }
                                }


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        dialogAddToDate.show();
    }

    private static void saveToDODateTOServer(String userId, final String mandateId, final String
            getCurrentDateTime, final Dialog dialogAddToDate) {
        final ProgressDialog pb = ProgressDialog.show(homeActivity, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("UserId", userId);
        params.put("MandateId", mandateId);
        params.put("ToDate", getCurrentDateTime);

        String appId = preferance.getInstance(homeActivity).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.UpdateToDate, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    JSONObject jsonObject = loginData.getJSONObject("UpdateToDateResult");
                    String status = String.valueOf(jsonObject.get("status"));
                    String message = String.valueOf(jsonObject.get("message"));
                    if (status.equalsIgnoreCase("Success")) {
                        Toast.makeText(homeActivity, message, Toast.LENGTH_LONG).show();
                        if (isEnach == true) {
                            tv_Enach.setVisibility(View.VISIBLE);
                            tv_physical.setVisibility(View.GONE);
                        } else if (isPhysical == true) {
                            tv_Enach.setVisibility(View.GONE);
                            tv_physical.setVisibility(View.VISIBLE);
                        } else {
                            tv_Enach.setVisibility(View.VISIBLE);
                            tv_physical.setVisibility(View.VISIBLE);
                        }
                        tv_submitmandate.setText(homeActivity.getString(R.string.edit));
                        edttodate.setText(changeDateFormatToUserFormat(getCurrentDateTime));
                        chcancel.setChecked(false);
                        edttodate.setError(null, null);
                        disabledAllVIews();
                        dialogAddToDate.dismiss();
                        dialogEnash.dismiss();
                    } else {
                        CustomDialogForMessages.showMessageAlert(homeActivity, status, message);
                        dialogAddToDate.dismiss();
                        dialogEnash.dismiss();

                    }
                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getCause().getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(homeActivity);
        requestQueue.add(req);

    }

    public static void uploaddialog(final String mandateid) {

        final CharSequence[] options = {"Choose from Gallery", "Cancel"};

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(homeActivity);
        builder.setTitle("upload file");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    (homeActivity).startActivityForResult(intent, 2);
                    homeActivity.overridePendingTransition(0, 0);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public void uploadimage(String baseimage, String mandateid) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("mm:ss");
        String strtime = "zip" + mdformat.format(calendar.getTime());

        final ProgressDialog pb = ProgressDialog.show(this, "Uploading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("base64", baseimage);
        params.put("MandateId", mandateid);
        params.put("RefrenceNo", "1");
        params.put("flag", "1");
        params.put("imageType", ".PNG");
        params.put("imageName", strtime);
        String appId = preferance.getInstance(homeActivity).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.uploadimage, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    JSONObject obj2 = loginData.getJSONObject("uploadImageResult");
                    strimagepath = obj2.getString("PngImage");
                    String message = obj2.getString("message");
                    if (message.equals("Success")) {
                        tv_submitmandate.setVisibility(View.GONE);
                        tv_upload.setVisibility(View.GONE);
                        tv_download.setVisibility(View.VISIBLE);
                        CustomDialogForMessages.showMessageAlert(HomeActivity.this, message, "Image Uploaded Successfully");
                    } else {
                        CustomDialogForMessages.showMessageAlert(HomeActivity.this, message, "Somethinhg Went Wrong");
                    }


                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getMessage());

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), error.getCause().getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        req.setRetryPolicy(new DefaultRetryPolicy(60000, 6, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }

    public static void callServiceFromEnach(String mandateId) {
        dialogEnash.dismiss();
        final ProgressDialog pb = ProgressDialog.show(homeActivity, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("MandateId", mandateId);
        String appId = preferance.getInstance(homeActivity).getAPPID();
        params.put("AppId", appId);

        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.ChkESign, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    Log.d("response", loginData.toString());
                    JSONObject obj2 = loginData.getJSONObject("ChkESignResult");
                    String status = obj2.getString("status").substring(0, 1).toUpperCase() + obj2.getString("status").substring(1);
                    String message = obj2.getString("message").substring(0, 1).toUpperCase() + obj2.getString("message").substring(1) + " " + obj2.getString("Error").substring(0, 1).toUpperCase() + obj2.getString("Error").substring(1);
                    CustomDialogForMessages.showMessageAlert(homeActivity, status, message);
                    if (message.contains("Unsuccessfully")) {
                        tv_Enach.setVisibility(View.VISIBLE);
                        tv_physical.setVisibility(View.VISIBLE);
                        tv_submitmandate.setText(homeActivity.getString(R.string.edit));
                        disabledAllVIews();
                    } else {
                        tv_upload.setVisibility(View.GONE);
                        tv_download.setVisibility(View.GONE);

                        tv_Enach.setVisibility(View.GONE);
                        tv_physical.setVisibility(View.GONE);
                        tv_submitmandate.setVisibility(View.GONE);
                        imageViewNew.setVisibility(View.VISIBLE);
                        disabledAllVIews();
                    }

                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getMessage());

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), error.getCause().getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(homeActivity);
        req.setRetryPolicy(new DefaultRetryPolicy(60000, 6, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }

    public static void callCancelBUtton(String mandateId, String merchantKey, String
            dDateTime, String errorCode) {
        callCancelWebservice(mandateId, merchantKey, dDateTime, errorCode);
        tv_Enach.setVisibility(View.VISIBLE);
        tv_physical.setVisibility(View.GONE);
        tv_download.setVisibility(View.GONE);
        tv_upload.setVisibility(View.GONE);
        tv_submitmandate.setText(homeActivity.getString(R.string.edit));
        disabledAllVIews();
    }

    public static void callReturnNSDLResponse(String nsdxmlPath, String eSignResponse, String
            mandateId, String userID, String merchantKey, String dDateTime, String entityId, String esignRequestId) {
        final ProgressDialog pb = ProgressDialog.show(homeActivity, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("MandateId", mandateId);
        params.put("NSDXMLPath", nsdxmlPath);
        params.put("AppID", "1");
        params.put("UserID", userID);
        params.put("ResponseXml", String.valueOf(eSignResponse));
        params.put("msgId", merchantKey);
        params.put("creationdatetime", dDateTime);
        params.put("EntityId", entityId);
        params.put("EsignRequestId", esignRequestId);
        String appId = preferance.getInstance(homeActivity).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.SaveSignData, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    JSONObject jsonObject = loginData.getJSONObject("SaveSignDataResult");
                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");
                    callAfterSuccessService(status, message);

                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getMessage());

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                //CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getCause().getMessage());/////comented by mohit 01-10-2018
                pb.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(homeActivity);
        req.setRetryPolicy(new DefaultRetryPolicy(100000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }

    private static void callCancelWebservice(String mandateId, String merchantKey, String
            dDateTime, final String errorCode) {
        //  dialogEnash.dismiss();
        final ProgressDialog pb = ProgressDialog.show(homeActivity, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("MandateId", mandateId);
        params.put("ErrorCode ", errorCode);
        params.put("MsgId ", merchantKey);
        params.put("CreationDateTime ", dDateTime);
        String appId = preferance.getInstance(homeActivity).getAPPID();
        params.put("AppId", appId);

        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.CancelEsign, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    JSONObject jsonObject = loginData.getJSONObject("CancelEsignResult");
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("success")) {
                        tv_Enach.setVisibility(View.VISIBLE);
                        tv_physical.setVisibility(View.GONE);
                        tv_upload.setVisibility(View.GONE);
                        tv_download.setVisibility(View.GONE);
                    } else {
                        tv_Enach.setVisibility(View.VISIBLE);
                        tv_physical.setVisibility(View.GONE);
                        tv_upload.setVisibility(View.GONE);
                        tv_download.setVisibility(View.GONE);
                    }
                    CustomDialogForMessages.showMessageAlert(homeActivity, "Error", errorCode);
                    Log.d("response", loginData.toString());

                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getMessage());

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                CustomDialogForMessages.showMessageAlert(homeActivity, homeActivity.getString(R.string.failure), e.getCause().getMessage());
                pb.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(homeActivity);
        requestQueue.add(req);
    }

    public static void callAfterSuccessService(String status, String message) {
        CustomDialogForMessages.showMessageAlert(homeActivity, status, message);
        if (status.equals("success")) {
            disabledAllVIews();
            tv_submitmandate.setText(homeActivity.getString(R.string.mandate_form));
            tv_Enach.setVisibility(View.GONE);
            tv_physical.setVisibility(View.GONE);
            tv_upload.setVisibility(View.GONE);
            tv_download.setVisibility(View.GONE);
            tv_submitmandate.setVisibility(View.GONE);
        } else {
            tv_submitmandate.setText(homeActivity.getString(R.string.edit));
            disabledAllVIews();
            tv_physical.setVisibility(View.GONE);
            tv_upload.setVisibility(View.GONE);
            tv_download.setVisibility(View.GONE);
            tv_Enach.setVisibility(View.VISIBLE);

        }
    }

    public void onSubmitButtonClick(View view) {
        stramount = edtamount.getText().toString();
        String amountInInt = "";
        long amount = 0;
        try {
            if (stramount.contains(".00")) {
                amountInInt = stramount.replace(".00", "");
            }
            amount = Long.parseLong(amountInInt);
        } catch (Exception e) {
        }
        if (rgbshowmandate.getVisibility() == View.GONE) {
            mandateModeChecked = true;
        }
        if (strtodate == null) {
            strtodate = "";
        }
        if (!chcancel.isEnabled()) {
            chcancel.setChecked(false);
        }
        if (tv_submitmandate.getText().toString().equalsIgnoreCase(getString(R.string.edit))) {
            tv_submitmandate.setText(getString(R.string.update));
            tv_upload.setVisibility(View.GONE);
            tv_download.setVisibility(View.GONE);
            tv_Enach.setVisibility(View.GONE);
            tv_physical.setVisibility(View.GONE);
            enabledAllVIews();
            if (strtem.equals("2")) {
                edtrefference.setEnabled(false);
            } else {
                edtrefference.setEnabled(true);
            }

        } else {
            //newMandateid = preferance.getInstance(homeActivity).getMandateID();
            if (strtodate == null) {
                strtodate = "";
            }
            strdateofmandate = changeDateFormatToServerFormat(edtmandate.getText().toString());
            strauthorize = edtauthorize.getText().toString();
            strbankaccountnumber = edtaccountnumber.getText().toString();
            strbankname = tvbankname.getText().toString();
            strifscc = edtIfsc.getText().toString();
            strmicr = edtmicr.getText().toString();
            stramount = edtamount.getText().toString();
            strreference1 = edtrefference.getText().toString();
            strreference2 = edtreference2.getText().toString();
            strphonenyumber = edtphonenumber.getText().toString();
            stremail = edtemail.getText().toString();
            strfromdate = changeDateFormatToServerFormat(edtfromdate.getText().toString());
            strtodate = changeDateFormatToServerFormat(edttodate.getText().toString());
            straccholder1 = edtprimaryholder.getText().toString();
            straccholder2 = edtnameaccholder.getText().toString();
            straccholder3 = edtnameaccholder3.getText().toString();

            strutility = spin_utilitybank.getText().toString();

            Refrencedata refrencedata = new Refrencedata();
            refrencedata.setRefdateofmandate(strdateofmandate);
            refrencedata.setRefsponsorcode(strsponsorbank);
            refrencedata.setRefutilitycode(strutility);
            refrencedata.setRefaccontno(strbankaccountnumber);
            refrencedata.setRefbankname(strbankname);
            refrencedata.setRefifsc(strifscc);
            refrencedata.setRefmicr(strmicr);
            refrencedata.setRefammountruppe(stramount);
            refrencedata.setRefrefrence1(strreference1);
            refrencedata.setRefreference2(strreference2);
            refrencedata.setRefphnenumber(strphonenyumber);
            refrencedata.setRefemail(stremail);
            refrencedata.setReffrom(strfromdate);
            refrencedata.setRefto(strtodate);
            refrencedata.setRefcustomer1(straccholder1);
            refrencedata.setRefcustomer2(straccholder2);
            refrencedata.setRefcustomer3(straccholder3);
            refrencedata.setRefdateofmandate(strdateofmandate);
            refrencedata.setRefdateofmandate(strdateofmandate);
            refrencedata.setRefdateofmandate(strdateofmandate);
            refrencedata.setRefmandatemode(strmandatevalue);
            refrencedata.setRefdiebittype(strdebittype);
            refrencedata.setReffrequency(strfrequency);
            refrencedata.setReftodebit(strtodebit);
            reftemplist.add(refrencedata);
            if (strdateofmandate.equals("")) {
                edtmandate.setError("Fill Date");
                edtmandate.requestFocus();
                openSoftKeyboard(edtmandate);

            } else if (strbankaccountnumber.equals("")) {
                edtaccountnumber.setError("Fill Account Number");
                edtaccountnumber.requestFocus();
                openSoftKeyboard(edtaccountnumber);

            } else if (strbankname.equals("")) {
                tvbankname.setError("Select Bank Name");
                tvbankname.requestFocus();

            } else if (strifscc.equals("") && strmicr.equals("")) {
                if (strifscc.equals("")) {
                    edtIfsc.setError("Fill IFSC Code");
                    edtIfsc.requestFocus();
                    openSoftKeyboard(edtIfsc);
                } else if (strmicr.equals("")) {
                    edtmicr.setError("Fill MICR Code");
                    edtmicr.requestFocus();
                    openSoftKeyboard(edtmicr);
                }
            } else if (strifscc.length() != 11 && strmicr.equals("")) {
                edtIfsc.setError("Fill Correct IFSC Code");
                edtIfsc.requestFocus();
                openSoftKeyboard(edtIfsc);

            } else if (strmicr.length() != 9 && strifscc.equals("")) {
                edtmicr.setError("Fill Correct MICR Code");
                edtmicr.requestFocus();
                openSoftKeyboard(edtmicr);

            } else if (stramount.equals("")) {
                edtamount.setError("Fill Amount");
                edtamount.requestFocus();
                openSoftKeyboard(edtamount);

            } else if (amount > 10000000) {
                textView_amount.setVisibility(View.GONE);
                edtamount.setError("Amount should not be greater than 1 Crore");
            } else if (strreference1.equals("")) {
                edtrefference.setError("Fill Reference");
                edtrefference.requestFocus();
                openSoftKeyboard(edtrefference);

            } else if (strfromdate.equals("")) {
                edtfromdate.setError("Select Date");
                edtfromdate.requestFocus();

            } else if ((strtodate == null) && !chcancel.isChecked()) {
                edttodate.setError("Select Date");
                edttodate.requestFocus();

            } else if (straccholder1.equals("")) {
                edtprimaryholder.setError("Fill Acc. Holder Name");
                edtprimaryholder.requestFocus();
                openSoftKeyboard(edtprimaryholder);

                //  erroralertmg("Fill Acc. Holder Name");
            } else if (!mandateModeChecked) {
                if (rgbshowmandate.getVisibility() == View.VISIBLE) {
                    if (strmandatemode.equalsIgnoreCase("None") || strmandatemode.equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), R.string.pleaseselectmandatetype, Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                String strmandateedit = preferance.getInstance(HomeActivity.this).getIsmandetedit();
                if (strmandateedit.equals("True") || strtem.equals("4")) {
                    savemandate();
                } else if (strmandateedit.equals("False") || strmandateedit.equals("")) {
                    String warningmsg = getString(R.string.cannotEditThisMandate);
                    CustomDialogForMessages.showMessageAlert(HomeActivity.this, getString(R.string.warning), warningmsg);
                }
            }
        }
    }

    ProgressDialog progressDialog;

    protected URL stringToURL(String urlString) {
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
