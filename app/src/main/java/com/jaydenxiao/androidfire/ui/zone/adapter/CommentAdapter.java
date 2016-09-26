package com.jaydenxiao.androidfire.ui.zone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.ui.zone.bean.CommentItem;
import com.jaydenxiao.androidfire.ui.zone.spannable.CircleMovementMethod;
import com.jaydenxiao.androidfire.ui.zone.spannable.NameClickListener;
import com.jaydenxiao.androidfire.ui.zone.spannable.NameClickable;
import com.jaydenxiao.androidfire.ui.zone.widget.CommentListView;

import java.util.ArrayList;
import java.util.List;

/**
 * des:评论适配器
 * Created by xsf
 * on 2016.07.11:11
 */
public class CommentAdapter {

    private Context mContext;
    private CommentListView mListview;
    private List<CommentItem> mDatas;

    public CommentAdapter(Context context){
        mContext = context;
        mDatas = new ArrayList<CommentItem>();
    }

    public CommentAdapter(Context context, List<CommentItem> datas){
        mContext = context;
        setDatas(datas);
    }

    public void bindListView(CommentListView listView){
        if(listView == null){
            throw new IllegalArgumentException("CommentListView is null....");
        }
        mListview = listView;
    }

    public void setDatas(List<CommentItem> datas){
        if(datas == null ){
            datas = new ArrayList<CommentItem>();
        }

        mDatas = datas;
    }

    public List<CommentItem> getDatas(){
        return mDatas;
    }

    public int getCount(){
        if(mDatas == null){
            return 0;
        }
        return mDatas.size();
    }

    public CommentItem getItem(int position){
        if(mDatas == null){
            return null;
        }
        if(position < mDatas.size()){
            return mDatas.get(position);
        }else{
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private View getView(final int position){
        System.out.println("CommentAdapter getView-----------------------" + position);
        View convertView = View.inflate(mContext,
                R.layout.item_social_comment, null);
        TextView commentTv = (TextView) convertView.findViewById(R.id.commentTv);
        final CircleMovementMethod circleMovementMethod = new CircleMovementMethod(R.color.circle_name_selector_color,
                R.color.circle_name_selector_color);

        final CommentItem bean = mDatas.get(position);
        String name = bean.getUserNickname();
  //      String id = bean.getId();
        String toReplyName = "";
        if (bean.getAppointUserid() != null) {
            toReplyName = bean.getAppointUserNickname();
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setClickableSpan(name,bean.getUserId(), 0));

        if (!TextUtils.isEmpty(toReplyName)) {

            builder.append(" 回复 ");
            builder.append(setClickableSpan(toReplyName,bean.getAppointUserNickname(), 1));
        }
        builder.append(": ");
        //转换表情字符
        String contentBodyStr = bean.getContent();
        //SpannableString contentSpanText = new SpannableString(contentBodyStr);
        //contentSpanText.setSpan(new UnderlineSpan(), 0, contentSpanText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(contentBodyStr);
        commentTv.setText(builder);

        commentTv.setMovementMethod(circleMovementMethod);
        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    mListview.getOnItemClickListener().onItemClick(position);
                }
            }
        });
        commentTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    mListview.getOnItemLongClickListener().onItemLongClick(position);
                    return true;
                }
                return false;
            }
        });


        return convertView;
    }

    @NonNull
    private SpannableString setClickableSpan(String textStr,String userId, int position) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new NameClickable(new NameClickListener(
                        subjectSpanText,userId), position), 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    public void notifyDataSetChanged(){
        if(mListview == null){
            throw new NullPointerException("listview is null, please bindListView first...");
        }
        mListview.removeAllViews();
        if(mDatas == null || mDatas.size() == 0){
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i=0; i<mDatas.size(); i++){
            final int index = i;
            View view = getView(index);
            if(view == null){
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            mListview.addView(view, index, layoutParams);
        }

    }

}
