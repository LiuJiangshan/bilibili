package ljs.bilibili.ui.live.area;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ljs.android.fragment.BaseFragment;
import ljs.bilibili.R;
import ljs.bilibili.data.Area;
import ljs.bilibili.data.BPageAdapter;
import ljs.bilibili.data.BViewModel;
import ljs.bilibili.data.Order;
import ljs.bilibili.entity.BQuery;

public class BaseAreaFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refresh_view;
    BPageAdapter adapter = new BPageAdapter();
    BViewModel viewModel;

    public BQuery getQuery() {
        BQuery bQuery = new BQuery();
        if (area != null)
            bQuery.setArea(area.value);
        if (order != null)
            bQuery.setOrder(order.value);
        return bQuery;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public Area area;
    public Order order;
    public String title;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_live_area_layout;
    }

    private GridLayoutManager gridLayoutManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        if (viewModel == null)
            viewModel = new BViewModel(getQuery());

        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerview.setLayoutManager(gridLayoutManager);

        recyclerview.setAdapter(adapter);

        viewModel.pageLive.observe(this,
                liveData -> adapter.submitList(liveData));

        refresh_view.setOnRefreshListener(() -> viewModel.refresh());
        viewModel.refreshLive.observe(this, refresh -> {
            if (refresh != null) refresh_view.setRefreshing(refresh);
        });
    }

    public void scrollToTop() {
        gridLayoutManager.scrollToPosition(0);
    }
}
