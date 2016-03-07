package com.hunter.chenxi.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.utils.DownImage;
import com.hunter.chenxi.vo.PartnerVo.Block;
import com.hunter.chenxi.vo.PartnerVo.Categories;
import com.hunter.chenxi.vo.PartnerVo.Events;
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
    public Categories categories;
    public Events events;
    public DownImage downImage;

    public PartnerAdapter(Context context, LinkedList list) {
        this.context = context;
        this.list = list;
        if (!list.isEmpty()) {
            Log.e("aa", list.toString());
        }

    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_partner, null);
            holder = new ViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.item_partner_title);
            holder.desc = (TextView) convertView.findViewById(R.id.item_partner_describe);
            holder.data = (TextView) convertView.findViewById(R.id.item_partner_data);
            holder.margin = (TextView) convertView.findViewById(R.id.item_partner_marginTop);
            holder.halving = (TextView) convertView.findViewById(R.id.item_partner_describe_halving);
            holder.img = (ImageView) convertView.findViewById(R.id.item_partner_img);

            holder.title2 = (TextView) convertView.findViewById(R.id.item_partner_title2);
            holder.more = (TextView) convertView.findViewById(R.id.item_partner_more);
            holder.banner = (ImageView) convertView.findViewById(R.id.item_partner_banner);

            holder.layout1 = (RelativeLayout) convertView.findViewById(R.id.item_partner_layout1);
            holder.layout2 = (RelativeLayout) convertView.findViewById(R.id.item_partner_layout2);
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
            holder.img.setVisibility(block.getPic_url().equals("") ? View.GONE : View.VISIBLE);
            holder.img.setImageResource(R.mipmap.ic_launcher);//设置默认图
            //设置图片
            downImage = new DownImage(block.getPic_url());
            downImage.loadImage(new DownImage.ImageCallBack() {
                @Override
                public void getDrawable(Drawable drawable) {
                    holder.img.setImageDrawable(drawable);
                }
            });
            //设置分割线
            if (list.get(i + 1).getClass() == Block.class && list.size() != (i + 1)) {
                holder.halving.setVisibility(((Block) list.get(i + 1)).is_new() ? View.VISIBLE : View.INVISIBLE);
            } else {
                holder.halving.setVisibility(View.INVISIBLE);
            }
            //设置marginTop
            holder.margin.setVisibility(block.is_new() ? View.GONE : View.VISIBLE);

        } else if (list.get(i).getClass() == Categories.class) {//设置categories
            Log.e("test", " ***categories*** ");
            holder.layout1.setVisibility(View.GONE);
            holder.layout2.setVisibility(View.VISIBLE);
            holder.margin.setVisibility(View.VISIBLE);
            categories = (Categories) list.get(i);
            holder.title2.setText(categories.getTitle());
            holder.more.setText(categories.getShow_more() ? "更多" : "");
            holder.banner.setVisibility(categories.getBanner_pic_url().equals("") || categories.getBanner_pic_url() == null ? View.GONE : View.VISIBLE);
            holder.banner.setImageResource(R.drawable.partner_photo);//设置默认图
            //设置图片
            downImage = new DownImage(categories.getBanner_pic_url());
            downImage.loadImage(new DownImage.ImageCallBack() {
                @Override
                public void getDrawable(Drawable drawable) {
                    holder.banner.setImageDrawable(drawable);
                }
            });

        } else if (list.get(i).getClass() == Events.class) {
            Log.e("test", " ***Events*** ");
            holder.layout1.setVisibility(View.VISIBLE);
            holder.layout2.setVisibility(View.GONE);
            holder.margin.setVisibility(View.GONE);
            events = (Events) list.get(i);

            holder.title.setText(events.getTitle());
            holder.desc.setText(events.getDesc());
            holder.data.setText("");
            holder.img.setVisibility(events.getPic_url().equals("") ? View.GONE : View.VISIBLE);
            holder.img.setImageResource(R.mipmap.ic_launcher);//设置默认图
            //设置图片
            downImage = new DownImage(events.getPic_url());
            downImage.loadImage(new DownImage.ImageCallBack() {
                @Override
                public void getDrawable(Drawable drawable) {
                    holder.img.setImageDrawable(drawable);
                }
            });
            //设置分割线
            holder.halving.setVisibility(i + 1 != list.size() ? list.get(i + 1).getClass() != Events.class ? View.INVISIBLE : View.VISIBLE : View.INVISIBLE);
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

        public ImageView banner;
        public TextView title2;
        public TextView more;

        public RelativeLayout layout1, layout2;

    }


}
