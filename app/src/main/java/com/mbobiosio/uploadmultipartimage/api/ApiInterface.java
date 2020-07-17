package com.mbobiosio.uploadmultipartimage.api;

import com.mbobiosio.uploadmultipartimage.model.ServerResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * UploadMultipartImage-Server
 * Created by Mbuodile Obiosio on Jun 07, 2020
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public interface ApiInterface {


    @Multipart
    @POST("upload.php")
    Call<ServerResponse> upload(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );

}