package com.hunter.chenxi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.google.common.collect.Lists;
import com.hunter.chenxi.R;
import com.hunter.chenxi.app.AppManager;
import com.hunter.chenxi.ui.fragment.GuideFragment;
import com.hunter.chenxi.utils.Utils;

import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

public class GuideActivity extends ActionBarActivity {

    ScrollerViewPager viewPager;
    Button btn_register, btn_login;
    public static GuideActivity guideActivity  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        guideActivity = GuideActivity.this;

        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);

        PagerModelManager manager = new PagerModelManager();
        manager.addCommonFragment(GuideFragment.class, getBgRes(), getTitles());
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        // just set viewPager
        springIndicator.setViewPager(viewPager);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Utils.getContext(), RegisterActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Utils.getContext(), LoginActivity.class));
            }
        });
    }

    private List<String> getTitles() {
        return Lists.newArrayList("1", "2", "3", "4");
    }

    private List<Integer> getBgRes() {
        return Lists.newArrayList(R.drawable.guide_bg_1, R.drawable.guide_bg_2, R.drawable.guide_bg_3, R.drawable.guide_bg_1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishAllActivity();
    }
}
