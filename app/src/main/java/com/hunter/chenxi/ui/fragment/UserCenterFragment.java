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
import com.hunter.chenxi.ui.activity.AboutUsActivity;
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

    @Bind(R.id.txt_card)
    TextView txt_card;

    @Bind(R.id.txt_money)
    TextView txt_money;

    @Bind(R.id.txt_collect)
    TextView txt_collect;

    @Bind(R.id.txt_setting)
    TextView txt_setting;

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



    @OnClick({R.id.view_user,R.id.txt_card,R.id.txt_money,R.id.txt_collect,R.id.txt_setting,R.id.btn_logout
    ,
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_user:
                startActivityForResult(new Intent(getActivity(), UserInfoActivity.class), REQUEST_CODE_USER_PROFILE);
                break;
            case R.id.txt_card:
                Utils.start_Activity(getActivity(), LoginActivity.class);
                break;
            case R.id.txt_money:
                Utils.start_Activity(getActivity(), UserInfoActivityNew.class);
                Utils.toast("待开发...3");
                break;
            case R.id.txt_collect:
                Utils.start_Activity(getActivity(), RegisterActivity.class);
                break;
            case R.id.txt_setting:
                Utils.start_Activity(getActivity(), AboutUsActivity.class);
                break;
            case R.id.btn_logout:
                        Utils.saveBooleanData("loginde", false);
               // Utils.toast("退出成功");
                Utils.start_Activity(getActivity(), LoginActivity.class);
                break;
            /*case R.id.btn_test_register:
                Utils.start_Activity(getActivity(), LoginActivity.class);
                break;
            case R.id.btn_test_login:

                break;*/
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
