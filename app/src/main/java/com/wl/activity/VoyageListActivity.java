package com.wl.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wl.R;
import com.wl.adapter.VoyageAdapter;
import com.wl.bean.VoyageEntity;
import com.wl.constant.CacheDataConstant;

import java.util.ArrayList;
import java.util.List;

public class VoyageListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private VoyageAdapter adapter;
    private List<VoyageEntity> entities = new ArrayList<>();
    private int page = 1;
    private int total;
    private TextView startPortTv;
    private TextView destPortTv;
    private String scId = "";
    private String startPort;
    private String destPort;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_voyage_list);
    }
    @Override
    protected void initView(TextView leftView,TextView titleView,TextView rightView) {
        Intent intent = getIntent();
        scId = intent.getStringExtra(SCID);
        startPort = intent.getStringExtra(STARTPORT);
        destPort = intent.getStringExtra(DESTPORT);
        startPortTv = (TextView) findViewById(R.id.start_port);
        startPortTv.setText(startPort);
        destPortTv = (TextView) findViewById(R.id.dest_port);
        destPortTv.setText(destPort);
        listView = (ListView) findViewById(R.id.activity_search_voyage_list);
        adapter = new VoyageAdapter(this, entities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
    @Override
    protected void initData() {
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(entities.get(i) instanceof VoyageEntity){
            CacheDataConstant.voyageEntity = entities.get(i);
            Intent intent = new Intent(this, VoyageDetailActivity.class);
            intent.putExtra(STARTPORT, startPort);
            intent.putExtra(DESTPORT, destPort);
            startActivityForResult(intent, LOCKCODE);
        }
    }
}
