package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.chenxi.R;

/**
 * Created by Ming on 2016/2/18.
 */
public class UserCenterFragment2 extends Fragment {

    private Activity context;
    private  View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_user_center,container,false);
    }

}
