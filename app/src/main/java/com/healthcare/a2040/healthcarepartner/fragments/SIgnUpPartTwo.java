package com.healthcare.a2040.healthcarepartner.fragments;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthcare.a2040.healthcarepartner.R;
import com.healthcare.a2040.healthcarepartner.adapters.DocumentUploadAdapter;
import com.healthcare.a2040.healthcarepartner.app_interfaces.DocumentCallBack;
import com.healthcare.a2040.healthcarepartner.app_utils.AppConstants;
import com.healthcare.a2040.healthcarepartner.app_utils.CustomMethods;
import com.healthcare.a2040.healthcarepartner.controllers.SignUpController;
import com.healthcare.a2040.healthcarepartner.obj_holders.DocumentHolder;
import com.healthcare.a2040.healthcarepartner.obj_holders.UserInformationHolder;
import com.healthcare.a2040.healthcarepartner.ui_activities.RequestConfirmationStatus;
import com.healthcare.a2040.healthcarepartner.ui_activities.SignUpActivity;
import com.healthcare.a2040.healthcarepartner.ui_activities.StepperSignupActivity;
import com.healthcare.a2040.healthcarepartner.ui_activities.SuperCompatActivity;
import com.healthcare.a2040.healthcarepartner.volley.VolleyMultipartRequest;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Amit-PC on 1/18/2018.
 */

public class SIgnUpPartTwo extends Fragment implements BlockingStep, DocumentCallBack {

    private RecyclerView recyclerView;
    private Context mContext;
    private DocumentUploadAdapter documentUploadAdapter;
    private ArrayList<DocumentHolder> documentHolderArrayList;
    private CustomMethods mCustomMethods;
    private final int REQUEST_CODE_ = 29, PICK_IMAGE = 32;

    private DocumentUploadAdapter.MyHolder currentHolder;
    private int currentPosition;
    private SignUpController signUpController;

    public SIgnUpPartTwo() {
    }


    public static SIgnUpPartTwo newInstance() {
        SIgnUpPartTwo fragment = new SIgnUpPartTwo();
        Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_stepper_signup, container, false);

        mContext = getActivity();
        mCustomMethods = new CustomMethods(mContext);
        signUpController = new SignUpController(mContext);

        recyclerView = rootView.findViewById(R.id.documentRec);

        documentHolderArrayList = new ArrayList<>();

        DocumentHolder documentHolder = new DocumentHolder();
        documentHolder.setDocNameTitle("Aadhaar Card");

        DocumentHolder documentHolder2 = new DocumentHolder();
        documentHolder2.setDocNameTitle("Address Proof");

        DocumentHolder documentHolder3 = new DocumentHolder();
        documentHolder3.setDocNameTitle("Voter ID");

        DocumentHolder documentHolder4 = new DocumentHolder();

        documentHolderArrayList.add(documentHolder);
        documentHolderArrayList.add(documentHolder2);
        documentHolderArrayList.add(documentHolder3);
//        documentHolderArrayList.add(documentHolder4);

        documentUploadAdapter = new DocumentUploadAdapter(mContext, documentHolderArrayList, this);

//        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(documentUploadAdapter);
        documentUploadAdapter.notifyDataSetChanged();
        return rootView;
    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        if (documentHolderArrayList.size() < 2) {
            mCustomMethods.msgbox("List Blank");
            return;
        }
        DocumentHolder dh1 = documentHolderArrayList.get(0);
        DocumentHolder dh2 = documentHolderArrayList.get(1);
        DocumentHolder dh3 = documentHolderArrayList.get(2);

        if (dh1.getDocument() != null && dh2.getDocument() != null) {

            mCustomMethods.msgbox("Uploaded");

        } else {

            mCustomMethods.msgbox("Not Uploaded");

        }

        if (StepperSignupActivity.getUserInfo() != null) {

            UserInformationHolder informationHolder = StepperSignupActivity.getUserInfo();
            informationHolder.setAddressPrrofPath(dh1.getServerPath());
            informationHolder.setAddressPrrofPath(dh2.getServerPath());
            informationHolder.setVoterIdPath(dh3.getServerPath());

            StepperSignupActivity.setUserInfo(informationHolder);

            startActivity(new Intent(mContext, RequestConfirmationStatus.class)
                    .putExtra("submitData", true));



        } else {
            mCustomMethods.msgbox("First Not Found");
        }
//signUpController.su

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onDocumentItemClick(DocumentUploadAdapter.MyHolder myViewHolder,
                                    DocumentHolder documentItemHolder,
                                    int position) {

        currentHolder = myViewHolder;
        currentPosition = position;

        if ((ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_);

        } else {

            startActivityForResult(getPickImageChooserIntent(), PICK_IMAGE);

        }


    }

    private class DocUploader implements Runnable {
        DocumentUploadAdapter.MyHolder adapterVHolder;
        int position;
        Uri fileURI;

        public DocUploader(DocumentUploadAdapter.MyHolder holder, int posi, Uri fileUri) {
            this.adapterVHolder = holder;
            this.position = posi;
            this.fileURI = fileUri;
        }

        @Override
        public void run() {
            VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppConstants.S_IS_LOGGED_IN,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mCustomMethods.msgbox("Uploaded " + position);
                                    adapterVHolder.progressLL.setVisibility(View.GONE);
                                }
                            });
                            Log.i("Error", response.toString());

                            try {
                                String resultResponse = new String(response.data);
                                int result = Integer.parseInt(resultResponse);
//                                  HANDLE RESULT
//                                loading.dismiss();
                            } catch (NumberFormatException e) {
//                                loading.dismiss();

                            } catch (Exception e) {
//                                loading.dismiss();

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCustomMethods.msgbox("Error- in Uploading " + position);
                            adapterVHolder.progressLL.setVisibility(View.GONE);
                        }
                    });


                    NetworkResponse networkResponse = error.networkResponse;
                    String errorMessage = "Unknown error";
                    if (networkResponse == null) {
                        if (error.getClass().equals(TimeoutError.class)) {
                            errorMessage = "Request timeout";
                        } else if (error.getClass().equals(NoConnectionError.class)) {
                            errorMessage = "Failed to connect server";
                        }
                    } else {
                        String result = new String(networkResponse.data);
                        try {
                            JSONObject response = new JSONObject(result);
                            String status = response.getString("status");
                            String message = response.getString("message");

                            Log.e("Error Status", status);
                            Log.e("Error Message", message);

                            if (networkResponse.statusCode == 404) {
                                errorMessage = "Resource not found";
                            } else if (networkResponse.statusCode == 401) {
                                errorMessage = message + " Please login again";
                            } else if (networkResponse.statusCode == 400) {
                                errorMessage = message + " Check your inputs";
                            } else if (networkResponse.statusCode == 500) {
                                errorMessage = message + " Something is getting wrong";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.i("Error", errorMessage);
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
//                    params.put("folder_name", MODEL_NAME);
//                    params.put("gps_latitude", mLatitudeTextView.getText().toString().trim());
//                    params.put("gps_longitude", mLongitudeTextView.getText().toString().trim());
                    return params;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    try {
                        InputStream iStream = mContext.getContentResolver().openInputStream(fileURI);
                        byte[] inputData = getBytes(iStream);
                        String randomFIleName = mCustomMethods.current_time(true);

//                        params.put(MODEL_NAME + i, new DataPart(MODEL_NAME + i + ".jpg", inputData, "image/jpeg"));
                        params.put("", new DataPart(randomFIleName + "_doc_file.jpg", inputData, "image/jpeg"));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(multipartRequest);

        }

        public byte[] getBytes(InputStream inputStream) throws IOException {
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

            Uri dataUri = getPickImageResultUri(data);

            if (dataUri != null) {

                mCustomMethods.msgbox("Uploading started" + currentPosition);
                currentHolder.progressLL.setVisibility(View.VISIBLE);

                new Thread(new DocUploader(currentHolder, currentPosition, dataUri)).start();

            } else {

                mCustomMethods.msgbox("Doc Not Found");
            }


        }
    }


    private void convertImage(final Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] faceByte_arr = stream.toByteArray();

    }

    public Intent getPickImageChooserIntent() {

        List<Intent> allIntents = new ArrayList();

        PackageManager packageManager = mContext.getPackageManager();

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);

            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);
        // Create a chooser from the main intent
        /// Intent chooserIntent = Intent.createChooser(new Intent(Intent.ACTION_CAMERA_BUTTON), "Select source");
        Intent chooserIntent = Intent.createChooser(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {

        Uri outputFileUri = null;

        File getImage = mContext.getExternalCacheDir();

        if (getImage != null) {

            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }

        return outputFileUri;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                startActivityForResult(getPickImageChooserIntent(), PICK_IMAGE);

            } else {

                Toast.makeText(mContext, "Please allow camera to access", Toast.LENGTH_LONG).show();
            }

        }

    }

    private Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {
        mContext.getContentResolver().notifyChange(selectedImage, null);

        ExifInterface ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }

    }

    private Bitmap rotateImage(Bitmap img, int degree) {

        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();

        return rotatedImg;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {

        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    /**
     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.
     * <p/>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }


}