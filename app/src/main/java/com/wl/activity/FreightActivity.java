package com.wl.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wl.R;
import com.wl.adapter.AdPagerAdapter;
import com.wl.util.PagerRadioUtils;
import com.wl.util.SQLUtils;
import com.wl.view.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FreightActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private TextView startPortTv;
    private TextView destPortTv;
    private AutoScrollViewPager viewPager;
    private AdPagerAdapter adPagerAdapter;
    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> histories = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<>();
    private String startPort = "";
    private String destPort ="";
    private String allStartPort = "";
    private String allDestPort ="";
    private ArrayAdapter<String> adapter;
    private ListView historyLv;
    private String startId ="";
    private String destId = "";

    @Override
    protected void setView() {
        setContentView(R.layout.activity_freight);
    }

    @Override
    protected void initView(TextView leftView, TextView titleView, TextView rightView) {
        startPortTv = (TextView)findViewById(R.id.start_port);
        destPortTv = (TextView)findViewById(R.id.dest_port);
        viewPager = (AutoScrollViewPager)findViewById(R.id.ad);
        urls.add("12");
        urls.add("12");
        urls.add("12");
        adPagerAdapter = new AdPagerAdapter(urls,getLayoutInflater());
        viewPager.setAdapter(adPagerAdapter);
        viewPager.startAutoScroll(1000);
        new PagerRadioUtils(viewPager, (RadioGroup)findViewById(R.id.group)).setChangeListener();
        findViewById(R.id.start_port).setOnClickListener(this);
        findViewById(R.id.dest_port).setOnClickListener(this);
        findViewById(R.id.search_price).setOnClickListener(this);
        historyLv = (ListView)findViewById(R.id.search_history_list);
        getHistoriesData();
        adapter = new ArrayAdapter(this,R.layout.adapter_history,R.id.text,histories);
        historyLv.setAdapter(adapter);
        historyLv.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
    }

    private void getHistoriesData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SEARCHPRICEHISTORY, Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet(SEARCHPRICEHISTORY, null);
        if (set!=null&&histories.size()==0){
            histories.addAll(set);
        }
        Set<String> ids = sharedPreferences.getStringSet(SEARCHPRICEHISTORYID,null);
        if (ids!=null&&idList.size()==0){
            idList.addAll(ids);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        startPortTv.setText(startPort);
        destPortTv.setText(destPort);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_port:
                startPort = "";
                startPortTv.setText(startPort);
                startActivityForResult(new Intent(this, SearchStartActivity.class), STARTPORTCODE);
                break;
            case R.id.dest_port:
                destPort = "";
                destPortTv.setText(destPort);
                startActivityForResult(new Intent(this, SearchDestActivity.class),DESTPORTCODE);
                break;
            case R.id.search_price:
                if((!"".equals(startPort))&&startPort!=null&&destPort!=null&&(!"".equals(destPort))){
                    if(!histories.contains(startPort+"-"+destPort)){
                        if(histories.size()>2){
                            histories.remove(0);
                            idList.remove(0);
                        }
                        histories.add(startPort+"-"+destPort);
                        idList.add(startId+"/"+destId);
                        adapter.notifyDataSetChanged();
                    }
                    startQuery();
                }else {
                    Toast.makeText(this, R.string.please_enter_the_port_information, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            switch (resultCode){
                case STARTPORTCODE:
                    startPort = data.getStringExtra(STARTPORT);
                    startPortTv.setText(startPort);
                    allStartPort = data.getStringExtra(ALLSTARTPORT);
                    startId = SQLUtils.queryPortId(startPort.trim());
                    break;
                case DESTPORTCODE:
                    destPort = data.getStringExtra(DESTPORT);
                    destPortTv.setText(destPort);
                    allDestPort = data.getStringExtra(ALLDESTPORT);
                    destId = SQLUtils.queryPortId(destPort.trim());
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SharedPreferences sharedPreferences = getSharedPreferences(SEARCHPRICEHISTORY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        HashSet<String> set = new HashSet<>();
        set.addAll(histories);
        editor.putStringSet(SEARCHPRICEHISTORY, set);
        HashSet<String> ids = new HashSet<>();
        ids.addAll(idList);
        editor.putStringSet(SEARCHPRICEHISTORYID, ids);
        editor.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String ports[] = histories.get(i).split("-");
        startPort = ports[0];
        destPort = ports[1];
        destPortTv.setText(ports[1]);
        startPortTv.setText(ports[0]);
        allStartPort=startPort;
        allDestPort=destPort;
        startId = idList.get(i).split("/")[0];
        destId = idList.get(i).split("/")[1];
        startQuery();
    }

    private void startQuery() {
        Intent intent = new Intent(this, FreightListActivity.class);
        intent.putExtra(ALLSTARTPORT,allStartPort);
        intent.putExtra(ALLDESTPORT, allDestPort);
        intent.putExtra(STARTPORT,startId);
        intent.putExtra(DESTPORT,destId);
        startActivityForResult(intent, LOCKCODE);
    }
}
