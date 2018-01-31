package com.healthcare.a2040.healthcarepartner.obj_holders;

import android.graphics.Bitmap;

/**
 * Created by Amit-PC on 1/18/2018.
 */

public class DocumentHolder {
    Bitmap document;
    String fileName,docNameTitle,serverPath;

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getDocNameTitle() {
        return docNameTitle;
    }

    public void setDocNameTitle(String docNameTitle) {
        this.docNameTitle = docNameTitle;
    }

    public Bitmap getDocument() {
        return document;
    }

    public String getFileName() {
        return fileName;
    }

    public void setDocument(Bitmap document) {
        this.document = document;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
