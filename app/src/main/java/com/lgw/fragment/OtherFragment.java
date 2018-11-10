package com.lgw.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lgw.R;
import com.lgw.base.BaseFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;


public class OtherFragment extends Fragment {


    private static OtherFragment otherFragment;

    public static OtherFragment getInstance(){
        if(otherFragment == null){
            synchronized(OtherFragment.class){
                if(otherFragment == null){
                    otherFragment = new OtherFragment();
                }
            }
        }
        return otherFragment;
    }
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       /* View view = inflater.inflate(R.layout.fragment_other,container,false);
        initView(view);
        return view;*/
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_other,container,false);
            initView(rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    public void initView(View view){
        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000/*,false*/);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        List list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("c");
        list.add("c");
        list.add("c");
        list.add("c");
        rv.setAdapter(new OutAdapter(list));
    }

    class OutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        final List list;
        public OutAdapter(List list) {
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if(i==5){
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_inner, null);
                return new InnerHolder(view);
            }else{
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_out, null);
                return new MyViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ((MyViewHolder)viewHolder).tvout.setText(list.get(i).toString());
        }


        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvout = itemView.findViewById(R.id.tv_out);
        }
    }

    class InnerHolder extends RecyclerView.ViewHolder{

        public InnerHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

}
