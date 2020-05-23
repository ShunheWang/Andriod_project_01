package com.example.lingquanlianmeng.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.base.BaseFragment;
import com.example.lingquanlianmeng.ui.fragment.HomeFragment;
import com.example.lingquanlianmeng.ui.fragment.RedPacketFragment;
import com.example.lingquanlianmeng.ui.fragment.SearchFragment;
import com.example.lingquanlianmeng.ui.fragment.SelectedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.main_navigation_bar)
    public BottomNavigationView mNavigationView;
    private HomeFragment mHomeFragment;
    private RedPacketFragment mRedPacketFragment;
    private SelectedFragment mSelectedFragment;
    private SearchFragment mSearchFragment;
    private FragmentManager mFm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragments();
        initListener();
    }

    private void initFragments() {
        mHomeFragment = new HomeFragment();
        mRedPacketFragment = new RedPacketFragment();
        mSelectedFragment = new SelectedFragment();
        mSearchFragment = new SearchFragment();
        mFm = getSupportFragmentManager();
        switchFragment(mHomeFragment);
    }

    private void initListener() {
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Log.d(TAG, "title -- >" + item.getTitle());
                if (item.getItemId() == R.id.home) {
//                    LogUtils.d(this, "home");
                    switchFragment(mHomeFragment);
                }else if (item.getItemId() == R.id.selected) {
//                    LogUtils.d(this, "selected");
                    switchFragment(mSelectedFragment);
                }else if (item.getItemId() == R.id.red_packet) {
//                    LogUtils.d(this, "red_packet");
                    switchFragment(mRedPacketFragment);
                }else if (item.getItemId() == R.id.search) {
//                    LogUtils.d(this, "search");
                    switchFragment(mSearchFragment);
                }
                return true;
            }
        });
    }

    private void switchFragment(BaseFragment target) {
        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
        fragmentTransaction.replace(R.id.main_page_container,target);
        fragmentTransaction.commit();

    }


}
