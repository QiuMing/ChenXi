package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.app.Constants;
import com.hunter.chenxi.ui.activity.FeedbackActivity;
import com.hunter.chenxi.ui.activity.GuideActivity;
import com.hunter.chenxi.ui.activity.LoginActivity;
import com.hunter.chenxi.ui.activity.RegisterActivity;
import com.hunter.chenxi.ui.activity.UserInfoActivity;
import com.hunter.chenxi.ui.activity.UserInfoActivityNew;
import com.hunter.chenxi.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * Created by Ming on 2016/2/16.
 */
public class UserCenterFragment extends Fragment {

    private Activity context;
    private View view;

    @Bind(R.id.txt_login_test)
    TextView txt_card;

    @Bind(R.id.txt_user_info_test)
    TextView txt_money;

    @Bind(R.id.txt_register_test)
    TextView txt_collect;

    @Bind(R.id.txt_setting)
    TextView txt_setting;

    @Bind(R.id.txt_about_us)
    TextView txt_about;

    @Bind(R.id.txt_guide_test)
    TextView txt_guide;

    @Bind(R.id.btn_logout)
    Button btnLogout;

    @Bind(R.id.head)
    ImageView imgUserHead;

    public static final int REQUEST_CODE_USER_PROFILE = 2001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_center,container,false);
        ButterKnife.bind(this,view);
        UserInfoActivity.updatePhoto(imgUserHead);
        return view;
    }

    @OnClick({R.id.view_user,
            R.id.txt_login_test,
            R.id.txt_user_info_test,
            R.id.txt_register_test,
            R.id.txt_setting,
            R.id.btn_logout,
            R.id.txt_guide_test,
            R.id.txt_about_us
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_user:
                startActivityForResult(new Intent(getActivity(), UserInfoActivity.class), REQUEST_CODE_USER_PROFILE);
                break;
            case R.id.txt_login_test:
                Utils.start_Activity(getActivity(), LoginActivity.class);
                break;
            case R.id.txt_user_info_test:
                Utils.start_Activity(getActivity(), UserInfoActivityNew.class);
                break;
            case R.id.txt_register_test:
                Utils.start_Activity(getActivity(), RegisterActivity.class);
                break;
            case R.id.txt_guide_test:
                Utils.start_Activity(getActivity(), GuideActivity.class);
                break;
            case R.id.txt_setting:
                Utils.toast(Constants.TOAST_STRING);
                break;
            case R.id.txt_about_us:
                Utils.toast("跳转到测试的页面");
                Utils.start_Activity(getActivity(), FeedbackActivity.class);
                break;
            case R.id.btn_logout:
                //Utils.saveBooleanData("loginde", false);
                Utils.clearUserData();
                Utils.start_Activity(getActivity(), LoginActivity.class);
                break;
            default:
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_USER_PROFILE:
                UserInfoActivity.updatePhoto(imgUserHead);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
