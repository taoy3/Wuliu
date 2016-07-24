package com.wl.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.wl.R;
import com.wl.adapter.SearchAdapter;
import com.wl.bean.CustomerBean;
import com.wl.constant.UrlConstant;
import com.wl.netBean.GeoUtil;
import com.wl.util.AppHttpUtils;
import com.wl.view.MySearchView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class CusSearchActivity extends BaseActivity implements MySearchView.OnTextChanged, AdapterView.OnItemClickListener {
    private MySearchView searchView;
    private ListView listView;
    protected List<CustomerBean> list = new ArrayList<>();//搜索出的结果存放
    private BaseAdapter adapter;
    private List<CustomerBean> data = new ArrayList<>();//搜索数据源
    private GifImageView listEmptyV;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivityForResult(new Intent(this, CusDetailActivity.class)
                .putExtra(CONTACTBEAN, list.get(position)), LOCKCODE);
    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void initView(TextView leftView, TextView titleView, TextView rightView) {
        searchView = (MySearchView) findViewById(R.id.search);
        searchView.setOnTextContentChange(this);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        listEmptyV = (GifImageView) getLayoutInflater().inflate(R.layout.view_empty,null);
        ((ViewGroup)listView.getParent()).addView(listEmptyV);
        listView.setEmptyView(listEmptyV);
        listEmptyV.setImageResource(R.mipmap.no_msg);
    }

    @Override
    protected void initData() {
        adapter = new SearchAdapter(this,list);
        listView.setAdapter(adapter);
        AppHttpUtils.getUtils(UrlConstant.getCustomersUrl(),new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what/100==2&&msg.obj!=null&&msg.obj.toString().trim().length()>0) {
                    try {
                        String json = new JSONObject(msg.obj.toString()).getString(RESULT);
                        if (json != null) {
                            List<CustomerBean> temp = new GeoUtil().deserializer(json, new TypeToken<List<CustomerBean>>() {
                            }.getType());
                            if (temp != null && temp.size() > 0) {
                                data.clear();
                                data.addAll(temp);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        bufferDialog.dismiss();
                    }
                }
                bufferDialog.dismiss();
            }
        });
    }

    @Override
    public void onTextChangedListener(CharSequence charSequence, int i, int i1, int i2) {
        for(CustomerBean item:data){
            if(item.getName_cn().contains(charSequence.toString().toLowerCase())
                    ||item.getId().contains(charSequence.toString().toUpperCase())
                    ||item.getName_en().contains(charSequence.toString().toLowerCase())
                    ||item.getName_en().contains(charSequence.toString().toUpperCase())
                    ||item.getMobile().contains(charSequence.toString())
                    ||item.getTel().contains(charSequence.toString())){
                list.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchCancel() {
        list.clear();
        adapter.notifyDataSetChanged();
    }
}
