package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hunter.chenxi.R;
import com.hunter.chenxi.ui.activity.StepCounterActivity;
import com.hunter.chenxi.ui.activity.UserInfoActivity;
import com.hunter.chenxi.ui.custom.pulltozoomview.PullToZoomScrollViewEx;
import com.hunter.chenxi.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ming on 2016/2/18.
 */
public class HomeFragment extends Fragment {

    private Activity context;

    private View root;

    private PullToZoomScrollViewEx scrollView;

    private ImageView imgUserHead;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        scrollView = (PullToZoomScrollViewEx) root.findViewById(R.id.scrollView);
        View headView = LayoutInflater.from(context).inflate(R.layout.member_head_view, null, false);
        View zoomView = LayoutInflater.from(context).inflate(R.layout.member_zoom_view, null, false);
        View contentView = LayoutInflater.from(context).inflate(R.layout.member_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);

        //1、获取Preferences
        SharedPreferences settings = getActivity().getSharedPreferences("BMI", 0);
        //2、取出数据
        String bmi = settings.getString("bmi","尚未检测BMI");
        //String url = setting.getString(“URL”,”default”);

        TextView txt_bmi = (TextView) headView.findViewById(R.id.header_txt_bmi);
        txt_bmi.setText("BMI:" + bmi);

        Calendar cal= Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        TextView txt_day =(TextView)headView.findViewById(R.id.txt_day);
        txt_day.setText(String.valueOf(day));

        Date date=new Date();
        SimpleDateFormat dateMonth = new SimpleDateFormat("MM");
        String month = dateMonth.format(date);

        TextView txt_month =(TextView)headView.findViewById(R.id.txt_month);
        txt_month.setText(month+"月");


        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String weekday = dateFm.format(date);

        TextView txt_weekday =(TextView)headView.findViewById(R.id.txt_weekday);
        txt_weekday.setText(weekday);

      /*  headView.findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toast("" + Utils.getBooleanData("loginde", false));
                if (!Utils.getBooleanData("loginde", false)) {
                    startActivity(new Intent(context, LoginActivity.class));
                } else {
                    Utils.toast("登录成功");
                    startActivity(new Intent(Utils.getContext(), UserInfoActivity.class));
                }
            }
        });*/

        imgUserHead = (ImageView)headView.findViewById(R.id.iv_user_head);
        UserInfoActivity.updatePhoto(imgUserHead);

        //睡
       scrollView.getPullRootView().findViewById(R.id.btn_seelp).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Utils.toast("待开发...");

               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               //builder.setIcon(R.drawable.ic_launcher);
               builder.setTitle("主人，你想做什么");
               final String[] actions = {"我要早睡", "我要早起"};
               builder.setItems(actions, new DialogInterface.OnClickListener()
               {
                   @Override
                   public void onClick(DialogInterface dialog, int which)
                   {
                       Toast.makeText(getActivity(), "选择的城市为：" + actions[which], Toast.LENGTH_SHORT).show();
                   }
               });
               builder.show();


           }
       });

        //食
        scrollView.getPullRootView().findViewById(R.id.btn_eat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //builder.setIcon(R.drawable.ic_launcher);
                builder.setTitle("请输入食物名字");
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(view);

                final EditText username = (EditText)view.findViewById(R.id.txt_txt2);
                final EditText password = (EditText)view.findViewById(R.id.txt_txt1);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String a = username.getText().toString().trim();
                        String b = password.getText().toString().trim();
                        //    将输入的用户名和密码打印出来
                        Toast.makeText(getActivity(), "用户名: " + a + ", 密码: " + b, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                builder.show();
            }
        });

        //动
        scrollView.getPullRootView().findViewById(R.id.btn_exercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Utils.getContext(), StepCounterActivity.class));
            }
        });
    }


   /* @OnClick(R.id.btn_eat)
    public void btnSingin_onClick() {
        //Intent intent = new Intent(context, RegisterActivity.class);
        //context.startActivity(intent);
        Toast.makeText(getActivity(), "You win!", Toast.LENGTH_SHORT).show();
    }*/

/*
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
    }*/
//
//    @OnClick(R.id.tv_test1)
//    public void tv_test1_onClick() {
//        Utils.toast("aa");
//    }
//
//    @OnClick(R.id.tv_test2)
//    public void tv_test2_onClick() {
//        Utils.toast("bb");
//    }
}
