package com.mbobiosio.uploadmultipartimage.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.mbobiosio.uploadmultipartimage.R;
import com.mbobiosio.uploadmultipartimage.api.ApiClient;
import com.mbobiosio.uploadmultipartimage.model.ServerResponse;
import com.mbobiosio.uploadmultipartimage.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private  static final int IMAGE = 100;
    ImageView mImageView;
    Button mSelectImage, mUploadImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermission();

        mSelectImage.setOnClickListener(v -> selectImage());
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE && resultCode == RESULT_OK && data != null) {

            Uri resultUri = data.getData();

            mImageView.setImageURI(resultUri);

            uploadImage(resultUri);

        }
    }

    public void getPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },
                1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static MultipartBody.Part prepareFilePart(Context context, String partName, Uri fileUri) {
        File file = FileUtils.getFile(context, fileUri);

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects
                                .requireNonNull(context.getContentResolver().getType(fileUri))),
                        file
                );
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void uploadImage(Uri fileUri) {

        String title = "avatar";

        MultipartBody.Part fileToSend = prepareFilePart(getApplicationContext(), "image_path", fileUri);


        ApiClient.getINSTANCE().upload(title,fileToSend).enqueue(new Callback<List<ServerResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerResponse>> call, @NonNull Response<List<ServerResponse>> response) {

                List<ServerResponse> serverResponses = response.body();

                for (int i = 0; i < serverResponses.size(); i++) {
                    Toast.makeText(
                            getApplicationContext(), serverResponses.get(i).getMessage()
                                    +
                                    "\n\n"
                                    + serverResponses.get(i).getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ServerResponse>> call, @NonNull Throwable t) {

                Log.d("bigo error  :  ",  t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}