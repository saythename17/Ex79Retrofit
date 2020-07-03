package com.icandothisallday2020.ex79retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static Retrofit getRetrofitInstatnce(){
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl("http://soon0.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        return retrofit;
    }
}
