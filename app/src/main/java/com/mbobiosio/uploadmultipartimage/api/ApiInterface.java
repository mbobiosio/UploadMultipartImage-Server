package com.mbobiosio.uploadmultipartimage.api;

import com.mbobiosio.uploadmultipartimage.model.ServerResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * UploadMultipartImage-Server
 * Created by Mbuodile Obiosio on Jun 07, 2020
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public interface ApiInterface {


    @Multipart
    @POST("upload.php")
    Call<List<ServerResponse>> storePost(@Part ("title") String title, @Part MultipartBody.Part image);

}