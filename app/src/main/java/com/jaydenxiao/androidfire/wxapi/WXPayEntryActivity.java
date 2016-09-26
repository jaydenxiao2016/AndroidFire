package com.jaydenxiao.androidfire.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jaydenxiao.common.baseapp.AppConfig;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 该类一定要放到主项目的项目包名目录下的wxapi包下才能成功回调
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, AppConfig.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d("wechat",resp.toString());
		String msg = "";

		if(resp.errCode == 0)
		{
			msg = "支付成功";
			//支付成功回调刷新
		}
		else if(resp.errCode == -1)
		{
			msg = "已取消支付";
		}
		else if(resp.errCode == -2)
		{
			msg = "支付失败";
		}
		ToastUitl.showShort(msg);
		//关闭窗口
		finish();
	}
}