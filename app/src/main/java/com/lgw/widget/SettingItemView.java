package com.lgw.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgw.R;

public class SettingItemView extends RelativeLayout {

    TextView tv_title,tv_desc;

    CheckBox cb_status;

    String desc_on, desc_off;

    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context,R.layout.setting_item_view,this);
        tv_title = (TextView)this.findViewById(R.id.tv_title);
        tv_desc = (TextView)this.findViewById(R.id.tv_desc);
        cb_status = (CheckBox)this.findViewById(R.id.cb_status);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        String title = ta.getString(R.styleable.SettingItemView_setting_title);
        desc_on = ta.getString(R.styleable.SettingItemView_desc_on);
        desc_off = ta.getString(R.styleable.SettingItemView_desc_off);
        tv_title.setText(title);
        ta.recycle();
    }



    public boolean isChecked(){
        return cb_status.isChecked();
    }

    public void setChecked(boolean checked){
        if(checked){
            tv_desc.setText(desc_on);
        }else{
            tv_desc.setText(desc_off);
        }
        cb_status.setChecked(checked);
    }


}
