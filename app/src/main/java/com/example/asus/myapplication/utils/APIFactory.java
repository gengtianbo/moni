package com.example.asus.myapplication.utils;

import com.example.asus.myapplication.bean.CarBean;
import com.example.asus.myapplication.bean.DeleteBean;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by gengtianbo on 2018/6/16.
 */
public class APIFactory {
    private static APIFactory factory = null;

    public static APIFactory getInstance(){
        if(factory==null){
            synchronized (APIFactory.class){
                if(factory==null){
                    factory = new APIFactory();

                }
            }
        }
        return factory;
    }

    public void select(String url, Map<String,String> map, AbstractObserver<CarBean> observer){
        RetrofitUtils.getInstance().select(url,map)
                .subscribeOn( Schedulers.io())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void delete(String url, Map<String,String> map, AbstractObserver<DeleteBean> observer){
        RetrofitUtils.getInstance().select(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
