package ljs.bilibili.data;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import ljs.bilibili.entity.BLive;

public class Listing {
    public LiveData<PagedList<BLive>> pageLive;
    public LiveData<Boolean> refreshLive;
    public Runnable refresh;

    public Listing(LiveData<PagedList<BLive>> pageLive, LiveData<Boolean> refreshLive, Runnable refresh) {
        this.pageLive = pageLive;
        this.refreshLive = refreshLive;
        this.refresh = refresh;
    }
}
