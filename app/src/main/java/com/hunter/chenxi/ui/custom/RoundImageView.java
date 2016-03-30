package com.hunter.chenxi.ui.custom;

/**
 * Created by Ming on 2016/3/16.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.hunter.chenxi.R;

public class RoundImageView extends ImageView {

    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_CORNER = 1;

    private int imageType;//圆形 or 圆角
    private int cornerRadius;
    private int imgPainterWidth;

    public Paint borderPaint;
    public int border;

    @ColorInt
    public int borderColor;

    public Paint bitmapPaint;
    public BitmapShader bitmapShader;
    public RectF imageRect;
    public RectF borderRect;
    public Matrix mMatrix;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        cornerRadius = array.getDimensionPixelSize(R.styleable.RoundImageView_cornerRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        6,
                        getResources().getDisplayMetrics()));//圆角默认6

        border = array.getDimensionPixelSize(R.styleable.RoundImageView_border,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        0,
                        getResources().getDisplayMetrics()));//border默认 0

        borderColor = array.getColor(R.styleable.RoundImageView_borderColor,0xFFFFFFFF);

        imageType = array.getInt(R.styleable.RoundImageView_imageType, TYPE_CIRCLE);// 默认为Circle
        array.recycle();

        mMatrix = new Matrix();
        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);

        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (imageType == TYPE_CIRCLE) {
            imgPainterWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(imgPainterWidth+border*2, imgPainterWidth+border*2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initShader();

        if (imageType == TYPE_CORNER) {
            canvas.drawRoundRect(borderRect, cornerRadius, cornerRadius,borderPaint);
            canvas.drawRoundRect(imageRect, cornerRadius, cornerRadius,bitmapPaint);
        } else {
            int imgRadius = imgPainterWidth / 2;
            int painterWidth = imgRadius+border;
            canvas.drawCircle(painterWidth, painterWidth, painterWidth, borderPaint);
            canvas.drawCircle(painterWidth, painterWidth, imgRadius, bitmapPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 圆角图片的范围
        if (imageType == TYPE_CORNER) {
            borderRect = new RectF(0,0,w,h);
            imageRect = new RectF(border, border, w-border, h-border);
        }
    }

    private void initShader() {
        Drawable drawable = getDrawable();
        Bitmap bmp = drawableToBitamp(drawable);
        bitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);

        float scale = 1.0f;
        if (imageType == TYPE_CIRCLE) {//圆形
            int imgMinWidth = Math.min(bmp.getWidth(), bmp.getHeight());
            scale = imgPainterWidth * 1.0f / imgMinWidth;
        } else if (imageType == TYPE_CORNER) {//圆角
            if (!(bmp.getWidth() == getWidth() && bmp.getHeight() == getHeight())) {
                // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
                scale = Math.max(getWidth() * 1.0f / bmp.getWidth(), getHeight() * 1.0f / bmp.getHeight());
            }
        }
        mMatrix.setScale(scale, scale);
        bitmapShader.setLocalMatrix(mMatrix);
        bitmapPaint.setShader(bitmapShader);
    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public void setCornerRadius(int cornerRadius) {
        int pxVal = dp2px(cornerRadius);
        if (this.cornerRadius != pxVal) {
            this.cornerRadius = pxVal;
            invalidate();
        }
    }

    public void setBorder(int border){
        int pxVal = dp2px(border);
        if (this.border != pxVal) {
            this.border = pxVal;
            invalidate();
        }
    }

    public void setBorderColor(@ColorInt int color){
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public void setImageType(int type) {
        if (this.imageType != type) {
            this.imageType = type;
            if (this.imageType != TYPE_CORNER && this.imageType != TYPE_CIRCLE) {
                this.imageType = TYPE_CIRCLE;
            }
            requestLayout();
        }
    }

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }
}