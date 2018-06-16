package com.example.asus.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.myapplication.view.Liushi;

import java.util.ArrayList;
import java.util.List;

public class SerachActivity extends AppCompatActivity {

    private String mNames[] = {
            "男装","电脑","手机","apple",
            "男神范","经典跑车",
            "超级跑车","阿迪短袖","迪凯斯男装",
            "男士皮鞋"
    };

    private EditText editText;
    private String sname;
    private List<String> list = new ArrayList<>();
    private ListView lv;
    private ArrayList<String> list2 = new ArrayList<>();
    private String string;
    private Liushi mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach);
        editText = findViewById(R.id.edname);

        lv = findViewById(R.id.lv);

        initChildViews();

    }
    private void initChildViews() {

        mFlowLayout = findViewById(R.id.flowlayout);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(

                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for( int i = 0; i < mNames.length; i ++){
            final TextView view1 = new TextView(this);
            view1.setText(mNames[i]);
            view1.setTextColor( Color.RED);
            view1.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            mFlowLayout.addView(view1,lp);


        }


    }


    public void serach(View view) {
        startActivity(new Intent(SerachActivity.this,MainActivity.class));
    }
}
