package com.tracy.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
/*private int[] setIcons = {
            R.drawable.user_home,
            R.drawable.user_heart,

    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyApplication.getInstance().addActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ViewPager viewPager = (ViewPager) findViewById(R.id.RLpager);
        //getSupportFragmentManager()管理活動內的區塊物件
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.RLtabs);
        //設定tab被點到時的顏色
        tabLayout.setTabTextColors(Color.GRAY,Color.argb(255,35,96,137));

        // 在viewpager增加區塊
        adapter.addFragment(new User_login(), "一般登入");
        adapter.addFragment(new Store_login(), "店家登入");
        viewPager.setAdapter(adapter);
        //讓tablayout與viewpager連動
        tabLayout.setupWithViewPager(viewPager);

/*for(int i =0; i < setIcons.length;i++){
            tabLayout.getTabAt(i).setIcon(setIcons[i]);


        }*/

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();



        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override //獲取需要的view 數量
        public int getCount() {
            return mFragmentList.size();
        }
        //增加區塊
        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);




        }

        @Override
        public CharSequence getPageTitle( int position) {

            return mFragmentTitleList.get(position);
        }
    }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setTitle("結束程式")
                    .setMessage("是否退出此應用程式")
                    .setPositiveButton("是",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
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
