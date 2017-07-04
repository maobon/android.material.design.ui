package com.xin.bob.materialdesignswipelayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

public class CollapseToolbarActivity extends BaseActivity {

    @Override
    public int getLayoutResId() {
        return R.layout.activity_collapse_toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CollapseToolbarActivity.this, ScrollingActivity.class));
            }
        });
    }
}
