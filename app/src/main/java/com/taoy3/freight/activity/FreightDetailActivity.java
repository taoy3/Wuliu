package com.taoy3.freight.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taoy3.freight.R;
import com.taoy3.freight.adapter.BoxViewAdapter;
import com.taoy3.freight.adapter.SurchargesAdapter;
import com.taoy3.freight.bean.PriceEntity;
import com.taoy3.freight.bean.SurchargesEntity;
import com.taoy3.freight.util.DateUtils;
import com.taoy3.freight.view.ChildGridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FreightDetailActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.start_port)
    private TextView startTv;
    @ViewInject(R.id.dest_port)
    private TextView destTv;

    @ViewInject(R.id.companyName)
    private TextView companyNameTv;
    @ViewInject(R.id.st_port)
    private TextView st_portTv;
    @ViewInject(R.id.line_port)
    private TextView line_portTv;
    @ViewInject(R.id.mark)
    private TextView markTv;
    @ViewInject(R.id.transit)
    private TextView transitTv;
    @ViewInject(R.id.validity)
    private TextView validityTv;
    @ViewInject(R.id.line_long)
    private TextView line_longTv;

    @ViewInject(R.id.price_details_list)
    private ChildGridView surchargesLv;
    private TextView expandTv;
    private Drawable addDrawable;
    private Drawable minusDrawable;
    private ChildGridView gridView;

    private boolean flag;
    private PriceEntity bean;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_freight_detail);
    }

    @Override
    protected void initView(TextView leftView, TextView titleView, TextView rightView) {
        ViewUtils.inject(this);

        rightView.setText(R.string.order);
        rightView.setOnClickListener(this);
        gridView = (ChildGridView) findViewById(R.id.freight_box);
        expandTv = (TextView) findViewById(R.id.price_details_expand);
        addDrawable = getResources().getDrawable(android.R.drawable.ic_input_add);
        minusDrawable = getResources().getDrawable(R.mipmap.ic_find_previous_holo_light);
        expandTv.setOnClickListener(this);
        surchargesLv = (ChildGridView) findViewById(R.id.price_details_list);
    }

    @Override
    protected void initData() {
        bean = (PriceEntity) getIntent().getSerializableExtra(FREIGHT);
        initBeanData();
    }

    private void initBeanData() {
        startTv.setText(bean.getStartPort());
        destTv.setText(bean.getDestPort());
        companyNameTv.setText("船公司：" + bean.getSc_code() + "");
        if (bean.getCls() != 0) {
            st_portTv.setText("截关/开船：" + DateUtils.changeDateFormat(bean.getCls())
                    + "-" + DateUtils.changeDateFormat(bean.getEtd()));
        }
        if (bean.getLine() != null) {
            line_portTv.setText("航线：" + bean.getLine());
        }
        line_longTv.setText("航程：" + bean.getTt() + "天");
        List<String> boxList = new ArrayList<>();
        gridView.setNumColumns(bean.getBoxBeans().size());
        for (int i = 0; i < bean.getBoxBeans().size(); i++) {
            boxList.add(i,bean.getBoxBeans().get(i).getName());
            boxList.add(bean.getBoxBeans().get(i).getValue()+"");
        }
        gridView.setAdapter(new BoxViewAdapter(this,boxList));
        markTv.setText(bean.getRemarks());
        transitTv.setText(bean.getVia()+"");
        validityTv.setText(bean.getSvalid() + "至" + bean.getEvalid());
        ArrayList<SurchargesEntity> surcharges = new ArrayList<>();
        Collections.sort(bean.getSurcharges());
        surcharges.addAll(bean.getSurcharges());
        SurchargesAdapter adapter = new SurchargesAdapter(this, surcharges);
        surchargesLv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.price_details_expand:
                if (flag) {
                    surchargesLv.setVisibility(View.GONE);
                    expandTv.setCompoundDrawablesWithIntrinsicBounds(addDrawable, null, null, null);
                } else {
                    surchargesLv.setVisibility(View.VISIBLE);
                    expandTv.setCompoundDrawablesWithIntrinsicBounds(minusDrawable, null, null, null);
                }
                flag = !flag;
                break;
            case R.id.title_right:
                Intent intent = new Intent(this, BookActivity.class);
                intent.putExtra(FREIGHT,bean);
                startActivityForResult(intent, LOCKCODE);
                break;
            default:
                break;
        }

    }
}
