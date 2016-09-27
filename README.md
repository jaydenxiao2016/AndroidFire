AndroidFire，一款新闻阅读 App框架，基于 Material Design + MVP + RxJava + Retrofit + Glide，基本涵盖了当前 Android 端开发最常用的主流框架，基于此框架可以快速开发一个app。

[安装地址](http://fir.im/androidFire)
####国际案例，有图有真相：

![首页效果图](https://mmbiz.qlogo.cn/mmbiz_jpg/2EhjCcceOmiacVsI4wE6cDpOYHc0grkibzibpoxAG8EwBLVn4dHib3D0Wwrez4WeNmFkib19vKibYnMu9nQucqP5gvvw/0?wx_fmt=jpeg)
![美女和视频效果图](https://mmbiz.qlogo.cn/mmbiz_jpg/2EhjCcceOmiacVsI4wE6cDpOYHc0grkibzqao1ia26RiaZkP91tKePGT4OkuWU87wtYdmxpTauialIhhkVNic5DTUbiag/0?wx_fmt=jpeg)
![关注和朋友圈效果图](https://mmbiz.qlogo.cn/mmbiz_jpg/2EhjCcceOmiacVsI4wE6cDpOYHc0grkibzxbZBpcwzfT4icn613ltpvcpVQ1um2RrdOKOzA2XO8ETTE5DKTntKSBQ/0?wx_fmt=jpeg)
![朋友圈选择图片发布](https://mmbiz.qlogo.cn/mmbiz_jpg/2EhjCcceOmiacVsI4wE6cDpOYHc0grkibzAILAo5nkQhnsHkL3GyjbgEib8CFykAjXKn9SKwsD7PSzfQDBhOIsVFA/0?wx_fmt=jpeg)

####目前模块包括：
- 新闻：头条、科技、财经、NBA等类型，可自主选择订阅
- 美图：美图壁纸
- 视频：包括热点、搞笑、娱乐、精品视频
- 关注：包括朋友圈、日夜模式切换(无需重启界面)功能

####项目亮点：
- mvp模式：解耦model和view层，契约类管理mvp，一目了然，实现纵向解耦，基类完美封装，避免频繁new对象
-  RxJava:包括Rx处理服务器请求、缓存、线程调度的完美封装
- 复杂列表处理，充分解决滑动卡顿问题，具体方法看关注模块里面的“朋友圈”例子
- 组件化开发，横向解耦
- 封装各种工具类，比如压缩图片、轮播器、查看大图、缓存工具、图片选择器，以common的module形式依赖
- 各种封装好的依赖库，比如Irecyclerview：包含万能适配器、recyclerview的下拉刷新上拉加载更多、自定义刷新头和加载更多头；selectordialog：经常使用到的几种Dialog；oneKeyShareSDK：社交分享；微信和支付宝封装等等
- 无关业务内容封装成model，基于此框架可以快速开发一个app

####用到的开源库有：
- om.flyco.tablayout:FlycoTabLayout_Lib:2.0.8@aar 
- com.github.clans:fab:1.6.4
- com.squareup.retrofit2:retrofit:2.0.0-beta3
- io.reactivex:rxjava:1.0.1
- io.reactivex:rxandroid:1.0.1
- com.github.bumptech.glide:glide:3.6.1
- fm.jiecao:jiecaovideoplayer:4.7.0 
- cn.hugeterry.updatefun:updatefun:1.8.6等等

>本人会坚持在这个项目上实践最新的技术，也会争取拓展更多的阅读内容，欢迎各位关注！
注意：本项目还在测试阶段，发现 bug 或有好的建议欢迎issue、email(jaydenxiao2016@gmail.com),如果感觉对你有帮助也欢迎点个 star、fork，本项目仅做学习交流使用，API 数据内容所有权归原作公司所有，请勿用于其他用途

最后附上下载地址和源码
[github源码](https://github.com/jaydenxiao2016/AndroidFire)
[安装地址](http://fir.im/androidFire)

更多精彩文章请关注微信公众号"**Android经验分享**"：这里将长期为您分享Android高手经验、中外开源项目、源码解析、框架设计和Android好文推荐！

![扫一扫加我哦](http://upload-images.jianshu.io/upload_images/1964096-6b04d2e7cff6d7c7.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
