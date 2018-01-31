package com.healthcare.a2040.healthcarepartner.app_interfaces;

import com.healthcare.a2040.healthcarepartner.adapters.DocumentUploadAdapter;
import com.healthcare.a2040.healthcarepartner.obj_holders.DocumentHolder;

/**
 * Created by Amit-PC on 1/18/2018.
 */

public interface DocumentCallBack {
    void onDocumentItemClick(DocumentUploadAdapter.MyHolder myViewHolder, DocumentHolder documentItemHolder, int position);
}
