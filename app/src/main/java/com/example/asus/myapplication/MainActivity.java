package com.example.asus.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.adapter.RecyAdapter;
import com.example.asus.myapplication.bean.CarBean;
import com.example.asus.myapplication.presenter.MyPresenter;
import com.example.asus.myapplication.utils.MyViewCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MyViewCallBack {
    @BindView(R.id.bianji)
    TextView bianji;
    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    @BindView(R.id.quanxuan)
    CheckBox quanxuan;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.total_num)
    TextView totalNum;
    @BindView(R.id.quzhifu)
    TextView quzhifu;
    @BindView(R.id.shanchu)
    TextView shanchu;
    @BindView(R.id.linear_shanchu)
    LinearLayout linearShanchu;

    private MyPresenter myPresenter;
    private RecyAdapter recyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bianji.setTag(1);
        quanxuan.setTag(1);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);

        myPresenter = new MyPresenter(this);
        recyAdapter = new RecyAdapter(this);
        myPresenter.select();

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recyAdapter);

        recyAdapter.setUpdateListener(new RecyAdapter.UpdateListener() {
            @Override
            public void setTotal(String total, String num, boolean allCheck) {
                totalNum.setText("共"+num+"件商品");
                totalPrice.setText("总价 :¥"+total+"元");
                if(allCheck){
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }else{
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }
                quanxuan.setChecked(allCheck);
            }
        });

        quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tag = (int) quanxuan.getTag();
                if(tag==1){
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }else{
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }

                recyAdapter.quanXuan(quanxuan.isChecked());
            }
        });

        shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyAdapter.shanChu();
            }
        });

        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) bianji.getTag();
                if(tag==1){
                    bianji.setText("完成");
                    bianji.setTag(2);
                    quzhifu.setVisibility(View.GONE);
                    linearShanchu.setVisibility(View.VISIBLE);
                }else{
                    bianji.setText("编辑");
                    bianji.setTag(1);
                    quzhifu.setVisibility(View.VISIBLE);
                    linearShanchu.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void success(CarBean cartBean) {
        if(cartBean!=null) {
            recyAdapter.add(cartBean);
        }
    }

    @Override
    public void failure() {
        System.out.println("网不好");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "网有点慢", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myPresenter.detach();
    }

}
