package ljs.bilibili.ui.live;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ljs.android.fragment.BaseFragment;
import ljs.bilibili.R;
import ljs.bilibili.base.BaseBFragment;
import ljs.bilibili.data.Area;
import ljs.bilibili.data.Order;
import ljs.bilibili.ui.live.area.BaseAreaFragment;

public class LiveFragment extends BaseBFragment {
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    List<BaseAreaFragment> fragments;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.float_top)
    FloatingActionButton float_top;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        getBActivity().setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getBActivity(), drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        if (fragments == null) {
            fragments = new ArrayList<>();
            BaseAreaFragment fragment = new BaseAreaFragment();
            fragment.area = Area.all;
            fragment.order = Order.liveTime;
            fragment.title = Order.liveTime.title;
            fragments.add(fragment);

            for (Area area : Area.values()) {
                fragment = new BaseAreaFragment();
                fragment.area = area;
                fragment.title = area.title;
                fragments.add(fragment);
            }
        }

        view_pager.setAdapter(new FragmentStatePagerAdapter(getBActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return ((BaseFragment) getItem(position)).getTitle();
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        tab_layout.setupWithViewPager(view_pager);

        View.OnClickListener onFloatTopClick = v -> fragments.get(view_pager.getCurrentItem()).scrollToTop();

        float_top.setOnClickListener(onFloatTopClick);

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                float_top.setOnClickListener(state == ViewPager.SCROLL_STATE_IDLE ? onFloatTopClick : null);
            }
        });
    }

    @Override
    public String getTitle() {
        return "直播";
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_live_layout;
    }
}
