package com.hunter.chenxi.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * 主Activity 包含多个fragment
 */
public class MainActivity extends BaseActivity {

    //底部导航当前指向
    private static int currSel = 0;

    @Bind(R.id.foot_bar_group)
    RadioGroup group;

    @Bind(R.id.temp_btnlogin)
    Button btnLogin;

    @Bind(R.id.temp_btnsingin)
    Button btnSigin;

    private Fragment homeFragment = new Fragment();
    private Fragment imFragment = new Fragment();
    private Fragment interestFragment = new Fragment();
    private Fragment memberFragment = new Fragment();
    private List<Fragment> fragmentList = Arrays.asList(homeFragment, imFragment, interestFragment, memberFragment);

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        initFootBar();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }

        });

        btnSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                MainActivity.this.finish();

                //Toast.makeText(MainActivity.this, "实现了点击", Toast.LENGTH_SHORT).show();
            }

        });
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
                    case R.id.foot_bar_im:
                        currSel = 1;
                        break;
                    case R.id.foot_bar_interest:
                        currSel = 2;
                        break;
                    case R.id.main_footbar_user:
                        currSel = 3;
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
}
