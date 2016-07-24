package com.wl.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wl.BaseApp;
import com.wl.R;
import com.wl.bean.PortsEntity;
import com.wl.constant.CacheDataConstant;
import com.wl.util.Messaging;
import com.wl.view.MySearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchStartActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private List<PortsEntity> ports = CacheDataConstant.ports;
    private List<String> data = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private List<String> hotList;
    private ListView listView;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_start);
    }
    @Override
    protected void initView(TextView leftView,TextView titleView,TextView rightView) {
        if(!BaseApp.companyDataIsOk){
            showBufferDialog();
            Messaging.register(this);
        }
        MySearchView searchView = (MySearchView) findViewById(R.id.start_searchView);
        searchView.setHint(R.string.please_enter_the_port_information);
        searchView.setOnTextContentChange(new MySearchView.OnTextChanged() {
            @Override
            public void onTextChangedListener(CharSequence charSequence, int j, int j1, int i2) {
                data.clear();
                    for (int i = 0; i < ports.size(); i++) {
                        if ((ports.get(i).getName_en() != null && ports.get(i).getName_en().contains(charSequence.toString().trim().toUpperCase()))
                                || (ports.get(i).getName_en() != null && ports.get(i).getName_en().contains(charSequence.toString().trim().toLowerCase()))
                                || (ports.get(i).getName_zh() != null && ports.get(i).getName_zh().contains(charSequence.toString().trim()))
                                || (ports.get(i).getCode() != null && ports.get(i).getCode().contains(charSequence.toString().trim()))) {
                            data.add(ports.get(i).getName_en() + "（" + ports.get(i).getName_zh() + "）");
                        }
                    }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onSearchCancel() {
                data.clear();
                data.addAll(hotList);
                adapter.notifyDataSetChanged();
            }
        });
        listView = (ListView) findViewById(R.id.start_port);

    }
    public void onEvent(Integer event,Integer status) {
        if(event==Messaging.PORTEVENT&&status==Messaging.OK){
            bufferDialog.dismiss();
        }
    }

    @Override
    protected void initData() {
        hotList = Arrays.asList(getResources().getStringArray(R.array.hot_start_port));
        data.addAll(hotList);
        adapter = new ArrayAdapter(this, R.layout.adapter_array, R.id.text, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(view instanceof TextView){
        TextView textView = (TextView) view;
        String[] names = textView.getText().toString().split("（");
        Intent intent = new Intent();
        intent.putExtra(ALLSTARTPORT, textView.getText().toString());
        intent.putExtra(STARTPORT, names[0].trim());
        setResult(STARTPORTCODE, intent);
        finish();}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Messaging.unregister(this);
    }
}
