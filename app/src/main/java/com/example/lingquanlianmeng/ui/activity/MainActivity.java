package com.example.lingquanlianmeng.ui.activity;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.base.BaseActivity;
import com.example.lingquanlianmeng.base.BaseFragment;
import com.example.lingquanlianmeng.ui.fragment.HomeFragment;
import com.example.lingquanlianmeng.ui.fragment.RedPacketFragment;
import com.example.lingquanlianmeng.ui.fragment.SearchFragment;
import com.example.lingquanlianmeng.ui.fragment.SelectedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.main_navigation_bar)
    public BottomNavigationView mNavigationView;
    private HomeFragment mHomeFragment;
    private RedPacketFragment mRedPacketFragment;
    private SelectedFragment mSelectedFragment;
    private SearchFragment mSearchFragment;
    private FragmentManager mFm;

    @Override
    protected void initView() {
        initFragments();
    }

    @Override
    protected void initEvent() {
        initListener();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {

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

    private BaseFragment lastOneFragment = null;

    private void switchFragment(BaseFragment target) {
        FragmentTransaction fragmentTransaction = mFm.beginTransaction();

        if(!target.isAdded()){
            fragmentTransaction.add(R.id.main_page_container,target);
        }else{
            fragmentTransaction.show(target);
        }
        if (lastOneFragment != null) {
            fragmentTransaction.hide(lastOneFragment);
        }
        lastOneFragment = target;

        //fragmentTransaction.replace(R.id.main_page_container,target);
        fragmentTransaction.commit();

    }


}
