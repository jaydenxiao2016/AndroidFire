package com.wevey.selector.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.weavey.utils.ScreenSizeUtils;

/**
 * Created by Weavey on 2016/9/4.
 */
public class MDEditDialog implements View.OnClickListener, DialogInterface.OnDismissListener {

    private Dialog mDialog;
    private View mDialogView;
    private TextView mTitle;
    private EditText mEdit;
    private TextView mLeftBtn;
    private TextView mRightBtn;
    private View lineView;
    private static Context mContext;
    private Builder mBuilder;

    public MDEditDialog(Builder builder) {

        mBuilder = builder;
        mDialog = new Dialog(mContext, R.style.MyDialogStyle);
        mDialogView = View.inflate(mContext, R.layout.widget_edit_dialog, null);
        mTitle = (TextView) mDialogView.findViewById(R.id.edit_dialog_title);
        mEdit = (EditText) mDialogView.findViewById(R.id.edit_dialog_exittext);
        mLeftBtn = (TextView) mDialogView.findViewById(R.id.edit_dialog_leftbtn);
        mRightBtn = (TextView) mDialogView.findViewById(R.id.edit_dialog_rightbtn);
        lineView = (View) mDialogView.findViewById(R.id.edit_dialog_line);
        mDialogView.setMinimumHeight((int) (ScreenSizeUtils.getInstance(mContext).getScreenHeight
                () * builder.getMinHeight()));
        mDialog.setContentView(mDialogView);

        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenSizeUtils.getInstance(mContext).getScreenWidth() * builder
                .getWidth());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        initDialog();

    }

    private void initDialog() {

        mDialog.setOnDismissListener(this);
        mDialog.setCanceledOnTouchOutside(mBuilder.isTouchOutside());

        if (mBuilder.getTitleVisible()) {

            mTitle.setVisibility(View.VISIBLE);
        } else {

            mTitle.setVisibility(View.GONE);
        }

        mTitle.setText(mBuilder.getTitleText());
        mTitle.setTextColor(mBuilder.getTitleTextColor());
        mTitle.setTextSize(mBuilder.getTitleTextSize());
        mEdit.setText(mBuilder.getContentText());
        mEdit.setSelection(mBuilder.getContentText().length());
        mEdit.setTextColor(mBuilder.getContentTextColor());
        mEdit.setTextSize(mBuilder.getContentTextSize());
        mLeftBtn.setText(mBuilder.getLeftButtonText());
        mLeftBtn.setTextColor(mBuilder.getLeftButtonTextColor());
        mLeftBtn.setTextSize(mBuilder.getButtonTextSize());
        mRightBtn.setText(mBuilder.getRightButtonText());
        mRightBtn.setTextColor(mBuilder.getRightButtonTextColor());
        mRightBtn.setTextSize(mBuilder.getButtonTextSize());
        lineView.setBackgroundColor(mBuilder.getLineColor());
        mLeftBtn.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);

        mEdit.setHint(mBuilder.getHintText());
        mEdit.setHintTextColor(mBuilder.getHintTextColor());
        if (mBuilder.getLines() != -1) {

            mEdit.setLines(mBuilder.getLines());
        }
        if (mBuilder.getMaxLines() != -1) {

            mEdit.setMaxLines(mBuilder.getMaxLines());
        }
        if (mBuilder.getMaxLength() != -1) {

            mEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mBuilder.getMaxLength
                    ())});
        }

    }

    public void show() {

        mDialog.show();
    }

    public void dismiss() {

        mDialog.dismiss();
    }

    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.edit_dialog_leftbtn && mBuilder.getListener() != null) {

            mBuilder.getListener().clickLeftButton(mLeftBtn, mEdit.getText().toString());
            return;
        }

        if (i == R.id.edit_dialog_rightbtn && mBuilder.getListener() != null) {

            mBuilder.getListener().clickRightButton(mRightBtn, mEdit.getText().toString());
            return;
        }

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

        mEdit.setText("");
    }

    public static class Builder {

        private String titleText;
        private int titleTextColor;
        private int titleTextSize;
        private String contentText;
        private int contentTextColor;
        private int contentTextSize;
        private String leftButtonText;
        private int leftButtonTextColor;
        private String rightButtonText;
        private int rightButtonTextColor;
        private int buttonTextSize;
        private int lineColor;
        private int lines;
        private int maxLines;
        private int maxLength;
        private boolean isTitleVisible;
        private boolean isTouchOutside;
        private float height;
        private float width;
        private String hintText;
        private int hintTextColor;
        private OnClickEditDialogListener listener;

        public Builder(Context context) {

            mContext = context;
            titleText = "提示";
            titleTextColor = ContextCompat.getColor(mContext, R.color.black_light);
            contentText = "";
            contentTextColor = ContextCompat.getColor(mContext, R.color.black_light);
            leftButtonText = "取消";
            leftButtonTextColor = ContextCompat.getColor(mContext, R.color.black_light);
            rightButtonText = "确定";
            rightButtonTextColor = ContextCompat.getColor(mContext, R.color.black_light);
            lineColor = ContextCompat.getColor(mContext, R.color.black_light);
            listener = null;
            isTitleVisible = true;
            isTouchOutside = true;
            hintText = "";
            hintTextColor = ContextCompat.getColor(mContext, R.color.gray);
            lines = -1;
            maxLines = -1;
            maxLength = -1;
            height = 0.28f;
            width = 0.8f;
            titleTextSize = 20;
            contentTextSize = 18;
            buttonTextSize = 16;
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

        public String getContentText() {
            return contentText;
        }

        public Builder setContentText(String contentText) {
            this.contentText = contentText;
            return this;
        }

        public int getContentTextColor() {
            return contentTextColor;
        }

        public Builder setContentTextColor(@ColorRes int contentTextColor) {
            this.contentTextColor = ContextCompat.getColor(mContext, contentTextColor);
            return this;
        }

        public String getLeftButtonText() {
            return leftButtonText;
        }

        public Builder setLeftButtonText(String leftButtonText) {
            this.leftButtonText = leftButtonText;
            return this;
        }

        public int getLeftButtonTextColor() {
            return leftButtonTextColor;
        }

        public Builder setLeftButtonTextColor(@ColorRes int leftButtonTextColor) {
            this.leftButtonTextColor = ContextCompat.getColor(mContext, leftButtonTextColor);
            return this;
        }

        public String getRightButtonText() {
            return rightButtonText;
        }

        public Builder setRightButtonText(String rightButtonText) {
            this.rightButtonText = rightButtonText;
            return this;
        }

        public int getRightButtonTextColor() {
            return rightButtonTextColor;
        }

        public Builder setRightButtonTextColor(@ColorRes int rightButtonTextColor) {
            this.rightButtonTextColor = ContextCompat.getColor(mContext, rightButtonTextColor);
            return this;
        }

        public boolean getTitleVisible() {
            return isTitleVisible;
        }

        public Builder setTitleVisible(boolean titleVisible) {
            isTitleVisible = titleVisible;
            return this;
        }

        public boolean isTouchOutside() {
            return isTouchOutside;
        }

        public Builder setCanceledOnTouchOutside(boolean touchOutside) {
            isTouchOutside = touchOutside;
            return this;
        }

        public float getMinHeight() {
            return height;
        }

        public Builder setMinHeight(float height) {
            this.height = height;
            return this;
        }

        public float getWidth() {
            return width;
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public int getContentTextSize() {
            return contentTextSize;
        }

        public Builder setContentTextSize(int contentTextSize) {
            this.contentTextSize = contentTextSize;
            return this;
        }

        public int getLineColor() {

            return lineColor;
        }

        public Builder setLineColor(@ColorRes int lineColor) {
            this.lineColor = ContextCompat.getColor(mContext, lineColor);
            return this;
        }

        public int getLines() {
            return lines;
        }

        public Builder setLines(int lines) {
            this.lines = lines;
            return this;
        }

        public int getMaxLines() {
            return maxLines;
        }

        public Builder setMaxLines(int maxLines) {
            this.maxLines = maxLines;
            return this;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public Builder setMaxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public int getTitleTextSize() {
            return titleTextSize;
        }

        public Builder setTitleTextSize(int titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public int getButtonTextSize() {
            return buttonTextSize;
        }

        public Builder setButtonTextSize(int buttonTextSize) {
            this.buttonTextSize = buttonTextSize;
            return this;
        }

        public String getHintText() {
            return hintText;
        }

        public Builder setHintText(String hintText) {
            this.hintText = hintText;
            return this;
        }

        public int getHintTextColor() {
            return hintTextColor;
        }

        public Builder setHintTextColor(int hintTextColor) {
            this.hintTextColor = ContextCompat.getColor(mContext, hintTextColor);
            return this;
        }

        public OnClickEditDialogListener getListener() {
            return listener;
        }

        public Builder setOnclickListener(OnClickEditDialogListener listener) {
            this.listener = listener;
            return this;
        }

        public MDEditDialog build() {

            return new MDEditDialog(this);
        }
    }

    public interface OnClickEditDialogListener {

        void clickLeftButton(View view, String editText);

        void clickRightButton(View view, String editText);

    }


}
