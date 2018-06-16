package com.example.asus.myapplication.utils;

import android.arch.lifecycle.Observer;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.disposables.Disposable;

/**
 * author:Created by gengtianbo on 2018/6/16.
 */
public abstract class AbstractObserver<T> implements Observer<String> {
    public abstract void onSuccess(T t);
    public abstract void onFailure();

    public void onSubscribe(Disposable d) {

    }


    public void onNext(String result) {

        Type type = getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        Class clazz = (Class) types[0];

        Gson gson = new Gson();
        T t = (T) gson.fromJson(result,clazz);


        onSuccess(t);
    }


    public void onError(Throwable e) {
        try {
            onFailure();

        }catch (Exception e1){
            e.printStackTrace();
        }
    }


    public void onComplete() {

    }
}
