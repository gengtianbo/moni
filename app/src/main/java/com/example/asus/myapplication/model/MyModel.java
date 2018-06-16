package com.example.asus.myapplication.model;

import android.support.annotation.Nullable;

import com.example.asus.myapplication.bean.CarBean;
import com.example.asus.myapplication.utils.APIFactory;
import com.example.asus.myapplication.utils.AbstractObserver;
import com.example.asus.myapplication.utils.MyModelCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * author:Created by gengtianbo on 2018/6/16.
 */
public class MyModel {

    public void select(final MyModelCallBack myModelCallBack) {

        final Map<String,String> map = new HashMap<>();
        map.put("source","android");
        map.put("uid","1650");

        //调用apifactory里面的方法
        APIFactory.getInstance().select("/product/getCarts", map, new AbstractObserver<CarBean>() {
            @Override
            public void onChanged(@Nullable String s) {

            }

            @Override
            public void onSuccess(CarBean cartBean) {
                myModelCallBack.success(cartBean);
            }

            @Override
            public void onFailure() {
                myModelCallBack.failure();
            }
        });
    }

}
