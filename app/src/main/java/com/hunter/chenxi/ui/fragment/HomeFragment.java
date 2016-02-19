package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hunter.chenxi.R;
import com.hunter.chenxi.ui.activity.LoginActivity;
import com.hunter.chenxi.ui.activity.RegisterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ming on 2016/2/18.
 */
public class HomeFragment extends Fragment  {

    @Bind(R.id.temp_btnLogin)
    Button btnLogin;

    @Bind(R.id.temp_btnSingin)
    Button btnSigin;

    private Activity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.temp_btnSingin)
    public void btnSingin_onClick() {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
        //Toast.makeText(getActivity(), "You win!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.temp_btnLogin)
    public void btnLogin_onClick() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        //Toast.makeText(getActivity(), "You win!", Toast.LENGTH_SHORT).show();
    }

}
