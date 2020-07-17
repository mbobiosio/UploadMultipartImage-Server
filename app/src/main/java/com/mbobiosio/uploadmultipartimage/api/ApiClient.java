package com.mbobiosio.uploadmultipartimage.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mbobiosio.uploadmultipartimage.model.ServerResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * UploadMultipartImage-Server
 * Created by Mbuodile Obiosio on Jun 07, 2020
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public class ApiClient {

    private static final String BASE_URL = "";
    private ApiInterface apiInterface;
    private static ApiClient INSTANCE;

    public ApiClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiClient getINSTANCE() {
        if (null == INSTANCE){
            INSTANCE = new ApiClient();
        }
        return INSTANCE;
    }

    public Call<List<ServerResponse>> upload(String title, MultipartBody.Part picFile){
        return apiInterface.storePost(title,picFile);
    }

}