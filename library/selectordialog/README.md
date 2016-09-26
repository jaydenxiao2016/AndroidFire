#前言
项目中经常使用到的几种Dialog，所以干脆封装起来了，方便以后使用，顺便分享给大家，话不多说，直接看效果。
- 样式一

![NormalSelectionDialog ](http://upload-images.jianshu.io/upload_images/2777417-f45c4e5c907472fc.gif?imageMogr2/auto-orient/strip)

使用如下代码：
```
 
     NormalSelectionDialog dialog1 = new NormalSelectionDialog.Builder(this)
                .setlTitleVisible(true)   //设置是否显示标题
                .setTitleHeight(65)   //设置标题高度
                .setTitleText("please select")  //设置标题提示文本
                .setTitleTextSize(14) //设置标题字体大小 sp
                .setTitleTextColor(R.color.colorPrimary) //设置标题文本颜色
                .setItemHeight(40)  //设置item的高度 
               .setItemWidth(0.9f)  //屏幕宽度*0.9
                .setItemTextColor(R.color.colorPrimaryDark)  //设置item字体颜色
                .setItemTextSize(14)  //设置item字体大小
                .setCancleButtonText("Cancle")  //设置最底部“取消”按钮文本
                .setOnItemListener(new DialogOnItemClickListener() {  //监听item点击事件
                         @Override
                         public void onItemClick(Button button, int position) {
//                                dialog1.dismiss();
                                Toast.makeText(MainActivity.this, s.get(position), Toast.LENGTH_SHORT).show();

                         }
                })
                .setCanceledOnTouchOutside(true)  //设置是否可点击其他地方取消dialog
                .build();
       ArrayList<String> s = new ArrayList<>();
        s.add("Weavey0");
        s.add("Weavey1");
        s.add("Weavey2");
        s.add("Weavey3");
        dialog1.setDataList(s);
```

- 样式二

![NormalAlertDialog](http://upload-images.jianshu.io/upload_images/2777417-a3edb1daaece6418.gif?imageMogr2/auto-orient/strip)
使用如下代码：
```
NormalAlertDialog  dialog2 = new NormalAlertDialog.Builder(MainActivity.this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText("温馨提示")
                .setTitleTextColor(R.color.black_light)
                .setContentText("是否关闭对话框？")
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText("关闭")
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText("不关闭")
                .setRightButtonTextColor(R.color.black_light)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
//                        dialog2.dismiss();
                    }
                    @Override
                    public void clickRightButton(View view) {
//                        dialog2.dismiss();
                    }
                })
                .build();
```

- 样式三


![NormalAlertDialog ](http://upload-images.jianshu.io/upload_images/2777417-c6986befdbd83202.gif?imageMogr2/auto-orient/strip)
使用如下代码：

```
NormalAlertDialog dialog3 = new NormalAlertDialog.Builder(MainActivity.this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText("温馨提示")
                .setTitleTextColor(R.color.colorPrimary)
                .setContentText("是否关闭对话框？")
                .setContentTextColor(R.color.colorPrimaryDark)
                .setSingleMode(true)
                .setSingleButtonText("关闭")
                .setSingleButtonTextColor(R.color.colorAccent)
                .setCanceledOnTouchOutside(true)
                .setSingleListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        dialog3.dismiss();
                    }
                })
                .build();
```

- 样式四

![MDAlertDialog](http://upload-images.jianshu.io/upload_images/2777417-e7cbf8f30d818458.gif?imageMogr2/auto-orient/strip)
使用如下代码：
```
 MDAlertDialog dialog4 = new MDAlertDialog.Builder(MainActivity.this)
                .setHeight(0.21f)  //屏幕高度*0.21
                .setWidth(0.7f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("温馨提示")
                .setTitleTextColor(R.color.black_light)
                .setContentText("确定发送文件？")
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText("不发送")
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText("发送")
                .setRightButtonTextColor(R.color.black_light)
                .setTitleTextSize(16)
                .setContentTextSize(14)
                .setButtonTextSize(14)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
//                        dialog4.dismiss();
                    }
                    @Override
                    public void clickRightButton(View view) {
//                        dialog4.dismiss();
                    } 
               })
                .build();
```


- 样式五


![MDSelectionDialog ](http://upload-images.jianshu.io/upload_images/2777417-fee4805d1f90360d.gif?imageMogr2/auto-orient/strip)
使用如下代码：
```
MDSelectionDialog dialog5 = new MDSelectionDialog.Builder(MainActivity.this)
                .setCanceledOnTouchOutside(true)
                .setItemTextColor(R.color.black_light)
                .setItemHeight(50)
                .setItemWidth(0.8f)  //屏幕宽度*0.8
                .setItemTextSize(15)
                .setCanceledOnTouchOutside(true)
                .setOnItemListener(new DialogOnItemClickListener() {
                    @Override
                    public void onItemClick(Button button, int position) {
                        Toast.makeText(MainActivity.this, datas.get(position), Toast.LENGTH_SHORT).show();
//                        dialog5.dismiss();
                    }
                })
                .build();
        datas = new ArrayList<>();
        datas.add("标为未读");
        datas.add("置顶聊天");
        datas.add("删除该聊天");
        dialog5.setDataList(datas);
```

- 样式六

![MDEditDialog ](http://upload-images.jianshu.io/upload_images/2777417-e8e9c3e327dc24ab.gif?imageMogr2/auto-orient/strip)
使用如下代码：
```
MDEditDialog dialog6 = new MDEditDialog.Builder(MainActivity.this)
        .setTitleVisible(true)
        .setTitleText("修改用户名")
        .setTitleTextSize(20)
        .setTitleTextColor(R.color.black_light)
        .setContentText("Weavey")
        .setContentTextSize(18)
        .setMaxLength(7)
        .setHintText("7位字符")
        .setMaxLines(1)
        .setContentTextColor(R.color.colorPrimary)
        .setButtonTextSize(14)
        .setLeftButtonTextColor(R.color.colorPrimary)
        .setLeftButtonText("取消")
        .setRightButtonTextColor(R.color.colorPrimary)
        .setRightButtonText("确定")
        .setLineColor(R.color.colorPrimary) 
       .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
            @Override
            public void clickLeftButton(View view, String text) {
                //text为编辑的内容
            }
            @Override
            public void clickRightButton(View view, String text) {
                //text为编辑的内容
            }
        })
        .setMinHeight(0.3f)
        .setWidth(0.8f)
        .build();
```
#引用方式

- Maven配置
```
<dependency>
 <groupId>com.lai.weavey</groupId>
 <artifactId>dialog</artifactId>
 <version>1.1</version>
 <type>pom</type>
</dependency>
```
- gradle引用
```
compile 'com.lai.weavey:dialog:1.1'
```



