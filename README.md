# DelayFragment   
Fragment延迟加载数据   
在实际使用中，考虑到性能和体验等方面，在fragment加载数据的时机有两种情况：   
1，在页面第一次可见时，加载数据，往后再次进入到该页面，需要手动刷新重新获取数据：   
2，无论何时，只要页面可见，就自动加载数据。   
而fragment主要用在两个地方：一是TabLayout和ViewPager结合使用，二是和DrawerLayout结合使用。   
   
一，TabLayout和ViewPager模式   
这种模式下，是通过FragmentPagerAdapter方式添加fragment，fragment是否可见是通过回调方法setUserVisibleHint。其中，要调用mViewPager.setOffscreenPageLimit(fragments.size-1); 缓存页面，防止切换时销毁fragment   
```   
/**
 * viewpager切换时调用，而且第一次是在onCreateView之前调用
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
```   
二，DrawerLayout模式   
这种模式是通过add()方法添加fragment，show()方法显示，fragment是否可见是通过回调方法onHiddenChanged。
```   
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
```   
三，控制可见时是否马上加载数据   
```   
@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initCreateView(inflater, container, savedInstanceState);
        isFirst = true;
        return view;
    }
```   
```   
    /**
     * 界面可见时做相应的处理
     */
    protected void doVisible() {
        if (isInit && isVisible) {
            // isInit = false;/初始化完成
            lazyLoad();
        }
    }
```   
```   
    @Override
    public void onResume() {
        super.onResume();
        // 判断当前fragment是否显示
        if (getUserVisibleHint()) {
            doVisible();
        }
    }
```   
## 注意点： ###   
如果app的结构是DrawerLayout+（TabLayout和Viewpager）：   
1，则在当前的Activity的DrawerLayout添加fragment获取的FragmentManager是通过getSupportFragmentManager()；而在fragment中添加子fragment获取的FragmentManager是通过getChildFragmentManager()。   
源码解析：   
```   
/** 
 * Return a private FragmentManager for placing and managing Fragments 
 * inside of this Fragment. 
 * 返回一个FragmentManager来设置和管理当前Fragment内部的子Fragment
 */  
final public FragmentManager getChildFragmentManager() {  
   if (mChildFragmentManager == null) {  
       instantiateChildFragmentManager();  
       if (mState >= RESUMED) {  
           mChildFragmentManager.dispatchResume();  
       } else if (mState >= STARTED) {  
           mChildFragmentManager.dispatchStart();  
       } else if (mState >= ACTIVITY_CREATED) {  
           mChildFragmentManager.dispatchActivityCreated();  
       } else if (mState >= CREATED) {  
           mChildFragmentManager.dispatchCreate();  
       }  
   }  
   return mChildFragmentManager;  
}  
```   
```   
/**
 * Return the FragmentManager for interacting with fragments associated
 * with this activity.
 * 返回一个FragmentManager来管理和当前Activity有关联的Fragment
 */
public FragmentManager getSupportFragmentManager() {
   return mFragments.getSupportFragmentManager();
}
```   


2，当点击DrawerLayout的tab切换到包含子fragment的fragment A时，最终调用lazyLoad()的只有fragment A，其子fragment则不会调用，解决如下：   
```   
/**
 * fragment A 
 */
@Override
protected void lazyLoad() {
    //解决当切换侧边栏tab时，如果主页面包含子Fragment时，子fragment数据不更新问题；
    List<Fragment> childFragmentList = getChildFragmentManager().getFragments();
    if (childFragmentList != null && childFragmentList.size() > 0) {
        for (Fragment childFragment : childFragmentList) {
             if (childFragment instanceof BaseFragment) {
                 BaseFragment childBaseFragment = (BaseFragment) childFragment;
                 if (childBaseFragment.getUserVisibleHint()) {
                        childBaseFragment.doVisible();
                    }
             }
        }
    }
}
```   

Screenshot
-------------------------
页面可见时只加载数据一次:

![image](https://github.com/XYScience/DelayFragment/raw/master/screenshot/fragment-delay-load-first.gif)

页面可见时就自动加载数据:

![image](https://github.com/XYScience/DelayFragment/raw/master/screenshot/fragment-visiable-load.gif)
