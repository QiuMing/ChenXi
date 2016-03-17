package com.hunter.chenxi.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;
import com.hunter.chenxi.ui.custom.SelectPicPopupWindow;
import com.hunter.chenxi.utils.BitmapUtil;
import com.hunter.chenxi.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.imgHead)
    ImageView imgHead;
    @Bind(R.id.textName)
    TextView textName;
    @Bind(R.id.textSex)
    TextView textSex;
    @Bind(R.id.textAge)
    TextView textAge;
    @Bind(R.id.textHeight)
    TextView textheight;
    @Bind(R.id.textWeight)
    TextView textWeight;
    @Bind(R.id.textBMI)
    TextView textBMI;
    @Bind(R.id.textProfession)
    TextView textProfession;
    @Bind(R.id.textPhysical)
    TextView textPhysical;
    @Bind(R.id.textAppeal)
    TextView textAppeal;

    private Bitmap head;//头像Bitmap
    public static final String HEAD_PATH = "/sdcard/chenxi/myHead/";//sd路径
    //    public static final int REQUEST_CODE_IMAGE_SELECT = 1001;
    public static final int REQUEST_CODE_IMAGE_CROP = 1003;
    public static final int REQUEST_CODE_PICK = 1001;
    public static final int REQUEST_CODE_TAKE = 1002;
    public static final String IMAGE_FILE_NAME = "head.jpg";
    SelectPicPopupWindow menuWindow;
    Context mContext;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_user_info);
    }

    @Override
    public void initView() {
        imgHead.setOnClickListener(this);
        textName.setOnClickListener(this);
        textSex.setOnClickListener(this);
        textAge.setOnClickListener(this);
        textheight.setOnClickListener(this);
        textWeight.setOnClickListener(this);
        textBMI.setOnClickListener(this);
        textProfession.setOnClickListener(this);
        textPhysical.setOnClickListener(this);
        textAppeal.setOnClickListener(this);

        updatePhoto(imgHead);

    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //这个用的时候再改
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgHead:
//                Utils.toast("imgHead");
                menuWindow = new SelectPicPopupWindow(this, itemsOnClick);
                menuWindow.showAtLocation(findViewById(R.id.imgHead),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

//                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(intent1, REQUEST_CODE_IMAGE_SELECT);
                break;
            case R.id.textName:
                Utils.toast("textName");
                break;
            case R.id.textSex:
                break;
            case R.id.textAge:
                break;
            case R.id.textHeight:
                break;
            case R.id.textWeight:
                break;
            case R.id.textBMI:
                break;
            case R.id.textProfession:
                break;
            case R.id.textPhysical:
                break;
            case R.id.textAppeal:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICK:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case REQUEST_CODE_TAKE:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;
            case REQUEST_CODE_IMAGE_CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if(head!=null){
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        imgHead.setImageBitmap(BitmapUtil.toRoundCorner(head));//用ImageView显示出来
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_CODE_IMAGE_CROP);
    }
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(HEAD_PATH);
        if (!file.exists())
            file.mkdirs();// 创建文件夹
        String fileName = HEAD_PATH + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                if(b!=null){
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // 拍照
                case R.id.btn_take_photo:
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //下面这句指定调用相机拍照后的照片存储的路径
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUEST_CODE_TAKE);
                    break;
                // 相册选择图片
                case R.id.btn_pick_photo:
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    // 如果朋友们要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUEST_CODE_PICK);
                    break;
                default:
                    break;
            }
        }
    };

    public static void updatePhoto(ImageView img){
        Bitmap bt = BitmapFactory.decodeFile(UserInfoActivity.HEAD_PATH + "head.jpg");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(BitmapUtil.toRoundCorner(bt));//转换成drawable
            img.setImageDrawable(drawable);
        } else {
            /**
             *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }
    }

}
