package com.quickcheck;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.quickcheck.model.Getmandate;
import com.quickcheck.preference.preferance;
import com.quickcheck.webservice.Serverfuction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GetmandateActivity extends AppCompatActivity {

    public static Activity activity;
    static RecyclerView rycmandate;
    static TextView textViewRetry;

    static ArrayList<Getmandate> listmandate;
    static MandateAdapter adapter;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static int newDataLength, firstDataLength;
    ImageView imgSearch, img_newedit, imgmenu;
    TextView tvTodate, Tvfromdate, tvSearchmandate, tv_loadmore;
    EditText edtSearchreference;
    String strtodate, strfromdate;
    private int mYear, mMonth, mDay;
    public static int count = 1;
    private static ProgressDialog pb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_getmandate);

        activity = this;
        count = 1;
        Calendar cc = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String getCurrentDateTime = sdf.format(cc.getTime());

        listmandate = new ArrayList<>();
        rycmandate = findViewById(R.id.reyc_mandate);
        textViewRetry = findViewById(R.id.textViewRetry);
        textViewRetry.setText(Html.fromHtml(getString(R.string.no_data_found_try_again)));
        imgSearch = findViewById(R.id.img_search);
        img_newedit = findViewById(R.id.img_newedit);
        imgmenu = findViewById(R.id.img_menu);
        imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menupop();
            }
        });

        img_newedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetmandateActivity.this, HomeActivity.class);
                intent.putExtra("strtem", "4");
                intent.putExtra("mandateid", "");
                intent.putExtra("AddNew", true);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GetmandateActivity.this, SearchActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        textViewRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmandate(count);

            }
        });
        if (count > 1) {
            count = 1;
        }
        getmandate(count);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    static int dataLength = 0;

    public static Activity getActivity() {
        return activity;
    }

    public static void getmandate(final int countt) {
        String user_id = null;
        String strcount = String.valueOf(count);
        user_id = preferance.getInstance(activity).getUserId();
        if (pb != null) {
            pb.dismiss();
        }
        pb = ProgressDialog.show(activity, "Loading", "Please wait...", false, false);
        pb.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("UserId", user_id);
        params.put("Count", strcount);
        String appId=preferance.getInstance(activity).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.gatmandate, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.dismiss();
                dataLength = 0;
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
                    if (jsonarray.length() == 0) {
                        rycmandate.setVisibility(View.GONE);
                        textViewRetry.setVisibility(View.VISIBLE);
                    }
                    listmandate.clear();

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
                        rycmandate.setVisibility(View.VISIBLE);
                        textViewRetry.setVisibility(View.GONE);
                        LinearLayoutManager llm = new LinearLayoutManager(activity);
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        rycmandate.setLayoutManager(llm);
                        if (adapter != null) {
                            adapter = null;
                        }
                        adapter = new MandateAdapter("Home", activity, listmandate);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(activity, "Opps", "No Record Found");
                    rycmandate.setVisibility(View.GONE);
                    textViewRetry.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    CustomDialogForMessages.showMessageAlert(activity, "Opps", "No Record Found");
                    rycmandate.setVisibility(View.GONE);
                    textViewRetry.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
              //  Log.e("errror", error.getMessage());
                CustomDialogForMessages.showMessageAlert(activity, "Opps", error.getCause().getMessage());
                rycmandate.setVisibility(View.GONE);
                textViewRetry.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        req.setRetryPolicy(new DefaultRetryPolicy(60000, 6, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pb != null)
            pb.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri contentURI = data.getData();
            try {
                String mandateid = preferance.getInstance(GetmandateActivity.this).get_mandate_id_temp();
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
        String appId=preferance.getInstance(this).getAPPID();
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
                        CustomDialogForMessages.showMessageAlert(GetmandateActivity.this, message, "Image Uploaded Successfully");
                    } else {
                        CustomDialogForMessages.showMessageAlert(GetmandateActivity.this, message, "Somethinhg Went Wrong");
                    }


                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(GetmandateActivity.this, getString(R.string.failure), e.getMessage());

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(GetmandateActivity.this, getString(R.string.failure), error.getCause().getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        req.setRetryPolicy(new DefaultRetryPolicy(200000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GetmandateActivity.this, HomeActivity.class));
        activity = null;
        overridePendingTransition(0, 0);
        finish();
        HomeActivity.count = 0;
    }

    public void searchdialog() {

        final Dialog dialog = new Dialog(GetmandateActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.searchdialog);

        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);


        tvTodate = dialog.findViewById(R.id.tv_searchtodate);
        Tvfromdate = dialog.findViewById(R.id.tv_searchfromdate);

        edtSearchreference = dialog.findViewById(R.id.edt_searchreference);

        tvSearchmandate = dialog.findViewById(R.id.tv_searchmandate);

        edtSearchreference.setEnabled(true);

        edtSearchreference.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = edtSearchreference.getText().toString().toLowerCase(Locale.getDefault());
                tvTodate.setEnabled(false);
                Tvfromdate.setEnabled(false);
                if (text.length() < 1) {
                    tvTodate.setEnabled(true);
                    Tvfromdate.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
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

                edtSearchreference.setEnabled(false);
                DatePickerDialog datePickerDialog = new DatePickerDialog(GetmandateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvTodate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                String strtodate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                Date date2 = null;
                                Date date1 = null;
                                try {
                                    date1 = sdf.parse(getCurrentDateTime);
                                    date2 = sdf.parse(strtodate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (date1.compareTo(date2) < 0) {
                                    tvTodate.setText(strtodate);
                                    // Log.d("Return","getMyTime smaller than getCurrentDateTime ");
                                } else {
                                    tvTodate.setText(getCurrentDateTime);
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        Tvfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtSearchreference.setEnabled(false);
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                Calendar cc = Calendar.getInstance();
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final String getCurrentDateTime = sdf.format(cc.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(GetmandateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Tvfromdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                String strtodate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                Date date2 = null;
                                Date date1 = null;
                                try {
                                    date1 = sdf.parse(getCurrentDateTime);
                                    date2 = sdf.parse(strtodate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (date1.compareTo(date2) < 0) {
                                    Tvfromdate.setText(strtodate);
                                    // Log.d("Return","getMyTime smaller than getCurrentDateTime ");
                                } else {
                                    Tvfromdate.setText(getCurrentDateTime);
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        tvSearchmandate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strtodatedialog = tvTodate.getText().toString();
                String strfromdatedialog = Tvfromdate.getText().toString();
                String strreferencedialog = edtSearchreference.getText().toString();

                if (!strreferencedialog.equals("") || !strtodatedialog.equals("")) {
                    //searchmandate(strtodatedialog, strfromdatedialog, strreferencedialog);
                } else {
                    Toast.makeText(GetmandateActivity.this, "Please fill date or reference ", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    ///change pass word dialog
    public void changepassword() {
        final String userid = preferance.getInstance(getApplicationContext()).getStudentId();
        final String oldPassword = preferance.getInstance(getApplicationContext()).get_password();

        final EditText edtoldpassword, edtnewpassword, edtconfirmpassword;
        TextView tvChangepass;

        Dialog dialogrefrence = new Dialog(GetmandateActivity.this);
        dialogrefrence.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogrefrence.setContentView(R.layout.changepassword);

        Window window = dialogrefrence.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        edtoldpassword = dialogrefrence.findViewById(R.id.edt_oldpassword);
        edtnewpassword = dialogrefrence.findViewById(R.id.edt_oldnewpassword);
        edtconfirmpassword = dialogrefrence.findViewById(R.id.edt_confirmpassword);

        tvChangepass = dialogrefrence.findViewById(R.id.tv_changepassword);

        tvChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String stroldpassword, newpassword, confirmpassword;

                stroldpassword = edtoldpassword.getText().toString();
                newpassword = edtnewpassword.getText().toString();
                confirmpassword = edtconfirmpassword.getText().toString();

                if (stroldpassword.equals("")) {
                    edtoldpassword.setError("Fill old password");
                } else if (!stroldpassword.equals(oldPassword)) {
                    edtoldpassword.setError("Wrong password");
                } else if (newpassword.equals("")) {
                    edtnewpassword.setError("Fill password");
                } else if (confirmpassword.equals("")) {
                    edtconfirmpassword.setError("Fill confirm password");
                } else if (!newpassword.equals(confirmpassword)) {
                    edtconfirmpassword.setError("Password dosn't match");
                } else {
                    changepassword(userid, newpassword);
                }
            }
        });

        dialogrefrence.show();
    }

    public void changepassword(String UserId, String Password) {

        final ProgressDialog pb = ProgressDialog.show(this, "Loading", "Please wait...", false, false);
        pb.show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("UserId", UserId);
        params.put("Password", Password);
        String appId=preferance.getInstance(this).getAPPID();
        params.put("AppId", appId);
        JsonObjectRequest req = new JsonObjectRequest(Serverfuction.changepassword, new JSONObject(
                params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                pb.dismiss();
                try {

                    JSONObject loginData = new JSONObject(String.valueOf(response));
                    //  String obj2= loginData.getString("message");
                    CustomDialogForMessages.showMessageAlert(GetmandateActivity.this, loginData.getString("status"), loginData.getString("message"));


                } catch (JSONException e) {
                    CustomDialogForMessages.showMessageAlert(GetmandateActivity.this, getString(R.string.failure), e.getMessage());

                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pb.dismiss();
                CustomDialogForMessages.showMessageAlert(activity, getString(R.string.failure), error.getCause().getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
        // MyApplication.getInstance().addToReqQueue(req);
    }


    public void menupop() {
        TextView tvlogout, tvchangepassword;
        Dialog dialogrefrence = new Dialog(GetmandateActivity.this);
        dialogrefrence.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogrefrence.setContentView(R.layout.menulayout);

        tvlogout = dialogrefrence.findViewById(R.id.tv_menulogout);
        tvchangepassword = dialogrefrence.findViewById(R.id.tv_menuchangepassword);
        Window window = dialogrefrence.getWindow();

        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP | Gravity.RIGHT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.y = 90;
        wlp.x = 10;
        window.setAttributes(wlp);

        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(GetmandateActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Log Out!")
                        .setContentText("Are you sure?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                preferance.getInstance(getApplicationContext()).clearuserSession();

                                Intent intent = new Intent(GetmandateActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(0, 0);

                                if (GetmandateActivity.activity != null) {
                                    GetmandateActivity.activity.finish();
                                    activity = null;
                                    overridePendingTransition(0, 0);
                                }

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

        dialogrefrence.show();

    }

    public static void loadMoreData() {
        getmandate(count);
        if (dataLength >= 10) {
            adapter.HideOrVisibleButton(View.VISIBLE);
        } else {
            adapter.HideOrVisibleButton(View.GONE);

        }
    }
}