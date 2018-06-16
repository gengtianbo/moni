package com.example.asus.myapplication.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author:Created by gengtianbo on 2018/6/16.
 */
public class RetrofitUtils {

    private static IGetService service = null;

    //http://api.tianapi.com/nba/?key=71e58b5b2f930eaf1f937407acde08fe&num=10
    //单例模式
    public static IGetService getInstance(){
        if(service==null){
            synchronized (RetrofitUtils.class){
                if(service==null){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://120.27.23.105")
                            .addConverterFactory( GsonConverterFactory.create())
                            .addCallAdapterFactory( RxJava2CallAdapterFactory.create())
                            .client(OkHttpUtils.getInstance())
                            .build();

                    service = retrofit.create(IGetService.class);
                }
            }
        }
        return service;
    }

}
