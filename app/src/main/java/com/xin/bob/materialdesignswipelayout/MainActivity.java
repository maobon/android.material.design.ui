package com.xin.bob.materialdesignswipelayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity {

    private static final String[] DATA_POOL = {
            "Apple", "Google", "Motorola",
            "SONY", "LG", "Walkman", "Microsoft"
    };

    private DrawerLayout mNaviDrawer;
    private NavigationView mNavigationView;
    private SwipeRefreshLayout mRefreshLayout;
    private ArrayAdapter adapter;
    private List<String> mData = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generateMockData();
        recomposeToolbar();
        initSideNaviMenu();
        initListView();
        updateDataListView();
    }

    private void updateDataListView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_swipe_refresh_layout);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            generateMockData();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    mRefreshLayout.setRefreshing(false);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    private void generateMockData() {
        mData.clear();
        for (int i = 0; i < 50; i++) {
            int x = new Random().nextInt(DATA_POOL.length);
            mData.add(DATA_POOL[x]);
        }
    }

    private void recomposeToolbar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
    }

    private void initSideNaviMenu() {
        mNaviDrawer = (DrawerLayout) findViewById(R.id.dl_navi_drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.nv_navi_view_menu);
        mNavigationView.setCheckedItem(R.id.nav_acc);

        // return true 选中的item的位置才会被记录下来
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_acc:
                                mNaviDrawer.closeDrawers();
                                break;

                            case R.id.nav_com:
                                mNaviDrawer.closeDrawers();
                                break;

                            case R.id.nav_err:
                                mNaviDrawer.closeDrawers();
                                break;
                        }

                        Snackbar.make(mNavigationView, "Snackbar Test", Snackbar.LENGTH_SHORT)
                                .setAction("YES", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .show();
                        return true;
                    }
                });
    }

    private void initListView() {
        ListView lv = (ListView) findViewById(R.id.lv_listview);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mData);
        lv.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lv.setNestedScrollingEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNaviDrawer.openDrawer(GravityCompat.START);
                break;
            case R.id.action_acc:
                break;
            case R.id.action_computer:
                break;
            case R.id.action_error:
                startActivity(new Intent(MainActivity.this, CollapseToolbarActivity.class));
                break;
        }
        return true;
    }
}
