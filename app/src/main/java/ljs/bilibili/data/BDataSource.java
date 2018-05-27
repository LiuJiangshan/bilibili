package ljs.bilibili.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import ljs.bilibili.entity.BLive;
import ljs.bilibili.entity.BQuery;
import ljs.bilibili.util.HttpUtils;

public class BDataSource extends PageKeyedDataSource<Integer, BLive> {
    public MutableLiveData<Boolean> refreshLive = new MutableLiveData<>();
    private BQuery bQuery;

    public BDataSource(BQuery bQuery) {
        this.bQuery = bQuery;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {
        refreshLive.postValue(true);
        bQuery.setPage(1);
        HttpUtils.bService.liveList(bQuery).subscribe(
                response -> callback.onResult(response.getData(), null, 2),
                e -> refreshLive.postValue(false),
                () -> refreshLive.postValue(false));
    }

    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {
        refreshLive.postValue(true);
        Integer page = Integer.parseInt(params.key.toString());
        bQuery.setPage(page);
        HttpUtils.bService.liveList(bQuery).subscribe(
                response -> callback.onResult(response.getData(), page + 1),
                e -> refreshLive.postValue(false),
                () -> refreshLive.postValue(false));

    }

//    @Override
//    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<BLive> callback) {
//        HttpUtils.bService.liveList("all", (params.requestedStartPosition / params.pageSize) + 1).subscribe(
//                response -> callback.onResult(response.getData(), params.requestedStartPosition),
//                e -> refreshLive.postValue(false),
//                () -> refreshLive.postValue(false));
//    }
//
//    @Override
//    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<BLive> callback) {
//        HttpUtils.bService.liveList("all", (params.startPosition / params.loadSize) + 1).subscribe(
//                response -> callback.onResult(response.getData()),
//                e -> refreshLive.postValue(false),
//                () -> refreshLive.postValue(false));
//    }

    static class Factory extends DataSource.Factory<Integer, BLive> {
        public MutableLiveData<BDataSource> dataSourceLive = new MutableLiveData();

        private BQuery bQuery;

        public Factory(BQuery bQuery) {
            this.bQuery = bQuery;
        }

        @Override
        public DataSource<Integer, BLive> create() {
            BDataSource bDataSource = new BDataSource(bQuery);
            dataSourceLive.postValue(bDataSource);
            return bDataSource;
        }
    }

}
