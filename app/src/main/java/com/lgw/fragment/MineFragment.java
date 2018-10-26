package com.lgw.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lgw.R;


public class MineFragment extends Fragment {

    private static MineFragment mineFragment;

    public static MineFragment getInstance(){
        if(mineFragment == null){
            synchronized(MineFragment.class){
                if(mineFragment == null){
                    mineFragment = new MineFragment();
                }
            }
        }
        return mineFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine,container,false);
    }


}
