package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hunter.chenxi.R;
import com.hunter.chenxi.utils.Utils;

/**
 * Created by Ming on 2016/2/16.
 */
public class UserCenterFragment extends Fragment {

    private Activity context;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_center, container, false);
        Button button = (Button) view.findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.saveBooleanData("loginde", false);
                Utils.toast("退出成功");
            }
        });
        return view;
    }

}
