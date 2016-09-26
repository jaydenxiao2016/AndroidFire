package com.wevey.selector.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weavey.utils.ScreenSizeUtils;
import com.weavey.utils.UiUtils;

import java.util.ArrayList;

/**
 * Created by Weavey on 2016/9/3.
 */
public class NormalSelectionDialog {


    private static Context mContext;
    private Dialog mDialog;
    private View dialogView;
    private TextView title;
    private Button bottomBtn;
    private LinearLayout linearLayout;

    private Builder mBuilder;
    private ArrayList<String> datas;
    private int selectPosition;//最后一次选择的位置

    public NormalSelectionDialog(Builder builder) {

        this.mBuilder = builder;
        mDialog = new Dialog(mContext, R.style.bottomDialogStyle);
        dialogView = View.inflate(mContext, R.layout.widget_bottom_dialog, null);
        mDialog.setContentView(dialogView); // 一定要在setAttributes(lp)之前才有效

        //设置dialog的宽
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenSizeUtils.getInstance(mContext).getScreenWidth() * builder
                .getItemWidth());
        lp.gravity = Gravity.BOTTOM;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);


        title = (TextView) dialogView.findViewById(R.id.action_dialog_title);
        linearLayout = (LinearLayout) dialogView.findViewById(R.id.action_dialog_linearlayout);
        bottomBtn = (Button) dialogView.findViewById(R.id.action_dialog_botbtn);
        bottomBtn.setText(builder.getCancleButtonText());
        bottomBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mDialog.dismiss();
            }
        });
        mDialog.setCanceledOnTouchOutside(builder.isTouchOutside());
    }

    //根据数据生成item
    private void loadItem() {

        //设置标题
        if (mBuilder.getTitleVisible()) {

            title.setVisibility(View.VISIBLE);
            title.setText(mBuilder.getTitleText());
            title.setTextColor(mBuilder.getTitleTextColor());
            title.setTextSize(mBuilder.getTitleTextSize());
            LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) title.getLayoutParams();
            l.height = UiUtils.dp2px(mContext, mBuilder.getTitleHeight());
            title.setLayoutParams(l);

            if (datas.size() != 0) {
                title.setBackgroundResource(R.drawable.selector_widget_actiondialog_top);

            } else {
                title.setBackgroundResource(R.drawable.selector_widget_actiondialog_single);
            }
        } else {

            title.setVisibility(View.GONE);
        }

        //设置底部“取消”按钮
        bottomBtn.setTextColor(mBuilder.getItemTextColor());
        bottomBtn.setTextSize(mBuilder.getItemTextSize());
        LinearLayout.LayoutParams btnlp = new LinearLayout.LayoutParams(AbsListView.LayoutParams
                .MATCH_PARENT, mBuilder.getItemHeight());
        btnlp.topMargin = 10;
        bottomBtn.setLayoutParams(btnlp);

        //设置数据item
        if (datas.size() == 1) {

            Button button = getButton(datas.get(0), 0);
            if (mBuilder.getTitleVisible())
                button.setBackgroundResource(R.drawable.selector_widget_actiondialog_bottom);
            else button.setBackgroundResource(R.drawable.selector_widget_actiondialog_single);

            linearLayout.addView(button);

        } else if (datas.size() > 1) {

            for (int i = 0; i < datas.size(); i++) {

                Button button = getButton(datas.get(i), i);
                if (!mBuilder.getTitleVisible() && i == 0) {
                    button.setBackgroundResource(R.drawable.selector_widget_actiondialog_top);
                } else {
                    if (i != datas.size() - 1)
                        button.setBackgroundResource(R.drawable
                                .selector_widget_actiondialog_middle);
                    else
                        button.setBackgroundResource(R.drawable
                                .selector_widget_actiondialog_bottom);
                }
                linearLayout.addView(button);
            }
        }


    }

    private Button getButton(String text, int position) {

        // 动态生成选择按钮
        final Button button = new Button(mContext);
        button.setText(text);
        button.setTag(position);
        button.setTextColor(mBuilder.getItemTextColor());
        button.setTextSize(mBuilder.getItemTextSize());
        button.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams
                .MATCH_PARENT, mBuilder.getItemHeight()));
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (mBuilder.getOnItemListener() != null) {

                    selectPosition = Integer.parseInt(button.getTag().toString());
                    mBuilder.getOnItemListener().onItemClick(button, selectPosition);

                }
            }
        });

        return button;
    }

    public void setDataList(ArrayList<String> datas) {

        int count = linearLayout.getChildCount();
        if(count>1){
            linearLayout.removeViewsInLayout(1,count-1);
        }
//
        this.datas = (datas == null ? new ArrayList<String>() : datas);
        loadItem();
    }

    public boolean isShowing() {

        return mDialog.isShowing();
    }

    public void show() {

        mDialog.show();

    }

    public void dismiss() {

        mDialog.dismiss();
    }

    public static class Builder {

        //标题属性
        private boolean boolTitle;
        private int titleHeight;
        private String titleText;
        private int titleTextColor;
        private float titleTextSize;
        //item属性
        private DialogOnItemClickListener onItemListener;
        private int itemHeight;
        private float itemWidth;
        private int itemTextColor;
        private float itemTextSize;
        //取消按钮属性
        private String cancleButtonText;

        private boolean isTouchOutside;

        public Builder(Context context) {

            mContext = context;

            boolTitle = false; // 默认关闭标题
            titleHeight = 65; // 默认标题高度  dp
            titleText = "请选择";
            titleTextColor = ContextCompat.getColor(context, R.color.black_light);
            titleTextSize = 13;

            onItemListener = null;
            itemHeight = UiUtils.dp2px(context, 45); // 默认item高度
            itemWidth = 0.92f;
            itemTextColor = ContextCompat.getColor(mContext, R.color.black_light); // 默认字体颜色
            itemTextSize = 14;  //默认自体大小

            cancleButtonText = "取消";
            isTouchOutside = true;
        }

        public boolean getTitleVisible() {
            return boolTitle;
        }

        public Builder setlTitleVisible(boolean isVisible) {
            this.boolTitle = isVisible;
            return this;
        }

        public int getTitleHeight() {
            return titleHeight;
        }

        public Builder setTitleHeight(int dp) {
            this.titleHeight = dp;
            return this;
        }

        public String getTitleText() {
            return titleText;
        }

        public Builder setTitleText(String titleText) {
            this.titleText = titleText;
            return this;
        }

        public int getTitleTextColor() {
            return titleTextColor;
        }

        public Builder setTitleTextColor(@ColorRes int titleTextColor) {
            this.titleTextColor = ContextCompat.getColor(mContext, titleTextColor);
            return this;
        }

        public float getTitleTextSize() {
            return titleTextSize;
        }

        public Builder setTitleTextSize(int sp) {

            this.titleTextSize = sp;
            return this;
        }

        public DialogOnItemClickListener getOnItemListener() {
            return onItemListener;
        }

        public Builder setOnItemListener(DialogOnItemClickListener onItemListener) {
            this.onItemListener = onItemListener;
            return this;
        }

        public int getItemHeight() {
            return itemHeight;
        }

        public Builder setItemHeight(int dp) {
            this.itemHeight = UiUtils.dp2px(mContext, dp);
            return this;
        }


        public float getItemWidth() {
            return itemWidth;
        }

        public Builder setItemWidth(float itemWidth) {
            this.itemWidth = itemWidth;
            return this;
        }

        public int getItemTextColor() {

            return itemTextColor;
        }

        public Builder setItemTextColor(@ColorRes int itemTextColor) {

            this.itemTextColor = ContextCompat.getColor(mContext, itemTextColor);
            return this;
        }

        public float getItemTextSize() {
            return itemTextSize;
        }

        public Builder setItemTextSize(int sp) {
            this.itemTextSize = sp;
            return this;
        }

        public String getCancleButtonText() {
            return cancleButtonText;
        }

        public Builder setCancleButtonText(String cancleButtonText) {
            this.cancleButtonText = cancleButtonText;
            return this;
        }

        public boolean isTouchOutside() {
            return isTouchOutside;
        }

        public Builder setCanceledOnTouchOutside(boolean isTouchOutside) {

            this.isTouchOutside = isTouchOutside;
            return this;
        }

        public NormalSelectionDialog build() {

            return new NormalSelectionDialog(this);
        }
    }

}