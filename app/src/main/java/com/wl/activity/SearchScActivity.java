package com.wl.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wl.BaseApp;
import com.wl.R;
import com.wl.bean.ShipCompanyEntity;
import com.wl.constant.CacheDataConstant;
import com.wl.util.Messaging;
import com.wl.util.SQLUtils;
import com.wl.view.MySearchView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchScActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @ViewInject(R.id.activity_select_ship_company)
    private MySearchView searchView;
    private List<ShipCompanyEntity> companies = CacheDataConstant.companies;
    private List<String> data = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private List<String> hotList;
    private ListView listView;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_ship_company);
    }

    @Override
    protected void initView(TextView leftView, TextView titleView, TextView rightView) {
        if(!BaseApp.companyDataIsOk){
            showBufferDialog();
            Messaging.register(this);
        }
        ViewUtils.inject(this);
        searchView.setHint(R.string.whl);
        searchView.setOnTextContentChange(new MySearchView.OnTextChanged() {
            @Override
            public void onTextChangedListener(CharSequence charSequence, int j, int i1, int i2) {
                data.clear();
                for (int i = 0; i < companies.size(); i++) {
                    if ((companies.get(i).getCode() != null && companies.get(i).getCode().contains(charSequence.toString().trim().toUpperCase()))
                            || (companies.get(i).getCode() != null && companies.get(i).getCode().contains(charSequence.toString().trim().toLowerCase()))
                            || (companies.get(i).getName_zh() != null && companies.get(i).getName_zh().contains(charSequence.toString().trim()))) {
                        data.add(companies.get(i).getCode() + "（" + companies.get(i).getName_zh() + "）");
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
        listView = (ListView) findViewById(R.id.ship_company_list);
    }
    public void onEvent(Integer event,Integer status) {
        if(event== Messaging.SHIPCOMPANY&&status==Messaging.OK){
            bufferDialog.dismiss();
        }
    }

    @Override
    protected void initData() {
        hotList = Arrays.asList(getResources().getStringArray(R.array.ship_company));
        data.addAll(hotList);
        Collections.sort(data);
        adapter = new ArrayAdapter(this, R.layout.adapter_text, R.id.adapter_item, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(view instanceof TextView){
        TextView textView = (TextView) view;
        String[] names = textView.getText().toString().split("（");
        Intent intent = new Intent();
        intent.putExtra(CODE, names[0]);
        intent.putExtra(SCID, SQLUtils.queryScId(names[0]));

        setResult(SHIPCOMPANYCODE, intent);
        finish();}
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Messaging.unregister(this);
    }
}