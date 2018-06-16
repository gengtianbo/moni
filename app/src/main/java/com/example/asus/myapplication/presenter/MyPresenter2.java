package com.example.asus.myapplication.presenter;

import com.example.asus.myapplication.bean.DeleteBean;
import com.example.asus.myapplication.model.MyModel2;
import com.example.asus.myapplication.utils.MyModelCallBack2;
import com.example.asus.myapplication.utils.MyViewCallBack2;

/**
 * author:Created by gengtianbo on 2018/6/16.
 */
public class MyPresenter2 {

    MyModel2 myModel2;
    MyViewCallBack2 myViewCallBack2;

    public MyPresenter2(MyViewCallBack2 myViewCallBack2) {
        this.myViewCallBack2 = myViewCallBack2;
        this.myModel2 = new MyModel2();

    }


    public void delete(String pid) {
        myModel2.delete( pid, new MyModelCallBack2() {
            @Override
            public void success(DeleteBean deleteBean) {
                myViewCallBack2.success( deleteBean );
            }

            @Override
            public void failure() {
                myViewCallBack2.failure();
            }
        } );
    }

    public void detach() {
        this.myViewCallBack2 = null;
    }

}
