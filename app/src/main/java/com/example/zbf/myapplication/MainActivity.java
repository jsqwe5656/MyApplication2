package com.example.zbf.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.zbf.myapplication.R;
import com.example.zbf.myapplication.fragment.BlankFragment1;
import com.example.zbf.myapplication.fragment.BlankFragment2;
import com.example.zbf.myapplication.fragment.BlankFragment3;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{

    @BindView(R.id.home_ln_replace)
    FrameLayout homeLnReplace;
    @BindView(R.id.home_bnb)
    BottomNavigationBar homeBnb;

    BlankFragment1 fragment1;
    BlankFragment2 fragment2;
    BlankFragment3 fragment3;

    FragmentManager manager;
    FragmentTransaction transaction;

    List<Fragment> fragments;


    int fragment_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        manager = getFragmentManager();
        fragments = new ArrayList<>();
        init(savedInstanceState);

    }

    /**
     * 初始化试图
     *
     * @param savedInstanceState
     */
    private void init(Bundle savedInstanceState)
    {
        homeBnb.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "主页"));
        homeBnb.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "主页2"));
        homeBnb.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "主页3"));
        homeBnb.setFirstSelectedPosition(0).initialise();
        homeBnb.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(int position)
            {
                switch (position)
                {
                    case 0:
                        fragment_index = 0;
                        break;
                    case 1:
                        fragment_index = 1;
                        break;
                    case 2:
                        fragment_index = 2;
                        break;
                }
                switchFragment(fragment_index);
            }

            @Override
            public void onTabUnselected(int position)
            {

            }

            @Override
            public void onTabReselected(int position)
            {

            }
        });

        if (savedInstanceState != null)
        {
            fragment_index = savedInstanceState.getInt("index");
            fragment1 = (BlankFragment1) manager.findFragmentByTag("home1");
            fragment2 = (BlankFragment2) manager.findFragmentByTag("home2");
            fragment3 = (BlankFragment3) manager.findFragmentByTag("home3");
            fragments.add(fragment1);
            fragments.add(fragment2);
            fragments.add(fragment3);
        } else
        {
            fragmentInit();
        }
        homeBnb.selectTab(fragment_index,true);

    }

    /**
     * 初始化碎片
     */
    private void fragmentInit()
    {
        transaction = manager.beginTransaction();
        fragment1 = new BlankFragment1();
        fragment2 = new BlankFragment2();
        fragment3 = new BlankFragment3();

        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);

        transaction.add(R.id.home_ln_replace, fragment1, "home1");
        transaction.add(R.id.home_ln_replace, fragment2, "home2");
        transaction.add(R.id.home_ln_replace, fragment3, "home3");

        transaction.commitAllowingStateLoss();
    }

    /**
     * 选择碎片
     */
    private void switchFragment(int index)
    {
        transaction = manager.beginTransaction();
        for (int i = 0; i < 2; i++)
        {
            Fragment fragment = fragments.get(i);
            if (i == index)
            {
                transaction.show(fragment);
                continue;
            }
            transaction.hide(fragment);
        }
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putInt("index", fragment_index);
        super.onSaveInstanceState(outState);
    }
}
