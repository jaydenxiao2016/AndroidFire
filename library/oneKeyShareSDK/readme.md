 //注意：分享图标不要超过100k
 private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("戳进去，玩转同城送达至快平台！");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(inviteUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("忙时，疯友为您服务，闲时，您为疯友服务，角色转换，娱乐交友两不误！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath(getDrawable(R.drawable.logo).get);//确保SDcard下面存在此张图片
        oks.setImageUrl(AppApi.APP_IMAGE_LOGO_URL);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(inviteUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("闲时，您为疯友服务，角色转换，娱乐交友两不误！");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(inviteUrl);
// 启动分享GUI
        oks.show(this);
    }