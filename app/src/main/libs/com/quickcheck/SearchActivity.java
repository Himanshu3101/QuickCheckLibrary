package com.quickcheck;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quickcheck.adapter.MandateAdapter;
import com.quickcheck.model.Getmandate;
import com.quickcheck.preference.preferance;
import com.quickcheck.webservice.Serverfuction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.quickcheck.GetmandateActivity.dataLength;
import static com.quickcheck.HomeActivity.changeDateFormatToServerFormat;
import static com.quickcheck.HomeActivity.changeDateFormatToUserFormat;


public class SearchActivity extends AppCompatActivity {
    private static int count = 1;
    TextView tvFromdate;
    TextView tvTodate;
    TextView tvSearchmandate;
    static TextView textViewDataNoFound;
    int mYear, mMonth, mDay;
    EditText edtSearchreference;
    private static ArrayList<Getmandate> listmandate;
    private static RecyclerView rycmandate;
    public static MandateAdapter adapter;
    private ImageButton imageButtonPrevioudRecord, imageButtonMenu;
    private String strmydate = "";
    static String strtodatedialog;
    static String strfromdatedialog;
    static String strreferencedialog = "";
    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        activity = this;
        count = 1;
        tvFromdate = findViewById(R.id.tv_searchFromdate);
        tvTodate = findViewById(R.id.tv_searchTodate);
        textViewDataNoFound = findViewById(R.id.textViewDataNoFound);
        imageButtonPrevioudRecord = findViewById(R.id.imageButtonPrevioudRecord);
        imageButtonMenu = findViewById(R.id.imageButtonMenu);


        imageButtonPrevioudRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GetmandateActivity.getActivity() == null) {
                    Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                } else {
                    GetmandateActivity.getmandate(GetmandateActivity.count);
                    finish();
                    overridePendingTransition(0, 0);

                }

                // finish();
            }
        });

        imageButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menupop();
            }
        });

        textViewDataNoFound.setVisibility(View.GONE);

        listmandate = new ArrayList<>();
        rycmandate = findViewById(R.id.reyc_mandate);
        rycmandate.setVisibility(View.GONE);

        edtSearchreference = findViewById(R.id.edt_searchreference);

        tvSearchmandate = findViewById(R.id.tv_searchmandate);

        edtSearchreference.setEnabled(true);

        tvFromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                Calendar cc = Calendar.getInstance();
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final String getCurrentDateTime = sdf.format(cc.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                strmydate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                Date date2 = null;
                                Date date1 = null;
                                try {
                                    date1 = sdf.parse(changeDateFormatToServerFormat(tvTodate.getText().toString()));
                                    date2 = sdf.parse(strmydate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (tvTodate.getText().toString().equals("") || tvTodate.getText().toString() == null) {
                                    tvFromdate.setText(changeDateFormatToUserFormat(strmydate));
                                    tvFromdate.setError(null, null);
                                } else {
                                    if (date2.compareTo(date1) <= 0) {
                                        tvFromdate.setText(changeDateFormatToUserFormat(strmydate));
                                        tvFromdate.setError(null, null);
                                    } else {
                                        tvFromdate.setText("");
                                        tvFromdate.setError("From Date should be lesser than To Date");
                                        Toast.makeText(getApplicationContext(), "From Date should be lesser than To Date", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        tvTodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                Calendar cc = Calendar.getInstance();
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final String getCurrentDateTime = sdf.format(cc.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                strmydate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                Date date2 = null;
                                Date date1 = null;
                                try {
                                    date1 = sdf.parse(changeDateFormatToServerFormat(tvFromdate.getText().toString()));
                                    date2 = sdf.parse(strmydate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (tvFromdate.getText().toString().equals("") || tvFromdate.getText().toString() == null) {
                                    tvTodate.setText(changeDateFormatToUserFormat(strmydate));
                                    tvTodate.setError(null, null);
                                } else {
                                    if (date1.compareTo(date2) <= 0) {
                                        tvTodate.setText(changeDateFormatToUserFormat(strmydate));
                                        tvTodate.setError(null, null);
                                    } else {
                                        tvTodate.setText("");
                                        tvTodate.setError("To Date should be greater than From Date");
                                        Toast.makeText(getApplicationContext(), "To Date should be greater than From Date", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        tvSearchmandate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1) {
                    count = 1;
                }
                getSearchMandate(count);


            }
        });
    }

    private void getSearchMandate(int count) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view2 = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view2 == null) {
            view2 = new View(getApplicationContext());
        }
        imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
        strtodatedialog = changeDateFormatToServerFormat(tvFromdate.getText().toString());
        strfromdatedialog = changeDateFormatToServerFormat(tvTodate.getText().toString());
        strreferencedialog = edtSearchreference.getText().toString();

        if (!strreferencedialog.equals("")) {
            searchmandate("", "", strreferencedialog);
        } else if (!strfromdatedialog.equals("") || !strtodatedialog.equals("")) {
            searchmandate(strtodatedialog, strfromdatedialog, strreferencedialog);
        } else {
            Toast.makeText(SearchActivity.this, "Please fill date or reference", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri contentURI = data.getData();
            try {
                String mandateid = preferance.getInstance(SearchActivity.this).get_mandate_id_temp();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bitmap.recycle();
                String final_path = Base64.encodeToString(byteArray, Base64.DEFAULT);
                byte[] imageBytes = Base64.decode(final_path, Base64.DEFAULT);
                String path = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                uploadimage(path, mandateid);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
            }

        }

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
        String appId = preferance.getInstance(this).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.uploadimage, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    JSONObject obj2 = loginData.getJSONObject("uploadImageResult");
                    String strimagepath = obj2.getString("PngImage");
                    String message = obj2.getString("message");
                    if (message.equals("Success")) {
                        CustomDialogForMessages.showMessageAlert(SearchActivity.this, message, "Image Uploaded Successfully");
                    } else {
                        CustomDialogForMessages.showMessageAlert(SearchActivity.this, message, "Somethinhg Wrong Happened");
                    }


                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(SearchActivity.this, getString(R.string.failure), e.getMessage());

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(SearchActivity.this, getString(R.string.failure), error.getCause().getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        req.setRetryPolicy(new DefaultRetryPolicy(60000, 6, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }

    public void changepassword() {
        final String userid = preferance.getInstance(getApplicationContext()).getStudentId();
        final String oldPassword = preferance.getInstance(getApplicationContext()).get_password();

        final EditText edtoldpassword, edtnewpassword, edtconfirmpassword;
        Button tvChangepass;

        Dialog dialogChangePassword = new Dialog(SearchActivity.this);
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
        String appId = preferance.getInstance(this).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.changepassword, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                pb.dismiss();
                try {

                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    //  String obj2= loginData.getString("message");
                    CustomDialogForMessages.showMessageAlert(SearchActivity.this, loginData.getString("status"), loginData.getString("message"));


                } catch (JSONException e) {

                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(SearchActivity.this, getString(R.string.failure), e.getMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                CustomDialogForMessages.showMessageAlert(SearchActivity.this, getString(R.string.failure), e.getCause().getMessage());

                pb.dismiss();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
        // MyApplication.getInstance().addToReqQueue(req);
    }


    public void menupop() {
        TextView tvlogout, tvchangepassword;
        Dialog dialogLogout = new Dialog(SearchActivity.this);
        dialogLogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogout.setContentView(R.layout.menulayout);

        tvlogout = dialogLogout.findViewById(R.id.tv_menulogout);
        tvchangepassword = dialogLogout.findViewById(R.id.tv_menuchangepassword);
        Window window = dialogLogout.getWindow();

        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP | Gravity.RIGHT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.y = 90;
        wlp.x = 10;
        window.setAttributes(wlp);

        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(SearchActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Log Out!")
                        .setContentText("Are you sure?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                preferance.getInstance(getApplicationContext()).clearuserSession();

                                Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(0, 0);

                                if (GetmandateActivity.activity != null)
                                    GetmandateActivity.activity.finish();
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

        dialogLogout.show();

    }

    private static int newDataLength, firstDataLength = 0;

    public static void searchmandate(String todate, String fromdate, String reference) {
        String user_id = null;

        user_id = preferance.getInstance(activity).getUserId();

        final ProgressDialog pb = ProgressDialog.show(activity, "Loading", "Please wait...", false, false);
        pb.show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("UserId", user_id);
        params.put("Count", count + "");
        params.put("ToDate", fromdate);
        params.put("FromDate", todate);
        params.put("Reference", reference);
        String appId = preferance.getInstance(activity).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.getmandatedatewise, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                pb.dismiss();
                listmandate.clear();
                try {
                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    JSONArray jsonarray = loginData.getJSONArray("Mandate");
                    if (count == 1) {
                        dataLength = jsonarray.length();
                    } else if (count == 2) {
                        firstDataLength = jsonarray.length();
                    } else {
                        newDataLength = jsonarray.length();
                    }
                    if (jsonarray.length() > 0) {

                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject obj = jsonarray.getJSONObject(i);
                            Getmandate data = new Getmandate();
                            data.setReferenceno(obj.getString("Refrence1"));
                            data.setReference2(obj.getString("Refrence2"));
                            data.setPhoneNumber(obj.getString("PhoneNumber"));
                            data.setEmail(obj.getString("EmailId"));
                            data.setBankname(obj.getString("BankName"));
                            data.setIfsc(obj.getString("Code"));
                            data.setEnach(obj.getString("Enach"));
                            data.setXmlpath(obj.getString("xmlpath"));
                            data.setDoneEmandate(obj.getString("doneEmandate"));
                            data.setCustomername(obj.getString("Customer1"));
                            data.setCustomername2(obj.getString("Customer2"));
                            data.setCustomername3(obj.getString("Customer3"));
                            data.setDateofmandate(obj.getString("DateOnMandate"));
                            data.setMandateupload(obj.getString("IsScan"));
                            data.setStatus(obj.getString("status"));
                            data.setMandateid(obj.getString("mandateid"));
                            data.setJpgpath(obj.getString("JPGPath"));
                            data.setTifpath(obj.getString("TIFPath"));
                            data.setAmmount(obj.getString("AmountRupees"));
                            data.setIfscCode(obj.getString("Code"));
                            data.setDebitType(obj.getString("DebitType"));
                            data.setFrequencyType(obj.getString("FrequencyType"));
                            data.setMandateMode(obj.getString("MandateMode"));
                            data.setSponsorbankCode(obj.getString("SponsorbankCode"));
                            data.setUtilityCode(obj.getString("UtilityCode"));
                            data.setAcNo(obj.getString("AcNo"));
                            data.setToDebit(obj.getString("ToDebit"));
                            data.setIsphysical(obj.getString("Isphysical"));

                            data.setFromDate(obj.getString("FromDate"));
                            data.setTodate(obj.getString("Todate"));
                            data.setUpdatedon(obj.getString("updatedon"));
                            data.setCreatedon(obj.getString("createdon"));
                            listmandate.add(data);

                        }
                        if (count == 1) {
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                            rycmandate.setLayoutManager(mLayoutManager);
                            rycmandate.setItemAnimator(new DefaultItemAnimator());
                            rycmandate.setVisibility(View.VISIBLE);
                            textViewDataNoFound.setVisibility(View.GONE);
                            if (adapter != null) {
                                adapter = null;
                            }

                            adapter = new MandateAdapter("Search", activity, listmandate);
                            rycmandate.setAdapter(adapter);
                        } else {
                            if (dataLength == firstDataLength) {
                                Toast.makeText(activity, activity.getString(R.string.no_more_data_found), Toast.LENGTH_SHORT).show();
                            } else if (firstDataLength == newDataLength) {
                                Toast.makeText(activity, activity.getString(R.string.no_more_data_found), Toast.LENGTH_SHORT).show();
                            }
                            adapter.notifyDataSetChanged();
                        }
                        count++;
                    } else {
                        textViewDataNoFound.setVisibility(View.VISIBLE);
                        rycmandate.setVisibility(View.GONE);
                        textViewDataNoFound.setText(activity.getString(R.string.nodatafind));

                    }

                    //  String status=loginData.getString("status");
                    // String message=loginData.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(activity, activity.getString(R.string.failure), e.getCause().getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(activity, activity.getString(R.string.failure), e.getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        req.setRetryPolicy(new DefaultRetryPolicy(60000, 6, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }

    public static void loadMoreData() {
        if (!strreferencedialog.equals("")) {
            searchmandate("", "", strreferencedialog);
        } else if (!strfromdatedialog.equals("") || !strtodatedialog.equals("")) {
            searchmandate(strtodatedialog, strfromdatedialog, strreferencedialog);
        } else {
            Toast.makeText(activity, "Please fill date or reference", Toast.LENGTH_SHORT).show();
        }
        if (dataLength >= 10) {
            adapter.HideOrVisibleButton(View.VISIBLE);
        } else {
            adapter.HideOrVisibleButton(View.GONE);

        }
    }
}
