# DelayFragment   
Fragment延迟加载数据  
在实际使用中，考虑到性能和体验等方面，在fragment加载数据的时机有两种情况：  
1，在页面第一次可见时，加载数据，往后再次进入到该页面，需要手动刷新重新获取数据；  
2，无论何时，只要页面可见，就自动加载数据。  


Screenshot
-------------------------
页面可见时只加载数据一次:

![image](https://github.com/XYScience/DelayFragment/raw/master/screenshot/fragment-delay-load-first.gif)

页面可见时就自动加载数据:

![image](https://github.com/XYScience/DelayFragment/raw/master/screenshot/fragment-visiable-load.gif)
