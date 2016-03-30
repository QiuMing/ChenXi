package com.hunter.chenxi.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.utils.Utils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Ming on 2016/3/17.
 */
public class UserInfoActivityNew extends BaseActivity {

   /* @Bind(R.id.txt_user_age)
    TextView txtUserAge;*/

    @Bind(R.id.txt_user_height)
    TextView txtUserHeight;

    @Bind(R.id.txt_user_weight)
    TextView txtUserWeight;

    @Bind(R.id.btn_submit_user_info)
    Button btnSubmitUserInfo;

    @Override
    public void initContentView() {
        setContentView(R.layout.layout_user_info);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }

     @OnClick(R.id.btn_submit_user_info)
    public void btn_submit_user_info_onclick() {
         if(TextUtils.isEmpty(txtUserHeight.getText().toString())
                 ||TextUtils.isEmpty(txtUserWeight.getText().toString())
                 ){
             Utils.toast("请填写完整数据");
             return ;
         }
         Double height = Double.valueOf(txtUserHeight.getText().toString()).doubleValue();
         Double weight = Double.valueOf(txtUserWeight.getText().toString()).doubleValue();
         Log.e("height ",String.valueOf(height));

         Log.e("weight ",String.valueOf(weight));
         Double dobuleBmi=(weight * 10000)/(height*height) ;

         SharedPreferences bmi = getSharedPreferences("BMI", 0);

         SharedPreferences.Editor editor = bmi.edit();
         String strBmi = String .format("%.2f", dobuleBmi);
         editor.putString("bmi", strBmi);
         editor.commit();
         Log.e("ChenXi", "DobuleBmi  is " + String.valueOf(dobuleBmi));
         Log.e("ChenXi", "StringBmi  is " + strBmi);
         Utils.toast("The BMI is " + strBmi);
         startActivity(new Intent(Utils.getContext(), MainActivity.class));
     }
}
