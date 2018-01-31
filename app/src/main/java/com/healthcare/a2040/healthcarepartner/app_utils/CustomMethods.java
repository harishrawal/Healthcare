package com.healthcare.a2040.healthcarepartner.app_utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.healthcare.a2040.healthcarepartner.R;
import com.healthcare.a2040.healthcarepartner.app_interfaces.DateCallBack;
import com.healthcare.a2040.healthcarepartner.app_interfaces.TimeCallBack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Created by Shivam on 13-07-2017.
 */
public class CustomMethods {
    Context mContext;

    public CustomMethods(Context context) {
        this.mContext = context;

    }

    public void msgbox(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void snackbox(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public void snackboxWithClick(View view, String msgToShow, int msgColor, String buttonText, int buttonTextColor, View.OnClickListener clickListener) {

        Snackbar snackbar = Snackbar
                .make(view, msgToShow, Snackbar.LENGTH_LONG)
                .setAction(buttonText, clickListener);

        snackbar.setActionTextColor(buttonTextColor);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(msgColor);
        snackbar.show();

        /*
        Snackbar snackbar = Snackbar
                .make(view, msgToShow, Snackbar.LENGTH_LONG)
                .setAction(buttonText, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

// Changing message text color
        //   snackbar.setActionTextColor(Color.RED);
        snackbar.setActionTextColor(buttonTextColor);

// Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        // textView.setTextColor(Color.YELLOW);
        textView.setTextColor(msgColor);
        snackbar.show();
*/
    }


    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(((AppCompatActivity) mContext).getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void invisibleToVisibleLayoutAnim(View myView) {

//          Please use this function after load the page that means can be called on button click
//              previously invisible view to Visible

        // get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
            // make the view visible and start the animation
            myView.setVisibility(View.VISIBLE);
            anim.start();
        }

    }

    public void visibleToInvisibleLayoutAnim(View myView, AnimatorListenerAdapter animListner) {

//          Please use this function after load the page that means can be called on button click
//              previously visible view to Invisible

        // get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

        // get the final radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(animListner);

// start the animation
            anim.start();
        }

    }


    public String today_date_Y_M_D() {
        Date date1 = new Date();
//        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy MMMM, d");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatter.format(date1);
    }

    public String today_date_D_M_Y() {
        Date date1 = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormatter.format(date1);
    }

    public String find_dmy_by_ymd(String yymmdd) {
        try {
            SimpleDateFormat _ymdSDF = new SimpleDateFormat("yyyy-MM-dd");

            SimpleDateFormat _dmySDF = new SimpleDateFormat("dd-MM-yyyy");

            Date _ymdDate = _ymdSDF.parse(yymmdd);

            return _dmySDF.format(_ymdDate);

        } catch (ParseException ex) {
            return yymmdd;
        }
    }

    public Calendar getNextCountDate(String initDate_ymd, int nxtdateCount) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(initDate_ymd));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, nxtdateCount);
        return c;
    }

    public String current_time(boolean is24HR) {
        Date time = new Date();
        SimpleDateFormat dateFormatter;
        if (is24HR) {
            dateFormatter = new SimpleDateFormat("hh:mm:ss");
        } else {
            dateFormatter = new SimpleDateFormat("hh:mm:ss a");
        }
        return dateFormatter.format(time);
    }

    public String convertTime24to12(String time24hr) {
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");

            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm:ss a");

            Date _24HourDate = _24HourSDF.parse(time24hr);

            //System.out.println(_24HourDt);

            return _12HourSDF.format(_24HourDate);
        } catch (ParseException ex) {
            return time24hr;
        }
    }

    public String convertTime12to24(String time12hr) {
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");

            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm:ss a");

            Date _12HourDate = _12HourSDF.parse(time12hr);


            return _24HourSDF.format(_12HourDate);

        } catch (ParseException ex) {
            return time12hr;
        }

    }

    public String convertDateTime12to24(String datetime12hr) {
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

            SimpleDateFormat _12HourSDF = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss a");

            Date _12HourDate = _12HourSDF.parse(datetime12hr);


            return _24HourSDF.format(_12HourDate);
        } catch (ParseException ex) {
            return datetime12hr;
        }

    }

    public boolean isConnectedToInternet() {
        boolean is_active_ = false;

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            is_active_ = info != null;
        } else {
            is_active_ = false;
        }


        return is_active_;
    }


    public boolean is_gps_enabled() {
        boolean is_active_ = false;

        final LocationManager manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            is_active_ = true;

        } else {
            is_active_ = false;
        }

        return is_active_;
    }

    public void ope_gps_Alert(final Context cont) {

        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(cont);
        alertDialog.setTitle("GPS Setting");
        alertDialog.setMessage("Please enable GPS");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                cont.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                msgbox("You don't have permission to Book Obey Cabs without GPS");

                dialog.dismiss();
                ((AppCompatActivity) cont).finish();

            }
        });
        alertDialog.create().show();
    }

    public String address_text(double lat, double lon) {

        String house = "", city = "", state = "", block = "", country = "", street_name = "";
        List<Address> addresses;
        Geocoder geocoder;

        try {
            geocoder = new Geocoder(mContext, Locale.getDefault());

            addresses = geocoder.getFromLocation(lat, lon, 1);
            if (addresses.size() <= 0) {
                return "";
            }
            house = addresses.get(0).getAddressLine(0);

            block = addresses.get(0).getAddressLine(1);


            street_name = addresses.get(0).getSubLocality();
            Log.e("street_name", street_name);

            state = addresses.get(0).getAdminArea();
            Log.e("state", state);
            city = addresses.get(0).getLocality();
            Log.e("city", city);
            country = addresses.get(0).getCountryName();

        } catch (IOException e) {
            e.printStackTrace();
            return lat + "-" + lon;

        }


        return house + "," + street_name + "," + block + "," + state + "," + city + "," + country;
    }

//    public float calculateDistanceInMeters(LatLng lat1, LatLng lat2) {
//
//        if (lat1 == null || lat2 == null) {
//            Log.d("calculateDistInMeters", "Both Cordinates null, Returning Distance is 00.00 ");
//            return 00.00f;
//        }
//
//        Location loc1 = new Location("");
//        loc1.setLatitude(lat1.latitude);
//        loc1.setLongitude(lat1.longitude);
//
//        Location loc2 = new Location("");
//        loc2.setLatitude(lat2.latitude);
//        loc2.setLongitude(lat2.longitude);
//
//
//        // return in meter
//        float calculatedDIst = loc1.distanceTo(loc2);
//
//        Log.d("calculateDistInMeters", "Returning Distance is "+calculatedDIst+" -->");
//        return calculatedDIst;
//
//    }
//

    public File createNewDirectory(String directoryName) {

        File Sub_direct = new File(Environment.getExternalStorageDirectory() + "/" + directoryName);

        if (!Sub_direct.exists()) {
            try {
                if (Sub_direct.mkdir()) {

                    return Sub_direct;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return Sub_direct;
        }
        return Sub_direct;
    }

    public String folder_image_insertwith_NewDir(String subdir, Bitmap bitmaps, String fileName) {

        String imagepath = null;


        File Sub_direct = new File(Environment.getExternalStorageDirectory() + "/" + subdir);

        if (!Sub_direct.exists()) {
            try {
                if (Sub_direct.mkdir()) {


                    //directory is created;
                    imagepath = insert_BitmapInExt_Dir(Sub_direct, bitmaps, fileName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // insert somethig
            imagepath = insert_BitmapInExt_Dir(Sub_direct, bitmaps, fileName);
        }
        return imagepath;
    }

    public String folder_image_insert(Bitmap bitmaps, String fileName) {
        String imagepath = null;

        File direct = new File(Environment.getExternalStorageDirectory() + "/Fabiano");
        if (!direct.exists()) {
            try {
                if (direct.mkdir()) {
                    //directory is created;
                    imagepath = insert_BitmapInExt_Dir(direct, bitmaps, fileName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {// insert somethig
            imagepath = insert_BitmapInExt_Dir(direct, bitmaps, fileName);
        }
        return direct + "/" + imagepath;
    }

    public String insert_BitmapInExt_Dir(File direct, Bitmap bitmap, String file_name) {

        File file;
        // Random random = new Random();
        //int n = 100000;
        //n = random.nextInt(n);
        file = new File(direct, file_name);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_name;
    }

    public Bitmap image_display(String imgFile_path) {
        Bitmap myBitmap = null;

        File imgFile = new File(imgFile_path);
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        } else {
            myBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.map_marker);
        }
        return myBitmap;
    }

    public static String convertImageToSring(Bitmap bitmap) {

        //   myselPic.setImageBitmap(bitmap);

        //BitmapFactory.Options options = new BitmapFactory.Options();

        // options.inSampleSize = 3;

        // Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);

        // Drawable myDrawable = getResources().getDrawable(R.drawable.splash_manjra);

        //BitmapDrawable bitmapDrawable = ((BitmapDrawable) user_pic.getDrawable());

        // Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Must compress the Image to reduce image size to make upload easy

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] byte_arr = stream.toByteArray();

        // Encode Image to String

        return Base64.encodeToString(byte_arr, 0);

        //custom_methods.msgbox("converted");

    }


    public void openTimePicker(final TimeCallBack m_callback) {

        Calendar mcurrentTime = Calendar.getInstance();
        final int myhour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int myminute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, final int hourOfDay, final int minute) {

                m_callback.setTime(hourOfDay + ":" + minute + ":00");

            }
        }, myhour, myminute, true);
        //   mTimePicker.setP_Name("Please Select Present Time");
        mTimePicker.show();
    }

    public void openDatePicker(final DateCallBack m_callback) {

        Calendar mcurrentTime = Calendar.getInstance();
        final int myyear = mcurrentTime.get(Calendar.YEAR);
        final int mymonth = mcurrentTime.get(Calendar.MONTH);
        final int mydate = mcurrentTime.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mdatePicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String nmonth, nday;

                if (month < 10) {
                    nmonth = "0" + (month + 1);
                } else {
                    nmonth = String.valueOf(month + 1);
                }
                if (dayOfMonth < 10) {
                    nday = "0" + dayOfMonth;
                } else {
                    nday = String.valueOf(dayOfMonth);
                }

                m_callback.setDateYYY_MM_DD(year + "-" + nmonth + "-" + nday);
                m_callback.setDateDD_MM_YYYY(nday + "-" + nmonth + "-" + year);
            }
        }, myyear, mymonth, mydate);
        //   mdatePicker.setP_Name("Please Select Present Date");
        mdatePicker.show();

    }

    public String getRealAudioFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if
                (ContentResolver.SCHEME_CONTENT.equals(scheme)) {

            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Audio.AudioColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {

                    int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
//                    int indeximage = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public String getRealImageFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if
                (ContentResolver.SCHEME_CONTENT.equals(scheme)) {

            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {

                    // int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public String getRealVideoFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if
                (ContentResolver.SCHEME_CONTENT.equals(scheme)) {

            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Video.VideoColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {

                    int index = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
//                    int indeximage = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    String str = "<html >" +
            "<head>" +
            "    <title>TMI</title>" +
            "</head>" +
            "<style type='text/css'>" +
            "    .clearfix:after {" +
            "  content: '';" +
            "  display: table;" +
            "  clear: both;" +
            "}" +
            "a {" +
            "  color: #000;" +
            "  text-decoration: underline;" +
            "}" +
            "#background {" +
            "   position: relative;" +
            "}" +
            "#background:after {" +
            "    content : '';" +
            "    display: block;" +
            "    position: absolute;" +
            "    top: 0;" +
            "    left: 0;" +
            "    background: url('logo.jpg') no-repeat fixed center;" +
            "    width: 100%;" +
            "    height: 100%;" +
            "    opacity : 0.19;" +
            "    z-index: -1;" +
            "}" +
            "body {" +
            "  position: relative;" +
            "  width: 21cm;  " +
            "  height: 29.7cm; " +
            "  margin: 0 auto; " +
            "  color: #000;" +
            "  background: #FFFFFF; " +
            "  font-family: Arial, sans-serif; " +
            "  font-size: 12px; " +
            "  font-family: Arial;" +
            "  border: 2px solid #000;" +
            "  padding: 10px 10px 10px 10px;" +
            "}" +
            "header {" +
            "  padding: 10px 0;" +
            "  margin-bottom: 30px;" +
            "}" +
            "h1 {" +
            "  border-top: 1px solid  #000;" +
            "  border-bottom: 1px solid  #000;" +
            "  color: #000;" +
            "  font-size: 2.4em;" +
            "  line-height: 1.4em;" +
            "  font-weight: normal;" +
            "  text-align: center;" +
            "  margin: 0 0 20px 0;" +
            "}" +
            "h2 {" +
            "  font-size: 32px;" +
            "      padding-left: 25px;" +
            "      border-bottom: 1px solid #000;" +
            "    box-shadow: 0 1px 0 #000;" +
            "}" +
            "#project {" +
            "  float: left;" +
            "      width: 400px;" +
            "      border-right: 1px solid #000;" +
            "          height: 175px;" +
            "          margin-top: -20px;" +
            "    padding-top: 20px;" +
            "}" +
            "#project span {" +
            "    color: #000;" +
            "   " +
            "    width: 52px;" +
            "    margin-right: 30px;" +
            "    display: inline-block;" +
            "    font-size: 15px;" +
            "    font-weight: bold;" +
            "}" +
            "#project span1 {" +
            "    color: #000;" +
            "    margin-right: 30px;" +
            "    display: inline-block;" +
            "    font-size: 15px;" +
            "    font-weight: bold;" +
            "}" +
            "#company {" +
            "    float: right;" +
            "" +
            "    width: 354px;" +
            "    margin-top: -20px;" +
            "    padding-top: 20px;" +
            "}" +
            "#company span" +
            "{" +
            "  font-weight: bold;" +
            "}" +
            "#company span1" +
            "{" +
            "  text-decoration: underline;" +
            "}" +
            "#project div," +
            "#company div {" +
            "  white-space: nowrap;        " +
            "}" +
            "table {" +
            "  width: 100%;" +
            "  border-collapse: collapse;" +
            "  border-spacing: 0;" +
            "  margin-bottom: 20px;" +
            "  border: 1px solid #000;" +
            "}" +
            "table tr:nth-child(2n-1) td {" +
            "  border: 1px solid #000;" +
            "}" +
            "table th," +
            "table td {" +
            "  text-align: center;" +
            "  border: 1px solid #000;" +
            "}" +
            "table th {" +
            "    padding: 5px 1px;" +
            "    color: #5D6975;" +
            "    white-space: pre-line;" +
            "    border: 1px solid #000;" +
            "}" +
            "table .service," +
            "table .desc {" +
            "  text-align: left;" +
            "}" +
            "table td {" +
            "  padding: 5px;" +
            "}" +
            "table td.service," +
            "table td.desc {" +
            "  vertical-align: top;" +
            "}" +
            "" +
            "table td.unit," +
            "table td.qty," +
            "table td.total" +
            "table td.gst {" +
            "  font-size: 1.2em;" +
            "}" +
            "table td.grand {" +
            "  border-top: 1px solid #000;;" +
            "}" +
            "#notices .notice {" +
            "  color: #000;" +
            "  font-size: 1.2em;" +
            "}" +
            "footer {" +
            "  color: #5D6975;" +
            "  width: 100%;" +
            "  height: 30px;" +
            "  position: absolute;" +
            "  bottom: 0;" +
            "  border-top: 1px solid #000;" +
            "  padding: 8px 0;" +
            "  text-align: center;" +
            "}" +
            "#table01 table tr td" +
            "{" +
            "  background: none;" +
            "}" +
            "#table table tr:nth-child(2n-1) td" +
            "{" +
            "  background-color: #000;" +
            "}" +
            ".notice ul li" +
            "{" +
            "  list-style: upper-roman;" +
            "}" +
            "</style>" +
            "<body>" +
            "<div id='background' style='-webkit-print-color-adjust: exact'>" +
            "    <header class='clearfix'>" +
            "        <h1 style='border-top: none !important;'>TAX INVOICE</h1>" +
            "        <div id='company' class='clearfix'>" +
            "            <div><span>Mode of dispatch : </span>" +
            "                <span1> XXXXX </span1>" +
            "            </div>" +
            "            <hr/>" +
            "            <div><span>Number of discription of pages : </span>" +
            "                <span1> XXXXX </span1>" +
            "            </div>" +
            "            <hr/>" +
            "            <div><span>Place of supply : </span>" +
            "                <span1> XXXXX </span1>" +
            "            </div>" +
            "            <hr/>" +
            "            <h2>Bill Number: <span1> ds </span1></h2>" +
            "        </div>" +
            "        <div id='project'>" +
            "            <div style='float: left;'><span>GSTIN : </span> XXXXX</div>" +
            "            <div style='float: right;margin-right: 50px;'><span>PAN : </span> XXXXX</div>" +
            "<div> </div>" +
            "            <div><span>What is Lorem Ipsum?</span></div>" +
            "            <span1 style='overflow-wrap: break-word;'>204,Harsha Complex,F-17,Subhash Chowk,Laxmi Nagar Delhi, India 110092</span1>" +
            "        </div>" +
            "    </header>" +
            "    <div style='margin-top: -40px; border-top: 2px solid #000;'></div>" +
            "    <div style='width: 50.4%;float: left;text-align: center;border-right: 1px solid #000;'>" +
            "        <h1 style='border-top: none !important;'>Billing Address</h1>" +
            "        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and </p>" +
            "        <div id='company'>" +
            "            <div style='width: 50%;float: left;'><span style='float: left;'>Cust GSTIN : </span>" +
            "                <span1 style=''> XXXXX </span1>" +
            "            </div>" +
            "            <div style='width: 50%;float: right;'><span style='float: left;'>State code : </span>" +
            "                <span1 style=''> XXXXX </span1>" +
            "            </div>" +
            "            <div style='height: 20px;'></div>" +
            "        </div>" +
            "    </div>" +
            "    <div style='width: 49.3%;float: right;text-align: center;'>" +
            "        <h1 style='border-top: none !important;'>Shipping Address</h1>" +
            "        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and </p>" +
            "        <div id='company'>" +
            "            <div style='width: 50%;float: left;'><span style='float: left;'>Cust GSTIN : </span>" +
            "                <span1 style=''> XXXXX </span1>" +
            "            </div>" +
            "            <div style='width: 50%;float: right;'><span style='float: left;'>State Code : </span>" +
            "                <span1 style=''> XXXXX </span1>" +
            "            </div>" +
            "            <div style='height: 20px;'></div>" +
            "        </div>" +
            "    </div>" +
            "    <hr/>" +
            "    <main>" +
            "        <table>" +
            "            <thead>" +
            "                <tr>" +
            "                    <td><strong>S.no.</strong></td>" +
            "                    <td style='width: 170px;'><strong>Desccription</strong></td>" +
            "                    <td style='width: 15px;'><strong>HSN code</strong></td>" +
            "                    <td style='width: 5px;'><strong>Qty</strong></td>" +
            "                    <td style='width: 50px;'><strong>Total</strong></td>" +
            "                    <td style='width: 15px;'><strong>Discount</strong></td>" +
            "                    <td style='width: 50px;'><strong>Texable value</strong></td>" +
            "                    <td style='width: 120px;'><strong>GST</strong>" +
            "                        <table style='    margin: 0px -31px -7px -6px;" +
            "    width: 124px;'>" +
            "                            <tr>" +
            "                                <td style='width: 40px;'>" +
            "                                    Rate" +
            "                                </td>" +
            "                                <td>" +
            "                                    Amt." +
            "                                </td>" +
            "                            </tr>" +
            "                        </table>" +
            "                    </td>" +
            "                    <td style='width: 120px;'><strong>SGST</strong>" +
            "                        <table style='    margin: 0px -31px -7px -6px;" +
            "    width: 124px;'>" +
            "                            <tr>" +
            "                                <td style='width: 40px;'>" +
            "                                    sdds" +
            "                                </td>" +
            "                                <td>" +
            "                                    sdds" +
            "                                </td>" +
            "                            </tr>" +
            "                        </table>" +
            "                    </td>" +
            "                    <td style='width: 120px;'><strong>IGST</strong>" +
            "                        <table style='margin: 0px -21px -7px -6px;width: 124px;'>" +
            "                            <tr>" +
            "                                <td>" +
            "                                    sdds" +
            "                                </td>" +
            "                                <td>" +
            "                                    sdds" +
            "                                </td>" +
            "                            </tr>" +
            "                        </table>" +
            "                    </td>" +
            "                </tr>" +
            "            </thead>" +
            "            <tbody>" +
            "                <tr>" +
            "                    <td>1</td>" +
            "                    <td style='text-align: left;'>border-right: 2px solid;border-right </td>" +
            "                    <td class='text-center'>0000</td>" +
            "                    <td class='text-center'>9</td>" +
            "                    <td class='text-center'>26000</td>" +
            "                    <td class='text-center'>15%</td>" +
            "                    <td class='text-center'>12000</td>" +
            "                    <td class='text-center' style='height:100%''>" +
            "                        <table class='text-center' style='height: 104%;" +
            "    border: 0;" +
            "    margin-top: 0px;margin-bottom: 0px !important'>" +
            "                            <tr>" +
            "                                <td class='text-center' style='width: 35px;border: 0;border-right: 1px solid #000;'>A</td>" +
            "                                <td class='text-center' style='border:0;'>B</td>" +
            "                            </tr>" +
            "                        </table>" +
            "                    </td>" +
            "                    <td class='text-center'>18%</td>" +
            "                    <td class='text-center'>18%</td>" +
            "                </tr>" +
            "                <tr>" +
            "                    <td>2</td>" +
            "                    <td style='text-align: left;'>Creating a recognizable design solution based on the company's existing visual identity</td>" +
            "                    <td class='text-center'>0000</td>" +
            "                    <td class='text-center'>9</td>" +
            "                    <td class='text-center'>26000</td>" +
            "                    <td class='text-center'>15%</td>" +
            "                    <td class='text-center'>12000</td>" +
            "                    <td class='text-center'>18%</td>" +
            "                    <td class='text-center'>18%</td>" +
            "                    <td class='text-right'>18%</td>" +
            "                </tr>" +
            "            </tbody>" +
            "        </table>" +
            "        <div id='notices'>" +
            "            <div>NOTICE:</div>" +
            "            <div class='notice'>" +
            "                <ul>" +
            "                    <li>Our responsibility ceases after handling over the goods to the carriers.</li>" +
            "                    <li>24% interest will be charged if the bill is not paid on presentation.</li>" +
            "                    <li>Goods once sold will not be taken back at any cost.</li>" +
            "                    <li>All disputes are subjects to delhi jurisdiction.</li>" +
            "                </ul>" +
            "            </div>" +
            "        </div>" +
            "    </main>" +
            "    </div>" +
            "</body>" +
            "</html>";


}
