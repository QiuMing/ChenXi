package com.hunter.chenxi.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.common.collect.Lists;
import com.hunter.chenxi.R;
import com.hunter.chenxi.app.AppManager;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.ui.fragment.GuideFragment;
import com.hunter.chenxi.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

 public class GuideActivity  extends BaseActivity {

    ScrollerViewPager viewPager;

    @Bind(R.id.btn_register)
    Button btn_register;

    @Bind(R.id.btn_login)
    Button btn_login;

    public static GuideActivity guideActivity  ;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_guide);

        guideActivity = GuideActivity.this;

        handleMaterialStatusBar();

        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);

        PagerModelManager manager = new PagerModelManager();
        manager.addCommonFragment(GuideFragment.class, getBgRes(), getTitles());
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        // just set viewPager
        springIndicator.setViewPager(viewPager);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }

    @OnClick(R.id.btn_login)
    public void btn_login() {
        startActivity(new Intent(Utils.getContext(), LoginActivity.class));
    }

    @OnClick(R.id.btn_register)
    public  void btn_register(){
        startActivity(new Intent(Utils.getContext(), RegisterActivity.class));
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

    /**
     * 状态栏适配
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void handleMaterialStatusBar() {
        Window window = this.getWindow();
        // Not supported in APK level lower than 21
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(0x00000000);
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}
