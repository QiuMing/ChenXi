package com.hunter.chenxi.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.ui.fragment.FindPageFragment;
import com.hunter.chenxi.ui.fragment.HomeFragment;
import com.hunter.chenxi.ui.fragment.PartnerView;
import com.hunter.chenxi.ui.fragment.UserCenterFragment;
import com.hunter.chenxi.utils.Utils;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * 主Activity 包含三个fragment
 */
public class MainActivity extends BaseActivity {

    //底部导航当前指向
    private static int currSel = 0;

    @Bind(R.id.foot_bar_group)
    RadioGroup group;

    private Fragment homeFragment = new HomeFragment();
    private Fragment partnerFragment = new PartnerView();
    private Fragment findPageFrament = new FindPageFragment();
    private Fragment userCenterFragment = new UserCenterFragment();
    private List<Fragment> fragmentList = Arrays.asList(homeFragment, findPageFrament, userCenterFragment);


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        initFootBar();

        initGuide();
    }

    @Override
    public void initPresenter() {

    }

    private void initFootBar() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        currSel = 0;
                        break;
                    case R.id.foot_bar_interest:
                        currSel = 1;
                        break;
                    case R.id.main_footbar_user:
                        currSel = 2;
                        break;

                }
                addFragmentToStack(currSel);
               /* if(currSel == 3) {
                    UIHelper.showLogin(MainActivity.this);
                }*/
            }
        });
        addFragmentToStack(0);
    }

    private void addFragmentToStack(int cur) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        Fragment fragment = fragmentList.get(cur);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment f = fragmentList.get(i);
            if (i == cur && f.isAdded()) {
                fragmentTransaction.show(f);
            } else if (f != null && f.isAdded() && f.isVisible()) {
                fragmentTransaction.hide(f);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 判断是否启动引导页面
     */
    private void initGuide() {
        // || !Utils.getBooleanData("loginde", false)
        if (!Utils.getBooleanData("needGuide", false)) {
            Log.e("Tag","跳到启动引导页面");
            Utils.saveBooleanData("needGuide", true);
            startActivity(new Intent(this, GuideActivity.class));
        }


    }
}
