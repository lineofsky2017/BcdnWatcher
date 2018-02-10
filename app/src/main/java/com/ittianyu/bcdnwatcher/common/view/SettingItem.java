package com.ittianyu.bcdnwatcher.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ittianyu.bcdnwatcher.R;

public class SettingItem extends FrameLayout {
    // data
    private String title;
    private String content;
    private boolean showArrow;
    // view
    private TextView tvTitle;
    private ImageView ivArrow;
    private TextView tvContent;


    public SettingItem(Context context) {
        super(context);
        init(null, 0);
    }

    public SettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SettingItem, defStyle, 0);

        title = a.getString(R.styleable.SettingItem_title);
        content = a.getString(R.styleable.SettingItem_content);
        showArrow = a.getBoolean(R.styleable.SettingItem_showArrow, true);
        a.recycle();

        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_setting_item, this, false);
        addView(view);

        tvTitle = view.findViewById(R.id.tv_title);
        tvContent = view.findViewById(R.id.tv_content);
        ivArrow = view.findViewById(R.id.iv_arrow);

        setTitle(title);
        setContent(content);
        setVisibility(showArrow ? VISIBLE : GONE);
    }

    public void setTitle(CharSequence text) {
        tvTitle.setText(text);
    }

    public void setContent(CharSequence text) {
        tvContent.setText(text);
    }

    public void setArrowVisibility(int visibility) {
        ivArrow.setVisibility(visibility);
    }

    public CharSequence getTitle() {
        return tvTitle.getText();
    }

}
