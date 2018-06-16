package com.example.asus.myapplication.model;

import android.support.annotation.Nullable;

import com.example.asus.myapplication.bean.DeleteBean;
import com.example.asus.myapplication.utils.APIFactory;
import com.example.asus.myapplication.utils.AbstractObserver;
import com.example.asus.myapplication.utils.MyModelCallBack2;

import java.util.HashMap;
import java.util.Map;

/**
 * author:Created by gengtianbo on 2018/6/16.
 */
public class MyModel2 {
    public void delete(String pid,final MyModelCallBack2 myModelCallBack2) {

        final Map<String,String> map = new HashMap<>();
        map.put("source","android");
        map.put("pid",pid);
        map.put("uid","1650");

        //调用apifactory里面的方法
        APIFactory.getInstance().delete("/product/deleteCart", map, new AbstractObserver<DeleteBean>() {
            @Override
            public void onChanged(@Nullable String s) {

            }

            @Override
            public void onSuccess(DeleteBean deleteBean) {
                myModelCallBack2.success(deleteBean);
            }

            @Override
            public void onFailure() {
                myModelCallBack2.failure();
            }
        });
    }
}
