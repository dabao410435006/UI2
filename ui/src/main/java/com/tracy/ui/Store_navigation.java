package com.tracy.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class Store_navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_navigation);
        MyApplication.getInstance().addActivity(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("周邊物件");


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //設定viewpager及tablayout連動
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        //TabLayoutOnPageChangeListener: tab position is kept in sync
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //設定title要用addOnPageChangeListener
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //螢幕滾動時
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //page滑動時
            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        toolbar.setTitle("周邊物件");
                        break;
                    case 1:
                        toolbar.setTitle("查看物件");
                        break;
                    case 2:
                        toolbar.setTitle("個人資料");
                        break;
                }
            }
            @Override            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        //DrawerLayout實現側滑菜單:DrawerLayout 連接活動的 ActionBar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //將 DrawerLayout 及action bar綁在一起
        //後面的字串參數 openDrawerContentDescRes與 closeDrawerContentDescRes 是 accessibility services 可轉換成語音??
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //將指定的Listener添加到將drawer事件的Listener列表
        drawer.addDrawerListener(toggle);
        //將drawer狀態與 DrawerLayout同步
        toggle.syncState();

        //側邊的drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    //點擊返回按鈕,如果DrawerLayout是打開就關閉
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.store_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.store_logout) {
            startActivity(new Intent(this, Login.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_set) {
            startActivity(new Intent(this, Setting.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //刪除PlaceholderFragment
    /**
     * A {@link FragmentPagerAdapter}回傳一個fragment 相合的tab.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Store_Tab1 tab1 = new Store_Tab1();
                    return tab1;
                case 1:
                    Store_Tab2 tab2 = new Store_Tab2();
                    return tab2;
                case 2:
                    Store_Tab3 tab3 = new Store_Tab3();
                    return tab3;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setTitle("結束程式")
                    .setMessage("是否退出此應用程式")
                    .setPositiveButton("是",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MyApplication.getInstance().exit();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }
}
