package com.science.delayfragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author 幸运Science
 * @description
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @data 2016/8/19
 */
public abstract class BaseFragment extends Fragment {

    protected boolean isVisible;
    private boolean isFirst = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initCreateView(inflater, container, savedInstanceState);
        isFirst = true;
        return view;
    }

    /**
     * 子类初始化控件
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View initCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * viewpager切换时调用，而且是在onCreateView之前调用
     *
     * @param isVisibleToUser true：用户可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInVisible();
        }
    }

    /**
     * 使用add(), hide()，show()添加fragment时
     * 刚开始add()时，当前fragment会调用该方法，但是目标fragment不会调用；
     * 所以先add()所有fragment，即先初始化控件，但不初始化数据。
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInVisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            onVisible();
        }
    }

    private void onVisible() {
        if (isFirst && isVisible) {
            onLazyLoad();
//            isFirst = false; // 控制fragment可见时，是否自动加载数据。
        }
    }

    /**
     * fragment可见时再加载数据
     */
    public abstract void onLazyLoad();

    private void onInVisible() {

    }
}
