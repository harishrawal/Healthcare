package com.healthcare.a2040.healthcarepartner.ui_activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthcare.a2040.healthcarepartner.R;
import com.healthcare.a2040.healthcarepartner.adapters.MultiViewTypeAdapeter;
import com.healthcare.a2040.healthcarepartner.app_interfaces.CallBacks;
import com.healthcare.a2040.healthcarepartner.app_utils.AppConstants;
import com.healthcare.a2040.healthcarepartner.app_utils.CustomMethods;
import com.healthcare.a2040.healthcarepartner.custom_fonts.Text_view_light;
import com.healthcare.a2040.healthcarepartner.local_storage.DB_Helper;
import com.healthcare.a2040.healthcarepartner.obj_holders.Group_Holder;
import com.healthcare.a2040.healthcarepartner.obj_holders.RideHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends SuperCompatActivity {

//    Url : test.2040healthcare.com/app/update_user_device.php
//    Parameters : client_id,device_id

    private final String TAG = RequestConfirmationStatus.class.getName();

    private CustomMethods mCustomMethods;


    private Context mContext;
    private DB_Helper mHelper;
    private RecyclerView mRecyclerView;
    ArrayList<RideHolder> rideHolders;
    MultiViewTypeAdapeter bookingAdapters;
    //Text_view_light nothingFoundTV;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mContext = DashboardActivity.this;
        mCustomMethods = new CustomMethods(mContext);

        Uri gmmIntentUri = Uri.parse("google.navigation:q=28.6213239,77.0355638");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

        rideHolders = new ArrayList<>();
        RecyclerView mRecyclerView = findViewById(R.id.recyclerV);

      //  nothingFoundTV = findViewById(R.id.nothingFoundTV);
      //  nothingFoundTV.setVisibility(View.GONE);

        bookingAdapters = new MultiViewTypeAdapeter(rideHolders, mContext, new CallBacks() {
            @Override
            public void result(String response) {
                try {

                    JSONObject bundle = new JSONObject(response);

                    switch (bundle.getString("action")) {
                        case "CANCEL_RIDE":

                            cancelRideRequest(bundle.getString("requestId"));

                            break;
                        case "PAYMENT":

                            // paymentOfRide(bundle.getString("requestId"));

                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
//       mRecyclerView.setLayoutManager(new MyCustomLayoutManager(mContext, LinearLayoutManager.VERTICAL,true));
        mRecyclerView.setLayoutManager(new

                LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(bookingAdapters);

        if (mCustomMethods.isConnectedToInternet())

        {

            getUserRides();

        } else

        {

            // displayFromLocal();
        }
    }


    protected void displayFromLocal() {

//        int ccountRides = mHelper.getTableRowsCount("select count (*) from " + DB_Helper.t_Booking + " where bookingStatus = '" + AppConstants.S_LEAD_ACCEPTED + "' ");
//
//        String totalEarning = mHelper.getSumOfTablesRow(DB_Helper.t_Booking, "total_Fare", "  bookingStatus = '" + AppConstants.S_LEAD_ACCEPTED + "' ");
//
//        String[] lastTrip = mHelper.get_ThreeColumn_SIngleReturn(DB_Helper.t_Booking, new String[]{"fromAddress", "total_Km", "total_Fare"}, "bookingStatus = '" + AppConstants.S_LEAD_ACCEPTED + "'");

        RideHolder holderGroup = new RideHolder();
        Group_Holder gh = new Group_Holder();
        gh.setLastTripText("Your Last trip from ");
        gh.setLastTripFrom("9");
        gh.setLastTripKm("32");
        gh.setLastTripKm("23");
        gh.setTotalRides(String.valueOf("daw"));
        gh.setTotalEarnings("Rs 43.00");

        holderGroup.setgHolder(gh);
        holderGroup.setDataType(AppConstants.GROUP_TYPE);
        rideHolders.add(holderGroup);

        Cursor cur = mHelper.executeWithReturn("select * from table name order by id desc ");
        if (cur != null && cur.moveToFirst()) {
            do {
                RideHolder holder = new RideHolder();
                holder.setDataType(AppConstants.LIST_TYPE);
                holder.setRowId(cur.getInt(cur.getColumnIndex("id")));
                holder.setRideId(cur.getString(cur.getColumnIndex("Requestid")));
                holder.setBookingDate(cur.getString(cur.getColumnIndex("BookingDate")));
                holder.setBookingStatus(cur.getString(cur.getColumnIndex("bookingStatus")));
                holder.setFromLat(cur.getString(cur.getColumnIndex("fromLat")));
                holder.setFromAddress(cur.getString(cur.getColumnIndex("fromAddress")));
                holder.setToAddress(cur.getString(cur.getColumnIndex("toAddress")));
                holder.setFromLong(cur.getString(cur.getColumnIndex("fromLong")));
                holder.setToLat(cur.getString(cur.getColumnIndex("toLat")));
                holder.setToLong(cur.getString(cur.getColumnIndex("toLong")));
                holder.setTotalFare(cur.getString(cur.getColumnIndex("total_Fare")));
                holder.setTotalKm(cur.getString(cur.getColumnIndex("total_Km")));
                holder.setCabType(cur.getString(cur.getColumnIndex("cabName")));
                rideHolders.add(holder);

            } while (cur.moveToNext());
        }

        bookingAdapters.notifyDataSetChanged();

        checkDataLength();

    }

    private void checkDataLength() {

        if (rideHolders.size() > 0) {

            //nothingFoundTV.setVisibility(View.GONE);

        } else {
         //   nothingFoundTV.setVisibility(View.VISIBLE);

        }
    }

    protected void getUserRides() {

//        Volley.newRequestQueue(mContext).add(stringRequest);
    }

    private void handleResponse(String historyResponse) {

        try {
            JSONObject parentObj = new JSONObject(historyResponse);
            if (parentObj.has("result") && parentObj.getInt("result") == 1) {

                JSONArray childArray = parentObj.getJSONArray("data");

                for (int z = 0; z < childArray.length(); z++) {

                    JSONObject childObj = childArray.getJSONObject(z);

                    String requestid = childObj.getString("request_id");
                    String fromLatlong = childObj.getString("from");
                    String toLatlong = childObj.getString("to");
                    String amount = childObj.getString("amount");
                    String km = childObj.getString("km");
                    String driverMobile = childObj.getString("phone");
                    String drivername = childObj.getString("dr_phone");
                    String cabname = childObj.getString("cabname");
                    String cabtype = childObj.getString("cabtype");
                    String status = childObj.getString("status") + "";
                    String date = childObj.getString("date") + "";
                    String from_address = childObj.getString("from_address");
                    String to_address = childObj.getString("to_address");

                    String fromLat = "", fromLon = "";
                    if (fromLatlong.contains(",")) {
                        String fm[] = fromLatlong.split(",");
                        fromLat = fm[0];
                        fromLon = fm[1];
                    }
                    String toLat = "", toLon = "";
                    if (toLatlong.contains(",")) {
                        String tml[] = toLatlong.split(",");
                        toLat = tml[0];
                        toLon = tml[1];
                    }

                    String SQL_INSERT = "insert into tablename (Requestid, " +
                            "userId, " +
                            "fromAddress, " +
                            "fromLat, " +
                            "fromLong, " +
                            "toAddress, " +
                            "toLat, " +
                            "toLong," +
                            " BookingDate, " +
                            "total_Fare, " +
                            "total_Km, " +
                            "bookingStatus, " +
                            "driverName, " +
                            "driverPhone, " +
                            "cabName, " +
                            "RTONo)" +
                            " values ('" +
                            requestid + "','" +
                            userId + "','" +
                            from_address + "','" +
                            fromLat + "','" +
                            fromLon + "','" +
                            to_address + "','" +
                            toLat + "','" +
                            toLon + "','" +
                            date + "','" +
                            amount + "','" +
                            km + "','" +
                            AppConstants.S_LEAD_COMPLETED + "','" +
                            drivername + "','" +
                            driverMobile + "','" +
                            cabtype + "',' ')";
//                            cabname + "',' ')";

//                    String SQL_DELETE = "delete from " + DB_Helper.t_Booking + " where Requestid='" + requestid + "' ";
//
//                    Log.e("SQL_DELETE", SQL_DELETE);
//
//                    mHelper.executeWithoutReturn(SQL_DELETE);
//
//                    Log.e("SQL_INSERT", SQL_INSERT);
//
//                    mHelper.executeWithoutReturn(SQL_INSERT);
                }


            } else {
//               / mMethods.msgbox("Nothing Found");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        displayFromLocal();

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }

    private void cancelRideRequest(String reqId) {

        //mHomePage.cancelRideRequest(reqId);

    }


}
