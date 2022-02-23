package com.icecool.mylist_xj;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyView extends LinearLayout {
    RecyclerView recycler;
    private adapter_xianji myadapter;
    public List<info_xianji> datalist=new ArrayList<>();
    private final ImageView img_gd;
    GridLayoutManager manager = null;
    private boolean xj_open=false;
    private int xj_clicked=0;//记录当前点击项
    private Context context;

    public MyView(Context context) {
        this(context, null);
        this.context=context;
    }
    @SuppressWarnings("SpellCheckingInspection")
    public void setlist(List<info_xianji> datalist){
        this.datalist=datalist;
        myadapter= new adapter_xianji(context,datalist,recycler);
    }
    public void notifyadapter(){
        myadapter.notifyDataSetChanged();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.myviewlay, this);
        datalist.add(new info_xianji(""));//加一个空数据占位，避免一开始recyclerview没有高度
        recycler=findViewById(R.id.recyclerview);
        img_gd=findViewById(R.id.img_gd);
        myadapter= new adapter_xianji(context,datalist,recycler);
        myadapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, List<info_xianji> datas) {
                // TODO Auto-generated method stub
                for(int i=0;i<datalist.size();i++) {
                    if(i==position) {
                        datalist.get(i).setcheckState(true);
                    }else {
                        datalist.get(i).setcheckState(false);
                    }
                }
                xj_clicked=position;
                myadapter.notifyDataSetChanged();
                Onitemclicklistener.OnMyItemClickListener(parent,view,position,datas);
            }
        });
        setmanager(GridLayoutManager.HORIZONTAL,1);
        recycler.setAdapter(myadapter);
        img_gd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(xj_open) {
                    setmanager(GridLayoutManager.HORIZONTAL,1);
                    MoveToPosition(xj_clicked);
                    img_gd.setImageResource(R.drawable.xiajian);
                    xj_open=false;
                }else {
                    setmanager(GridLayoutManager.VERTICAL,5);
                    img_gd.setImageResource(R.drawable.shangjian);
                    xj_open=true;
                }
                myadapter.notifyDataSetChanged();
            }
        });
    }


    public void setmanager(int orientation,int spanCount ) {

        this.manager = new GridLayoutManager(context,spanCount);// 设置布局管理器
        this.manager.setOrientation(orientation);
        this.recycler.setLayoutManager(manager);

        manager = new GridLayoutManager(context,spanCount);// 设置布局管理器
        manager.setOrientation(orientation);
        recycler.setLayoutManager(manager);

        LayoutParams linearParams =(LayoutParams) recycler.getLayoutParams();
        linearParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
    }


    public void addData(String title,String link){
        this.datalist.add(new info_xianji(title,link));
        this.myadapter.notifyDataSetChanged();
    }

    public void clearData(){
        this.datalist.clear();
        this.myadapter.notifyDataSetChanged();
    }


    public void MoveToPosition(int position) {//滚动到指定位置
        recycler.smoothScrollToPosition(position);
    }


    /***************自定义测试*********自定义回调的三步**********/
    OnMyitemClickListener Onitemclicklistener;  //定义实例

    public void SetOnMyitemClickListener(OnMyitemClickListener Listener){
        this.Onitemclicklistener=Listener;
    }
    public interface OnMyitemClickListener{     //定义接口
        public void OnMyItemClickListener(RecyclerView parent, View view, int position, List<info_xianji> datas);
    }


}
