package com.hunter.chenxi.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.utils.Utils;

import butterknife.Bind;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.imgHead)
    ImageView imgHead;
    @Bind(R.id.textName)
    TextView textName;
    @Bind(R.id.textSex)
    TextView textSex;
    @Bind(R.id.textAge)
    TextView textAge;
    @Bind(R.id.textHeight)
    TextView textheight;
    @Bind(R.id.textWeight)
    TextView textWeight;
    @Bind(R.id.textBMI)
    TextView textBMI;
    @Bind(R.id.textProfession)
    TextView textProfession;
    @Bind(R.id.textPhysical)
    TextView textPhysical;
    @Bind(R.id.textAppeal)
    TextView textAppeal;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_user_info);
    }

    @Override
    public void initView() {
        imgHead.setOnClickListener(this);
        textName.setOnClickListener(this);
        textSex.setOnClickListener(this);
        textAge.setOnClickListener(this);
        textheight.setOnClickListener(this);
        textWeight.setOnClickListener(this);
        textBMI.setOnClickListener(this);
        textProfession.setOnClickListener(this);
        textPhysical.setOnClickListener(this);
        textAppeal.setOnClickListener(this);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //这个用的时候再改
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgHead:
                Utils.toast("imgHead");
                break;
            case R.id.textName:
                Utils.toast("textName");
                break;
            case R.id.textSex:
                break;
            case R.id.textAge:
                break;
            case R.id.textHeight:
                break;
            case R.id.textWeight:
                break;
            case R.id.textBMI:
                break;
            case R.id.textProfession:
                break;
            case R.id.textPhysical:
                break;
            case R.id.textAppeal:
                break;

        }
    }
}
