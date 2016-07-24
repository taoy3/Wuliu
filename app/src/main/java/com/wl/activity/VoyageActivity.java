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
import com.wl.constant.CacheDataConstant;
import com.wl.util.PagerRadioUtils;
import com.wl.view.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VoyageActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private TextView startPortTv;
    private TextView destPortTv;
    private TextView companyNameTv;
    private AutoScrollViewPager viewPager;

    private AdPagerAdapter adPagerAdapter;
    private ArrayList<String> urls = new ArrayList<>();
    private String voyageScId = "";
    private String startPort = "";
    private String destPort = "";
    private String allStartPort = "";
    private String allDestPort = "";
    private ListView historyLv;
    private ArrayAdapter adapter;
    private List<String> histories = new ArrayList<>();
    private String shipCompanyCode = "";

    @Override
    protected void setView() {
        setContentView(R.layout.activity_voyage);
    }

    @Override
    protected void initView(TextView leftView, TextView titleView, TextView rightView) {
        startPortTv = (TextView) findViewById(R.id.start_port);
        destPortTv = (TextView) findViewById(R.id.dest_port);
        companyNameTv = (TextView) findViewById(R.id.ship_company);
        viewPager = (AutoScrollViewPager) findViewById(R.id.ad);
        urls.add("12");
        urls.add("12");
        urls.add("12");
        adPagerAdapter = new AdPagerAdapter(urls, getLayoutInflater());
        viewPager.setAdapter(adPagerAdapter);
        viewPager.startAutoScroll(1000);
        new PagerRadioUtils(viewPager, (RadioGroup) findViewById(R.id.group)).setChangeListener();
        findViewById(R.id.start_port).setOnClickListener(this);
        findViewById(R.id.dest_port).setOnClickListener(this);
        findViewById(R.id.ship_company).setOnClickListener(this);
        findViewById(R.id.search_voyage).setOnClickListener(this);
        historyLv = (ListView) findViewById(R.id.search_history_list);
    }

    @Override
    protected void initData() {
        getHistoriesData();
        adapter = new ArrayAdapter(this, R.layout.adapter_history, R.id.text, histories);
        historyLv.setAdapter(adapter);
        historyLv.setOnItemClickListener(this);
    }

    private void getHistoriesData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SEARCHVOYAGEHISTORY, Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet(SEARCHVOYAGEHISTORY, null);
        if (set != null && histories.size() == 0) {
            histories.addAll(set);
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
        switch (view.getId()) {
            case R.id.start_port:
                startPort = "";
                startPortTv.setText(startPort);
                startActivityForResult(new Intent(this, SearchStartActivity.class), STARTPORTCODE);
                break;
            case R.id.dest_port:
                destPort = "";
                destPortTv.setText(destPort);
                startActivityForResult(new Intent(this, SearchDestActivity.class), DESTPORTCODE);
                break;
            case R.id.ship_company:
                shipCompanyCode = "";
                companyNameTv.setText(shipCompanyCode);
                startActivityForResult(new Intent(this, SearchScActivity.class), SHIPCOMPANYCODE);
                break;
            case R.id.search_voyage:
                if ((!"".equals(startPort)) && startPort != null && destPort != null && (!"".equals(destPort))) {
                    if (!histories.contains(startPort + "-" + destPort)) {
                        if (histories.size() > 2) {
                            histories.remove(0);
                        }
                        histories.add(startPort + "-" + destPort);
                        adapter.notifyDataSetChanged();
                    }
                    startQuery();
                } else {
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
        if (data != null) {
            switch (resultCode) {
                case STARTPORTCODE:
                    startPort = data.getStringExtra(STARTPORT);
                    startPortTv.setText(startPort);
                    allStartPort = data.getStringExtra(ALLSTARTPORT);
                    break;
                case DESTPORTCODE:
                    destPort = data.getStringExtra(DESTPORT);
                    destPortTv.setText(destPort);
                    allDestPort = data.getStringExtra(ALLDESTPORT);
                    break;
                case SHIPCOMPANYCODE:
                    voyageScId = data.getStringExtra(SCID);
                    shipCompanyCode = data.getStringExtra(CODE);
                    companyNameTv.setText(shipCompanyCode);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SharedPreferences sharedPreferences = getSharedPreferences(SEARCHVOYAGEHISTORY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        HashSet<String> set = new HashSet<>();
        set.addAll(histories);
        editor.putStringSet(SEARCHVOYAGEHISTORY, set);
        editor.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String ports[] = histories.get(i).split("-");
        startPort = ports[0];
        destPort = ports[1];
        startPortTv.setText(ports[0]);
        destPortTv.setText(ports[1]);
        for (int j = 0; j < CacheDataConstant.ports.size(); j++) {
            if (CacheDataConstant.ports.get(j).getName_en().equals(ports[0])) {
                allStartPort = ports[0] + "（" + CacheDataConstant.ports.get(j).getName_zh() + "）";
            }
        }
        for (int j = 0; j < CacheDataConstant.ports.size(); j++) {
            if (CacheDataConstant.ports.get(j).getName_en().equals(ports[1])) {
                allDestPort = ports[1] + "（" + CacheDataConstant.ports.get(j).getName_zh() + "）";
            }
        }
        startQuery();
    }

    private void startQuery() {
        Intent intent = new Intent(this, VoyageListActivity.class);
        intent.putExtra(ALLSTARTPORT, allStartPort);
        intent.putExtra(ALLDESTPORT, allDestPort);
        intent.putExtra(SCID, voyageScId);
        intent.putExtra(STARTPORT, startPort);
        intent.putExtra(DESTPORT, destPort);
        startActivityForResult(intent, LOCKCODE);
    }

}
