package com.healthcare.a2040.healthcarepartner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.healthcare.a2040.healthcarepartner.R;
import com.healthcare.a2040.healthcarepartner.app_interfaces.DocumentCallBack;
import com.healthcare.a2040.healthcarepartner.obj_holders.DocumentHolder;

import java.util.ArrayList;

/**
 * Created by Shivam on 8/4/2017.
 */

public class DocumentUploadAdapter extends RecyclerView.Adapter<DocumentUploadAdapter.MyHolder> {

    private ArrayList<DocumentHolder> userList;
    private Context mcontext;
    private DocumentCallBack clickCallBack;


    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView docName, docNameTitle;
        public ProgressBar progressLL;
        public ImageView docItem;
        public View view;


        public MyHolder(View itemView) {
            super(itemView);
            view = itemView;
            progressLL = itemView.findViewById(R.id.progressLL);
            docName = itemView.findViewById(R.id.docName);
            docNameTitle = itemView.findViewById(R.id.docNameTitle);
            docItem = itemView.findViewById(R.id.docItem);

        }
    }

    public DocumentUploadAdapter(Context montext, ArrayList<DocumentHolder> msgArrayList, DocumentCallBack back) {

        this.mcontext = montext;
        this.userList = msgArrayList;
        this.clickCallBack = back;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(mcontext).inflate(R.layout.document_item, parent, false);

        return new MyHolder(myview);
    }


    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final DocumentHolder mOdelq = userList.get(position);

        if (mOdelq.getDocNameTitle() != null) {
            holder.docNameTitle.setText(mOdelq.getDocNameTitle());
        }
        if (mOdelq.getDocument() != null) {
            holder.docItem.setImageBitmap(mOdelq.getDocument());
        }
        if (mOdelq.getFileName() != null && mOdelq.getFileName().length() > 9) {
            holder.docName.setText(mOdelq.getFileName());
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickCallBack.onDocumentItemClick(holder, mOdelq, position);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public int getItemViewType(int position) {


        return super.getItemViewType(position);
    }
}
