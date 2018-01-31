package com.healthcare.a2040.healthcarepartner.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthcare.a2040.healthcarepartner.R;
import com.healthcare.a2040.healthcarepartner.app_interfaces.CallBacks;
import com.healthcare.a2040.healthcarepartner.app_utils.AppConstants;
import com.healthcare.a2040.healthcarepartner.app_utils.CustomMethods;
import com.healthcare.a2040.healthcarepartner.obj_holders.Group_Holder;
import com.healthcare.a2040.healthcarepartner.obj_holders.RideHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dell on 21-12-2017.
 */

public class MultiViewTypeAdapeter extends RecyclerView.Adapter {

    private ArrayList<RideHolder> dataSet;
    int total_types;
    private static Context mContext;
    private int positionClick = -1;
    private CallBacks callBacks;
    private CustomMethods mCustomMethods;

    public MultiViewTypeAdapeter(ArrayList<RideHolder> data, Context context, CallBacks callBacks) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
        this.callBacks = callBacks;
        this.mCustomMethods = new CustomMethods(mContext);
    }


    public static class ListTypeViewHolder extends RecyclerView.ViewHolder {

        TextView fromAdd1, ToAdd1, bookingStatus, current_day_date, car_name_number;
        //  TextView textKm, textFare;
        TextView expandIcon;
        LinearLayout linearExtraLL, cancelRidelL, payNowlL, closeExtralL;

        public ListTypeViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            fromAdd1 = view.findViewById(R.id.fromAdd1);
            ToAdd1 = view.findViewById(R.id.ToAdd1);
//            textKm = view.findViewById(R.id.textKm);
//            textFare = view.findViewById(R.id.textFare);
            bookingStatus = view.findViewById(R.id.bookingStatus);
            expandIcon = view.findViewById(R.id.expandIcon);
            linearExtraLL = view.findViewById(R.id.linearExtraLL);
            cancelRidelL = view.findViewById(R.id.cancelRidelL);
            payNowlL = view.findViewById(R.id.payNowlL);
            closeExtralL = view.findViewById(R.id.closeExtralL);
            current_day_date = view.findViewById(R.id.current_day_date);
            car_name_number = view.findViewById(R.id.car_name_number);

        }
    }

    public static class GroupDataTypeViewHolder extends RecyclerView.ViewHolder {

        TextView curentTextRideId;
        TextView curentTextRideIdDetail, totalRides, totalEarnings;

        public GroupDataTypeViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();

            this.curentTextRideId = itemView.findViewById(R.id.curentTextRideId);
            this.curentTextRideIdDetail = itemView.findViewById(R.id.curentTextRideIdDetail);
            this.totalRides = itemView.findViewById(R.id.totalRides);
            this.totalEarnings = itemView.findViewById(R.id.totalEarnings);

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case AppConstants.LIST_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_layout, parent, false);
                return new ListTypeViewHolder(view);
            case AppConstants.GROUP_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_ride_details, parent, false);
                return new GroupDataTypeViewHolder(view);

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        return dataSet.get(position).getDataType();
//
//        switch (dataSet.get(position).getDataType()) {
//            case 0:
//                return AppConstants.LIST_TYPE;
//            case 1:
//                return AppConstants.GROUP_TYPE;
//
//            default:
//                return -1;
//        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        final RideHolder singleModelHolder = dataSet.get(listPosition);

        if (singleModelHolder != null) {

            switch (singleModelHolder.getDataType()) {

                case AppConstants.LIST_TYPE:

                    ListTypeViewHolder listViewHolder = ((ListTypeViewHolder) holder);

                    listViewHolder.fromAdd1.setText(singleModelHolder.getFromAddress());
                    listViewHolder.ToAdd1.setText(singleModelHolder.getToAddress());
//                    listViewHolder.textKm.setText(singleModelHolder.getTotalKm());
//                    listViewHolder.textFare.setText(singleModelHolder.getTotalFare());
                    listViewHolder.current_day_date.setText(Html.fromHtml("<html> <body>" +
                            "<p> <b>" + getdayDate(singleModelHolder.getBookingDate()) + "</b> </p> </body>" +
                            "</html>"));

                    listViewHolder.car_name_number.setText(Html.fromHtml("<html> <body>" +
                            "<p> <b>" + singleModelHolder.getCabType() + "</b> </p> </body>" +
                            "</html>"));

                    switch (singleModelHolder.getBookingStatus()) {

                        case AppConstants.S_LEAD_ACCEPTED:

                            listViewHolder.bookingStatus.setText("ACCEPTED");
                            listViewHolder.bookingStatus.setTextColor(Color.parseColor("#FF3F9755"));

                            listViewHolder.expandIcon.setVisibility(View.VISIBLE);
                            listViewHolder.expandIcon.setClickable(true);
                            break;
                        case AppConstants.S_LEAD_COMPLETED:
                            listViewHolder.bookingStatus.setText("DONE");
                            listViewHolder.bookingStatus.setTextColor(Color.parseColor("#b7263238"));

                            listViewHolder.expandIcon.setVisibility(View.INVISIBLE);
                            listViewHolder.expandIcon.setClickable(false);
                            break;
                        case AppConstants.S_LEAD_CANCELLED:
                            listViewHolder.bookingStatus.setText("CANCELLED");
                            listViewHolder.bookingStatus.setTextColor(Color.parseColor("#FFD4544D"));

                            listViewHolder.expandIcon.setVisibility(View.INVISIBLE);
                            listViewHolder.expandIcon.setClickable(false);

                            break;

                    }
                    listViewHolder.expandIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (positionClick > 0) {

                                positionClick = -1;

                            } else {

                                positionClick = listPosition;

                            }
                            notifyDataSetChanged();
                        }
                    });
                    listViewHolder.cancelRidelL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // cancelRide here
                            if (callBacks != null) {

                                try {
                                    JSONObject bundle = new JSONObject();
                                    bundle.put("action", "CANCEL_RIDE");
                                    bundle.put("position", listPosition);
                                    bundle.put("requestId", singleModelHolder.getRideId());
                                    callBacks.result(bundle.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                    listViewHolder.payNowlL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // pay now
                            if (callBacks != null) {

                                try {
                                    JSONObject bundle = new JSONObject();
                                    bundle.put("action", "PAYMENT");
                                    bundle.put("position", listPosition);
                                    bundle.put("requestId", singleModelHolder.getRideId());
                                    callBacks.result(bundle.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    listViewHolder.closeExtralL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            positionClick = -1;
                            notifyDataSetChanged();
                        }
                    });

                    if (positionClick == listPosition) {

                        listViewHolder.linearExtraLL.setVisibility(View.VISIBLE);

                    } else {

                        listViewHolder.linearExtraLL.setVisibility(View.GONE);
                    }

                    break;
                case AppConstants.GROUP_TYPE:

                    Group_Holder groupHolder = singleModelHolder.getgHolder();

                    GroupDataTypeViewHolder groupViewHolder = ((GroupDataTypeViewHolder) holder);
                    groupViewHolder.totalEarnings.setText(groupHolder.getTotalEarnings());
                    groupViewHolder.totalRides.setText(groupHolder.getTotalRides());
                    groupViewHolder.curentTextRideIdDetail.setText(groupHolder.getLastTripKm() + " - " + groupHolder.getLastTripFrom());
                    groupViewHolder.curentTextRideId.setText(groupHolder.getLastTripText());


                    break;

            }
        }
    }

    private String getdayDate(String yyyymmdd) {
        return "";
//        return mCustomMethods.convertDateToDay(yyyymmdd) + ", " + yyyymmdd;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}