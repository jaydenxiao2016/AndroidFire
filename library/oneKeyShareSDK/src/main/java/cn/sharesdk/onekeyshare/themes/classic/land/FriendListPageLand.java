/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package cn.sharesdk.onekeyshare.themes.classic.land;

import com.mob.tools.utils.R;

import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.themes.classic.FriendListPage;

/** 横屏的好友列表 */
public class FriendListPageLand extends FriendListPage {
	private static final int DESIGN_SCREEN_WIDTH = 1280;
	private static final int DESIGN_TITLE_HEIGHT = 70;

	public FriendListPageLand(OnekeyShareThemeImpl impl) {
		super(impl);
	}

	protected float getRatio() {
		float screenWidth = R.getScreenWidth(activity);
		return screenWidth / DESIGN_SCREEN_WIDTH;
	}

	protected int getDesignTitleHeight() {
		return DESIGN_TITLE_HEIGHT;
	}

}
