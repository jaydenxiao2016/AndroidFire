package com.jaydenxiao.androidfire.ui.zone.adapter;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

import com.jaydenxiao.androidfire.app.AppApplication;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.ui.zone.bean.FavortItem;
import com.jaydenxiao.androidfire.ui.zone.spannable.CircleMovementMethod;
import com.jaydenxiao.androidfire.ui.zone.spannable.NameClickable;
import com.jaydenxiao.androidfire.ui.zone.widget.FavortListView;

import java.util.List;


/**
 * des:点赞适配器
 * Created by xsf
 * on 2016.07.11:11
 */
public class FavortListAdapter {

    private FavortListView mListView;
    private List<FavortItem> datas;

    public List<FavortItem> getDatas() {
        return datas;
    }

    public void setDatas(List<FavortItem> datas) {
        this.datas = datas;
    }

    @NonNull
    public void bindListView(FavortListView listview){
        if(listview == null){
            throw new IllegalArgumentException("FavortListView is null ....");
        }
        mListView = listview;
    }


    public int getCount() {
        if(datas != null && datas.size() > 0){
            return datas.size();
        }
        return 0;
    }

    public Object getItem(int position) {
        if(datas != null && datas.size() > position){
            return datas.get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public void notifyDataSetChanged(){
        if(mListView == null){
            throw new NullPointerException("listview is null, please bindListView first...");
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if(datas != null && datas.size() > 0){
            //添加点赞图标
            builder.append(setImageSpan());
            //builder.append("  ");
            FavortItem item = null;
            for (int i=0; i<datas.size(); i++){
                item = datas.get(i);
                if(item != null){
                    builder.append(setClickableSpan(item.getUserNickname(), i));
                    if(i != datas.size()-1){
                        builder.append(", ");
                    }
                }
            }
        }
        mListView.setText(builder);
        mListView.setMovementMethod(new CircleMovementMethod(R.color.circle_name_selector_color));
    }

    @NonNull
    private SpannableString setClickableSpan(String textStr, int position) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new NameClickable(mListView.getSpanClickListener(), position), 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    private SpannableString setImageSpan(){
        String text = "  ";
        SpannableString imgSpanText = new SpannableString(text);
        imgSpanText.setSpan(new ImageSpan(AppApplication.getAppContext(), R.drawable.dianzansmal, DynamicDrawableSpan.ALIGN_BASELINE),
                0 , 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imgSpanText;
    }
}
