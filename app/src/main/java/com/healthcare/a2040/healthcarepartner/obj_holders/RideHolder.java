package com.healthcare.a2040.healthcarepartner.obj_holders;

import com.healthcare.a2040.healthcarepartner.app_utils.AppConstants;
/**
 * Created by Satnam on 12/11/2017.
 */

public class RideHolder {
    private int rowId = 0;
    private int dataType = AppConstants.LIST_TYPE;
    private String rideId, cabType, userId, fromLat, fromLong, toLat, toLong, bookingDate, totalFare, totalKm, bookingStatus, fromAddress, toAddress;

    public String getCabType() {
        return cabType;
    }

    public void setCabType(String cabType) {
        this.cabType = cabType;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    private Group_Holder gHolder;

    public Group_Holder getgHolder() {
        return gHolder;
    }

    public void setgHolder(Group_Holder gHolder) {
        this.gHolder = gHolder;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFromLat() {
        return fromLat;
    }

    public void setFromLat(String fromLat) {
        this.fromLat = fromLat;
    }

    public String getFromLong() {
        return fromLong;
    }

    public void setFromLong(String fromLong) {
        this.fromLong = fromLong;
    }

    public String getToLat() {
        return toLat;
    }

    public void setToLat(String toLat) {
        this.toLat = toLat;
    }

    public String getToLong() {
        return toLong;
    }

    public void setToLong(String toLong) {
        this.toLong = toLong;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public String getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(String totalKm) {
        this.totalKm = totalKm;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
