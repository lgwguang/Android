package com.lgw.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lgw.R;
import com.lgw.widget.HandleDrawerLayout;

public class HandleDrawerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_drawer);

        final HandleDrawerLayout handleDrawerLayout =findViewById(R.id.hdl_layout);
        View handleView = findViewById(R.id.hdl_handle_view);
        /*handleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handleDrawerLayout.isDrawerOpen()){
                    handleDrawerLayout.closeDrawer();
                }else{
                    handleDrawerLayout.openDrawer();
                }
            }
        });*/

    }
}
