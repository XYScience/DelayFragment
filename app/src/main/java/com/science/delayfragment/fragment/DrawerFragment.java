package com.science.delayfragment.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.science.delayfragment.R;

/**
 * @author 幸运Science
 * @description
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @data 2016/8/19
 */
public class DrawerFragment extends BaseFragment {

    private static final String KEY = "key";
    private TextView text;
    private ContentLoadingProgressBar progressBar;

    public static DrawerFragment newInstance(String str) {

        Bundle args = new Bundle();
        args.putString(KEY, str);
        DrawerFragment fragment = new DrawerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        text = (TextView) view.findViewById(R.id.text);
        progressBar = (ContentLoadingProgressBar) view.findViewById(R.id.progress);
    }

    @Override
    public void onLazyLoad() {
        progressBar.setVisibility(View.VISIBLE);
        // 模拟网络请求数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                text.setText(getArguments().getString(KEY));
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
    }
}
