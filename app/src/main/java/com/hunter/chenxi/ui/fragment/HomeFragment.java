package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hunter.chenxi.R;
import com.hunter.chenxi.ui.activity.LoginActivity;
import com.hunter.chenxi.ui.activity.RegisterActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ming on 2016/2/18.
 */
public class HomeFragment extends Fragment {

    @Bind(R.id.temp_btnLogin)
    Button btnLogin;

    @Bind(R.id.temp_btnSingin)
    Button btnSigin;

    @Bind(R.id.temp_btnSleep)
    Button btnSleep;

    @Bind(R.id.temp_btnPedometer)
    Button btnPedometer;

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

    @OnClick(R.id.temp_btnSleep)
    public void btnSleep_onClick() {
        //获得ServiceManager类
        try {
            //获得ServiceManager类
            Class<?> ServiceManager = Class
                    .forName("android.os.ServiceManager");

            //获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);

            //调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null,Context.POWER_SERVICE);

            //获得IPowerManager.Stub类
            Class<?> cStub = Class
                    .forName("android.os.IPowerManager$Stub");
            //获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            //调用asInterface方法获取IPowerManager对象
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            //获得shutdown()方法
            Method shutdown = oIPowerManager.getClass().getMethod("shutdown",boolean.class,boolean.class);
            //调用shutdown()方法
            shutdown.invoke(oIPowerManager,false,true);

        } catch (Exception e) {
            Log.e("TAG", e.toString(), e);
            e.printStackTrace();
        }

//        Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
//        intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }

    @OnClick(R.id.temp_btnPedometer)
    public void btnPedometer_onClick() {
//        Intent intent = new Intent(context, LoginActivity.class);
//        context.startActivity(intent);
        //Toast.makeText(getActivity(), "You win!", Toast.LENGTH_SHORT).show();
    }
}
