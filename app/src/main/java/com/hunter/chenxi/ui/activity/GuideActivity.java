package com.hunter.chenxi.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hunter.chenxi.R;
import com.hunter.chenxi.ui.fragment.GuideFragment;

import java.util.List;

import com.google.common.collect.Lists;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

public class GuideActivity extends ActionBarActivity {

    ScrollerViewPager viewPager;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        btnStart = (Button) findViewById(R.id.btnStart);

        PagerModelManager manager = new PagerModelManager();
        manager.addCommonFragment(GuideFragment.class, getBgRes(), getTitles());
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        // just set viewPager
        springIndicator.setViewPager(viewPager);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private List<String> getTitles() {
        return Lists.newArrayList("1", "2", "3", "4");
    }

    private List<Integer> getBgRes() {
        return Lists.newArrayList(R.drawable.guide_bg_1, R.drawable.guide_bg_2, R.drawable.guide_bg_3, R.drawable.guide_bg_1);
    }
}
