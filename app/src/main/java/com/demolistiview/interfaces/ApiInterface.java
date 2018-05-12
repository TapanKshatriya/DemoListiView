package com.demolistiview.interfaces;


import com.demolistiview.model.ImageList;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("breed/airedale/images")
    Call<ImageList> getImageList();
}