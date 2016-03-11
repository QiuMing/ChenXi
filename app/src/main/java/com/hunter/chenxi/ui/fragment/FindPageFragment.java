package com.hunter.chenxi.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.hunter.chenxi.R;


/**
 * Created by Ming on 2016/3/4.
 * 发现页的 Fragment,内嵌 FindPage*Fragment  以展示不同的资讯列表
 */
public class FindPageFragment extends Fragment {

    private static String[] TITLES;

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find,container,false);
        pager = (ViewPager) view.findViewById(R.id.viewPager);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.slidtabs);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TITLES = getResources().getStringArray(R.array.news_titles);

        FragmentPagerAdapter adapter = new NewsAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        //设置 Page 间的空隙
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

    }

    class NewsAdapter extends FragmentPagerAdapter {

        public NewsAdapter(FragmentManager fm) {
            super(fm);
        }

        Fragment1 fragment1;
        Fragment2 fragment2;
        Fragment3 fragment3;
        FindPageSuggestFragment fragment4;
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragment1 = new Fragment1();
                    return fragment1;
                case 1:
                    fragment2 = new Fragment2();
                    return fragment2;
                case 2:
                    fragment3 = new Fragment3();
                    return fragment3;

                default:
                    fragment4 = new FindPageSuggestFragment();
                    return  fragment4;

            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position % TITLES.length];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }

}
