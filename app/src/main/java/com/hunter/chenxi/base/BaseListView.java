package com.hunter.chenxi.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.utils.Utils;

public class BaseListView extends ListView {

    public BaseListView(Context context) {
        super(context);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setSelector(R.drawable.nothing);
        this.setCacheColorHint(R.drawable.nothing);
        this.setDivider(Utils.getResource().getDrawable(R.drawable.nothing));

    }
}
