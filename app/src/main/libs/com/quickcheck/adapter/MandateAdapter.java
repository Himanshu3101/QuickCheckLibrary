package com.quickcheck.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quickcheck.model.Getmandate;
import com.quickcheck.DownloadTask;
import com.quickcheck.GetmandateActivity;
import com.quickcheck.HomeActivity;
import com.quickcheck.R;
import com.quickcheck.SearchActivity;
import com.quickcheck.preference.preferance;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class MandateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Getmandate> mandatelist;
    Activity context;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private Activity mContext;
    private Activity mActivity;

    private CoordinatorLayout mCLayout;
    private Button mButtonDo;
    private ProgressDialog mProgressDialog;
    private ImageView mImageView;
    private ImageView mImageViewInternal;

    private AsyncTask mMyTask;
    private ProgressDialog pDialog;
    ProgressDialog progressDialog;
    String home = "";

    public MandateAdapter(String home, Activity context, ArrayList<Getmandate> mandatelist) {
        this.home = home;
        this.context = context;
        this.mandatelist = mandatelist;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == mandatelist.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        return position > mandatelist.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.getmandate_adapter, parent, false);
            // set the view's size, margins, paddings and layout parameters
            return new ItemViewHolder(v);
        } else if (viewType == TYPE_FOOTER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
            return new FooterViewHolder(v);

        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    public void HideOrVisibleButton(int visible) {
        FooterViewHolder.loadMore.setVisibility(visible);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View View;
        public TextView reference_no, bankname, ifsc, customername, dateofmandate, mandateupload, status;
        LinearLayout tv_gatemandateedit, tv_upload, tv_getmandatedownload;

        public ItemViewHolder(View v) {
            super(v);
            reference_no = (TextView) itemView.findViewById(R.id.tv_getmanreferenceno);
            bankname = (TextView) itemView.findViewById(R.id.tv_getmandatebankname);
            ifsc = (TextView) itemView.findViewById(R.id.tv_getmandateifsc);
            customername = (TextView) itemView.findViewById(R.id.tv_getmandatecustomername);
            dateofmandate = (TextView) itemView.findViewById(R.id.tv_getmandatedate);
            //   mandateupload = (TextView) itemView.findViewById(R.id.tv_getmandateupload);
            // status = (TextView) itemView.findViewById(R.id.tv_getmandatestatus);
            tv_gatemandateedit = itemView.findViewById(R.id.tv_gatemandateedit);
            tv_upload = itemView.findViewById(R.id.tv_getmandateuploadfile);
            tv_getmandatedownload = itemView.findViewById(R.id.tv_getmandatedownload);
            // Add your UI Components here
        }

    }


    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public View View;
        public static TextView loadMore;

        public FooterViewHolder(View v) {
            super(v);
            View = v;
            loadMore = (TextView) v.findViewById(R.id.footer_text);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FooterViewHolder) {
            // ((FooterViewHolder)hoiflder).
            if (mandatelist.size() >= 10) {
                ((FooterViewHolder) holder).loadMore.setVisibility(View.VISIBLE);
            } else {
                ((FooterViewHolder) holder).loadMore.setVisibility(View.GONE);

            }

            ((FooterViewHolder) holder).loadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (home.equals("Home")) {
                        GetmandateActivity.loadMoreData();
                    } else {
                        SearchActivity.loadMoreData();
                    }
                }
            });

        } else if (holder instanceof ItemViewHolder) {
            final Getmandate data = mandatelist.get(position);
            ((ItemViewHolder) holder).reference_no.setText(data.getReferenceno());
            ((ItemViewHolder) holder).bankname.setText(data.getEnach());
            ((ItemViewHolder) holder).ifsc.setText(data.getCreatedon());
            ((ItemViewHolder) holder).customername.setText(data.getUpdatedon());
            ((ItemViewHolder) holder).dateofmandate.setText(data.getStatus());
            // holder.mandateupload.setText(data.getMandateupload());
            // holder.status.setText(data.getStatus());

            final String jpgpath = data.getJpgpath();
            String jpgtif = data.getTifpath();

            ((ItemViewHolder) holder).tv_getmandatedownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!jpgpath.equals("")) {
                        new DownloadTask(context, jpgpath);

//                        mMyTask = new DownloadTask(context)
//                                .execute(stringToURL(jpgpath));

                    } else {
                        Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            final String mandateid = data.getMandateid();
            preferance.getInstance(context.getApplicationContext()).savemandateid(mandateid);
            if (HomeActivity.isEMandate.equals("False")) {
                ((ItemViewHolder) holder).tv_upload.setEnabled(true);
                ((ItemViewHolder) holder).tv_getmandatedownload.setEnabled(true);
                ((ItemViewHolder) holder).tv_upload.setBackground(context.getResources().getDrawable(R.color.buttonColor));
                ((ItemViewHolder) holder).tv_getmandatedownload.setBackground(context.getResources().getDrawable(R.color.buttonColor));
            } else {
                if (data.getDoneEmandate().equalsIgnoreCase("True")) {
                    if (data.getXmlpath().equalsIgnoreCase("")) {
                        ((ItemViewHolder) holder).tv_upload.setEnabled(true);
                        ((ItemViewHolder) holder).tv_getmandatedownload.setEnabled(true);
                        ((ItemViewHolder) holder).tv_upload.setBackground(context.getResources().getDrawable(R.color.buttonColor));
                        ((ItemViewHolder) holder).tv_getmandatedownload.setBackground(context.getResources().getDrawable(R.color.buttonColor));
                    } else {
                        ((ItemViewHolder) holder).tv_upload.setEnabled(false);
                        ((ItemViewHolder) holder).tv_getmandatedownload.setEnabled(false);
                        ((ItemViewHolder) holder).tv_upload.setBackground(context.getResources().getDrawable(R.color.transparentbuttonColor));
                        ((ItemViewHolder) holder).tv_getmandatedownload.setBackground(context.getResources().getDrawable(R.color.transparentbuttonColor));

                    }
                }
            }
            ((ItemViewHolder) holder).tv_gatemandateedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //   HomeActivity.;
                    String strmandateedit = preferance.getInstance(context).getIsmandetedit();

                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("mandateid", mandateid);
                    intent.putExtra("referenceno", data.getReferenceno());
                    intent.putExtra("customername", data.getCustomername());
                    intent.putExtra("Enach", data.getEnach());

                    intent.putExtra("Customername2", data.getCustomername2());
                    intent.putExtra("Email", data.getEmail());
                    intent.putExtra("PhoneNumber", data.getPhoneNumber());
                    intent.putExtra("Reference2", data.getReference2());
                    intent.putExtra("Customername3", data.getCustomername3());
                    intent.putExtra("Isphysical", data.getIsphysical());
                    intent.putExtra("doneEmandate", data.getDoneEmandate());
                    intent.putExtra("xmlpath", data.getXmlpath());

                    intent.putExtra("AmountRupees", data.getAmmount());
                    intent.putExtra("BankName", data.getBankname());
                    intent.putExtra("Code", data.getIfsc());
                    intent.putExtra("DebitType", data.getDebitType());
                    intent.putExtra("FrequencyType", data.getFrequencyType());
                    intent.putExtra("MandateMode", data.getMandateMode());
                    intent.putExtra("SponsorbankCode", data.getSponsorbankCode());
                    intent.putExtra("ToDebit", data.getToDebit());
                    intent.putExtra("FromDate", data.getFromDate());
                    intent.putExtra("ToDate", data.getTodate());
                    intent.putExtra("UtilityCode", data.getUtilityCode());
                    intent.putExtra("AcNo", data.getAcNo());
                    intent.putExtra("Status", data.getStatus());
                    intent.putExtra("Dateofmandate", data.getDateofmandate());
                    intent.putExtra("strtem", "2");
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(0, 0);

                }
            });

            ((ItemViewHolder) holder).tv_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    preferance.getInstance(context).savemandateidtempid(mandateid);
                    uploaddialog(mandateid);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mandatelist.size() >= 10) {
            return mandatelist.size() + 1;
        } else {
            return mandatelist.size();
        }
    }

    public void uploaddialog(final String mandateid) {

        final CharSequence[] options = {"Choose from Gallery", "Cancel"};

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("upload file");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    ((Activity) context).startActivityForResult(intent, 2);
                    ((Activity) context).overridePendingTransition(0, 0);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /* private class DownloadTask extends AsyncTask<URL, Void, Bitmap> {
         // Before the tasks execution
         protected void onPreExecute() {
             if (progressDialog != null) {
                 progressDialog.dismiss();
             }
             progressDialog = ProgressDialog.show(context, "Loading", "Please wait...", false, false);
             progressDialog.show();
         }

         // Do the task in background/non UI thread
         protected Bitmap doInBackground(URL... urls) {
             Bitmap bitmapImg = null;

             URL url = urls[0];
             HttpURLConnection connection = null;

             try {
                 connection = (HttpURLConnection) url.openConnection();

                 connection.connect();
                 InputStream inputStream = connection.getInputStream();
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                 bufferedInputStream.wait(3000);
                 bitmapImg = BitmapFactory.decodeStream(bufferedInputStream);
             } catch (IOException e) {
                 e.printStackTrace();
                 return null;
             } catch (InterruptedException e) {
                 e.printStackTrace();
                 return null;
             }
             return bitmapImg;
         }

         // When all async task done
         protected void onPostExecute(Bitmap result) {
             // Hide the progress dialog

             if (progressDialog != null) {
                 progressDialog.dismiss();
             }
             if (result != null) {
                 // Display the downloaded image into ImageView
                 // mImageView.setImageBitmap(result);
                 storeImage(result);
                 // Save bitmap to internal storage
                 //  Uri imageInternalUri = saveImageToInternalStorage(result);
                 // Set the ImageView image from internal storage
                 //  mImageViewInternal.setImageURI(imageInternalUri);
             } else {
                 // Notify user that an error occurred while downloading image
                 //Snackbar.make(mCLayout,"Error",Snackbar.LENGTH_LONG).show();
             }
         }
     }
 */
    protected URL stringToURL(String urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void storeImage(Bitmap image) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(context, "Image download successfully..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    protected Uri saveImageToInternalStorage(Bitmap bitmap) {
        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(mContext);

        // Initializing a new_icon file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir("Images", MODE_PRIVATE);

        // Create a file to save the image
        file = new File(file, "UniqueFileName" + ".jpg");

        try {
            // Initialize a new_icon OutputStream
            OutputStream stream = null;

            // If the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            // Flushes the stream
            stream.flush();

            // Closes the stream
            stream.close();

        } catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }
        // Parse the gallery image url to uri
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());
        // Return the saved image Uri
        return savedImageURI;
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

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                // Output stream to write file
                OutputStream output = new FileOutputStream("/sdcard/downloadedfile.jpg");
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }
                // flushing output
                output.flush();
                // closing streams
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {
            //dismiss the dialog after the file was downloaded
            //dismissDialog(progress_bar_type);
            //Displaying downloaded image into image view
            //Reading image path from sdcard
            /*String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
            //setting downloaded into image view
             my_image.setImageDrawable(Drawable.createFromPath(imagePath));*/
        }
    }

}
