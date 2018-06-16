package com.example.asus.myapplication.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.R;
import com.example.asus.myapplication.bean.CarBean;
import com.example.asus.myapplication.bean.DeleteBean;
import com.example.asus.myapplication.presenter.MyPresenter;
import com.example.asus.myapplication.presenter.MyPresenter2;
import com.example.asus.myapplication.utils.MyViewCallBack;
import com.example.asus.myapplication.utils.MyViewCallBack2;
import com.example.asus.myapplication.view.CustomJiajian;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:Created by gengtianbo on 2018/6/16.
 */
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyViewHolder> implements MyViewCallBack2,MyViewCallBack {
    MyPresenter myPresenter;
    private List<CarBean.DataBean.ListBean> list;
    private Map<String, String> map = new HashMap<>();

    MyPresenter2 myPresenter2;
    Context context;

    public RecyAdapter(Context context) {
        this.context = context;
        myPresenter = new MyPresenter( this );
        myPresenter2 = new MyPresenter2( this );
        Fresco.initialize( context );
    }

    public void add(CarBean cartBean) {
        if (list == null) {
            list = new ArrayList<>();
        }

        if (cartBean != null) {
            for (CarBean.DataBean shop : cartBean.getData()) {
                map.put( shop.getSellerid(), shop.getSellerName() );
                for (int i = 0; i < shop.getList().size(); i++) {
                    list.add( shop.getList().get( i ) );
                }
            }
            setFirst( list );

        }
        notifyDataSetChanged();
    }

    private void setFirst(List<CarBean.DataBean.ListBean> list) {
        if (list.size() > 0) {
            list.get( 0 ).setIsFirst( 1 );
            for (int i = 1; i < list.size(); i++) {
                if (list.get( i ).getSellerid() == list.get( i - 1 ).getSellerid()) {
                    list.get( i ).setIsFirst( 2 );
                } else {
                    list.get( i ).setIsFirst( 1 );
                    if (list.get( i ).isItem_check() == true) {
                        list.get( i ).setShop_check( list.get( i ).isItem_check() );
                    }
                }
            }
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate( context, R.layout.item1, null );
        MyViewHolder myViewHolder = new MyViewHolder( view );
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (list.get( position ).getIsFirst() == 1) {

            holder.shop_checkbox.setVisibility( View.VISIBLE );
            holder.shop_name.setVisibility( View.VISIBLE );

            holder.shop_checkbox.setChecked( list.get( position ).isShop_check() );
            holder.shop_name.setText( map.get( String.valueOf( list.get( position ).getSellerid() ) ) + " ＞" );
        } else {

            holder.shop_name.setVisibility( View.GONE );
            holder.shop_checkbox.setVisibility( View.GONE );
        }

        String[] split = list.get( position ).getImages().split( "\\|" );
        holder.item_face.setImageURI( Uri.parse( split[0] ) );
        holder.item_checkbox.setChecked( list.get( position ).isItem_check() );
        holder.item_name.setText( list.get( position ).getTitle() );
        holder.item_price.setText( list.get( position ).getPrice() + "" );
        holder.customJiaJian.setEditText( list.get( position ).getNum() );

        holder.item_bianji.setTag( 1 );
        holder.item_bianji.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.item_bianji.getTag();

                if (tag == 1) {
                    holder.item_bianji.setText( "完成" );
                    holder.customJiaJian.setVisibility( View.VISIBLE );
                    holder.item_name.setVisibility( View.GONE );
                    holder.item_yansechima.setVisibility( View.VISIBLE );
                    holder.item_price.setVisibility( View.GONE );
                    holder.item_delete.setVisibility( View.VISIBLE );
                    holder.item_bianji.setTag( 2 );
                } else {
                    holder.item_bianji.setText( "编辑" );
                    holder.customJiaJian.setVisibility( View.GONE );
                    holder.item_name.setVisibility( View.VISIBLE );
                    holder.item_yansechima.setVisibility( View.GONE );
                    holder.item_price.setVisibility( View.VISIBLE );
                    holder.item_delete.setVisibility( View.GONE );
                    holder.item_bianji.setTag( 1 );
                }
            }
        } );
        holder.shop_checkbox.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get( position ).setShop_check( holder.shop_checkbox.isChecked() );

                for (int i = 0; i < list.size(); i++) {
                    if (list.get( position ).getSellerid() == list.get( i ).getSellerid()) {
                        list.get( i ).setItem_check( holder.shop_checkbox.isChecked() );
                    }
                }
                notifyDataSetChanged();
                sum( list );
            }
        } );

        holder.item_checkbox.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get( position ).setItem_check( holder.item_checkbox.isChecked() );
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get( i ).getSellerid() == list.get( j ).getSellerid() && !list.get( j ).isItem_check()) {
                            list.get( i ).setShop_check( false );
                            break;
                        } else {
                            list.get( i ).setShop_check( true );
                        }
                    }
                }

                notifyDataSetChanged();
                sum( list );
            }
        } );

        holder.item_delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove( position );
                setFirst( list );

                sum( list );
                notifyDataSetChanged();
            }
        } );

        holder.customJiaJian.setCustomListener( new CustomJiajian.CustomListener() {
            @Override
            public void jiajian(int count) {
                list.get( position ).setNum( count );
                notifyDataSetChanged();
                sum( list );
            }

            @Override
            public void shuRuZhi(int count) {
                list.get( position ).setNum( count );
                notifyDataSetChanged();
                sum( list );
            }
        } );
    }

    public void shanChu() {
        final List<Integer> delete_listid = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get( i ).isItem_check()) {
                delete_listid.add( list.get( i ).getPid() );
            }
        }
        if (delete_listid.size() == 0) {
            Toast.makeText( context, "请选中至少一个商品后再删除", Toast.LENGTH_SHORT ).show();
            return;
        }
        final AlertDialog.Builder dialog = new AlertDialog.Builder( context );
        dialog.setTitle( "操作提示" );
        dialog.setMessage( "你确定要删除这" + delete_listid.size() + "个商品?" );
        dialog.setPositiveButton( "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String a = "";
                for (int j = 0; j < delete_listid.size(); j++) {

                    Integer integer = delete_listid.get( j );
                    String pid = String.valueOf( integer );
                    myPresenter2.delete( pid );
                }

            }
        } ).setNegativeButton( "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        } ).create().show();
    }

    public void quanXuan(boolean checked) {
        for (int i = 0; i < list.size(); i++) {
            list.get( i ).setShop_check( checked );
            list.get( i ).setItem_check( checked );

        }
        notifyDataSetChanged();
        sum( list );
    }

    private void sum(List<CarBean.DataBean.ListBean> list) {
        int totalNum = 0;
        float totalMoney = 0.0f;
        boolean allCheck = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get( i ).isItem_check()) {
                totalNum += list.get( i ).getNum();
                totalMoney += list.get( i ).getNum() * list.get( i ).getPrice();
            } else {
                allCheck = false;
            }
        }
        updateListener.setTotal( totalMoney + "", totalNum + "", allCheck );
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void success(DeleteBean deleteBean) {
        myPresenter.select();

    }

    @Override
    public void success(CarBean cartBean) {
        list.clear();
        add( cartBean );
    }

    @Override
    public void failure() {
        System.out.println( "网不好" );
        Toast.makeText( context, "adapter网有点慢", Toast.LENGTH_SHORT ).show();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox shop_checkbox;
        private final TextView shop_name;
        private final CheckBox item_checkbox;
        private final TextView item_name;
        private final TextView item_price;
        private final CustomJiajian customJiaJian;
        private final TextView item_delete;
        private final ImageView item_face;
        private final TextView item_bianji;
        private final TextView item_yansechima;


        public MyViewHolder(View itemView) {
            super( itemView );
            shop_checkbox = itemView.findViewById( R.id.shop_checkbox );
            shop_name = itemView.findViewById( R.id.shop_name );
            item_checkbox = itemView.findViewById( R.id.item_checkbox );
            item_name = itemView.findViewById( R.id.item_name );
            item_price = itemView.findViewById( R.id.item_price );
            customJiaJian = itemView.findViewById( R.id.custom_jiajian );
            //item_delete = (ImageView) itemView.findViewById(R.id.item_delete);
            item_delete = itemView.findViewById( R.id.item_delete );

            item_face = itemView.findViewById( R.id.item_face );
            item_bianji = itemView.findViewById( R.id.item_bianji );
            item_yansechima = itemView.findViewById( R.id.item_yansechima );

        }
    }


    UpdateListener updateListener;

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public interface UpdateListener {
        public void setTotal(String total, String num, boolean allCheck);
    }
}