package com.wl.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.wl.R;
import com.wl.adapter.VoyageDetailAdapter;
import com.wl.constant.CacheDataConstant;
import com.wl.util.DateUtils;

public class VoyageDetailActivity extends BaseActivity {
    private TextView companyNameTv;
    private TextView startPortZh;
    private TextView startPortTv;
    private TextView clsTv;
    private TextView etdTv;
    private TextView destPortTv;
    private TextView etaTv;
    private TextView ttTv;
    private ListView listView;
    private String startPort;
    private String destPort;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_voyage_detail);
    }
    @Override
    protected void initView(TextView leftView,TextView titleView,TextView rightView) {
        listView = (ListView) findViewById(R.id.port_list);
        companyNameTv = (TextView) findViewById(R.id.company_name);
        startPortZh = (TextView) findViewById(R.id.start_port_zh);
        startPortTv = (TextView) findViewById(R.id.start_port);
        clsTv = (TextView) findViewById(R.id.cls);
        etdTv = (TextView) findViewById(R.id.etd);
        destPortTv = (TextView) findViewById(R.id.dest_port);
        etaTv = (TextView) findViewById(R.id.eta);
        ttTv = (TextView) findViewById(R.id.tt);
    }
    @Override
    protected void initData() {
        startPort = getIntent().getStringExtra(STARTPORT);
        destPort = getIntent().getStringExtra(DESTPORT);
        companyNameTv.setText(getScCode(CacheDataConstant.voyageEntity.getSc_name()));
        startPortZh.setText(getNameZh(CacheDataConstant.voyageEntity.getPorts().get(0).getPort_name())
                + "/" + CacheDataConstant.voyageEntity.getSchema());
        startPortTv.setText(startPort);
        clsTv.setText("CLS:" + DateUtils.changeDateFormat(CacheDataConstant.voyageEntity.getPorts().get(0).getCls()));
        etdTv.setText("ETD:" + DateUtils.changeDateFormat(CacheDataConstant.voyageEntity.getPorts().get(0).getEtd()));
        destPortTv.setText(destPort);
        etaTv.setText("ETA:" + DateUtils.changeDateFormat(CacheDataConstant.voyageEntity.getPorts().get(0).getEta()));
        ttTv.setText("TT:" + (CacheDataConstant.voyageEntity.getPorts().get(CacheDataConstant.voyageEntity.getPorts().size() - 1).getTt() - CacheDataConstant.voyageEntity.getPorts().get(0).getTt()) + "天");
        listView.setAdapter(new VoyageDetailAdapter(this, CacheDataConstant.voyageEntity.getPorts(), startPort, destPort));
    }

    private String getNameZh(String port_name) {
        String name_zh = "";
        for (int i = 0; i < CacheDataConstant.ports.size(); i++) {
            if (CacheDataConstant.ports.get(i).getName_en().equals(port_name)) {
                name_zh = CacheDataConstant.ports.get(i).getName_zh();
            }
        }
        return name_zh;
    }

    private String getScCode(String sc_name) {
        String sc_code = "";
        for (int i = 0; i < CacheDataConstant.companies.size(); i++) {
            if (CacheDataConstant.companies.get(i).getName_zh().equals(sc_name)) {
                sc_code = CacheDataConstant.companies.get(i).getCode();
            }
        }
        return sc_code;
    }
}
