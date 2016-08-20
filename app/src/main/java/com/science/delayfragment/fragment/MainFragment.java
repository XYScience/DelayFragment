package com.science.delayfragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.science.delayfragment.R;
import com.science.delayfragment.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 幸运Science
 * @description
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @data 2016/8/19
 */
public class MainFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected View initCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(DrawerFragment.newInstance("First"));
        fragmentList.add(DrawerFragment.newInstance("Second"));
        fragmentList.add(DrawerFragment.newInstance("Third"));
        List<String> titles = new ArrayList<>();
        titles.add("First");
        titles.add("Second");
        titles.add("Third");
        FragmentPagerAdapter adapter = new FragmentAdapter(getChildFragmentManager(), fragmentList, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2); // 缓存两个页面，防止切换时销毁fragment
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onLazyLoad() {

    }
}
