package com.example.asus.myapplication.presenter;

import com.example.asus.myapplication.bean.CarBean;
import com.example.asus.myapplication.model.MyModel;
import com.example.asus.myapplication.utils.MyModelCallBack;
import com.example.asus.myapplication.utils.MyViewCallBack;

/**
 * author:Created by gengtianbo on 2018/6/16.
 */
public class MyPresenter {

    MyModel myModel;
    MyViewCallBack myViewCallBack;


    public MyPresenter(MyViewCallBack myViewCallBack) {
        this.myViewCallBack = myViewCallBack;
        this.myModel = new MyModel();
    }


    public void select() {
        myModel.select(new MyModelCallBack() {
            @Override
            public void success(CarBean cartBean) {
                myViewCallBack.success(cartBean);
            }

            @Override
            public void failure() {
                myViewCallBack.failure();
            }
        });
    }

    public void detach() {
        this.myViewCallBack = null;
    }

}
