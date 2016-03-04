package com.hunter.chenxi.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.utils.DownImage;
import com.hunter.chenxi.vo.PartnerVo.Block;
import com.hunter.chenxi.vo.PartnerVo.Categories;
import com.hunter.chenxi.vo.PartnerVo.Sliders;

import java.util.LinkedList;

/**
 * 创建人：SunShine
 * 功能说明：
 */
public class PartnerAdapter extends BaseAdapter {

    private Context context;
    private LinkedList list;
    public Block block;


    public PartnerAdapter(Context context, LinkedList list) {
        this.context = context;
        this.list = list;
        if (!list.isEmpty()) {
            Log.e("aa", list.toString());
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {//暂未完成
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_partner_default, null);
            holder = new ViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.item_partner_title);
            holder.desc = (TextView) convertView.findViewById(R.id.item_partner_describe);
            holder.data = (TextView) convertView.findViewById(R.id.item_partner_data);
            holder.margin = (TextView) convertView.findViewById(R.id.item_partner_marginTop);
            holder.halving = (TextView) convertView.findViewById(R.id.item_partner_describe_halving);
            holder.img = (ImageView) convertView.findViewById(R.id.item_partner_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (list.get(i).getClass() == Sliders.class) {//设置sliders
            Log.e("test", " ***sliders*** ");

        } else if (list.get(i).getClass() == Block.class) {//设置block
            Log.e("test", " ***block*** ");
            block = (Block) list.get(i);
            holder.title.setText(block.getTitle());
            holder.desc.setText(block.getDesc());
            holder.data.setText(block.getData());
            holder.img.setImageResource(R.mipmap.ic_launcher);//设置默认图
            //设置图片
            DownImage downImage = new DownImage(block.getPic_url());
            downImage.loadImage(new DownImage.ImageCallBack() {
                @Override
                public void getDrawable(Drawable drawable) {
                    holder.img.setImageDrawable(drawable);
                }
            });
            //设置分割线
            if (list.get(i + 1).getClass() == Block.class && list.size() != (i + 1))
                holder.halving.setVisibility(((Block) list.get(i + 1)).is_new() ? View.VISIBLE : View.INVISIBLE);
            //设置marginTop
            holder.margin.setVisibility(block.is_new() ? View.GONE : View.VISIBLE);

        } else if (list.get(i).getClass() == Categories.class) {//设置categories

            Log.e("test", " ***categories*** ");

        }
        return convertView;
    }


    public class ViewHolder {

        public ImageView img;
        public TextView title;
        public TextView desc;
        public TextView data;
        public TextView margin;
        public TextView halving;
    }


}
