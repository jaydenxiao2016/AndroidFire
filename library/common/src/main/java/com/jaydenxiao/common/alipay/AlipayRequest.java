package com.jaydenxiao.common.alipay;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;

/**
 * des:发起支付宝支付请求
 * Created by xsf
 * on 2016.04.19:18
 */
public class AlipayRequest {

    public static void StartAlipay(final Activity activity, final String payInfo,final PayCallback payCallback){
        // 必须异步调用
       new Thread(new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                final PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                 payCallback.payResult(alipay.pay(payInfo,true));
            }
        }).start();
    }
}
