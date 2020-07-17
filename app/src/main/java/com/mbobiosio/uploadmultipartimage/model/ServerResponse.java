package com.mbobiosio.uploadmultipartimage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * UploadMultipartImage-Server
 * Created by Mbuodile Obiosio on Jun 07, 2020
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public class ServerResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}