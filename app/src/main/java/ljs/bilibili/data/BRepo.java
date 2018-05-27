package ljs.bilibili.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ljs.bilibili.entity.BLive;
import ljs.bilibili.entity.BQuery;

public class BRepo {
    private final Executor networkIO = Executors.newFixedThreadPool(5);

    public Listing liveOfB(BQuery bQuery, int pageSize) {

        BDataSource.Factory factory = new BDataSource.Factory(bQuery);

        PagedList.Config config = new PagedList.Config
                .Builder()
                .setEnablePlaceholders(false)
                .setPageSize(pageSize)
                .setPrefetchDistance(pageSize)
                .build();

        LiveData<PagedList<BLive>> pageLive = new LivePagedListBuilder(factory, config).setFetchExecutor(networkIO).build();

        LiveData<Boolean> refreshLive = Transformations.switchMap(factory.dataSourceLive, bDataSource -> bDataSource.refreshLive);

        return new Listing(pageLive, refreshLive, () -> factory.dataSourceLive.getValue().invalidate());
    }
}
