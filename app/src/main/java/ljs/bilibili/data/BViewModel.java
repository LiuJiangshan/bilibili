package ljs.bilibili.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import ljs.bilibili.entity.BLive;
import ljs.bilibili.entity.BQuery;

public class BViewModel extends ViewModel {
    public LiveData<PagedList<BLive>> pageLive;
    public LiveData<Boolean> refreshLive;

    private LiveData<Listing> resultLive;

    public BViewModel(BQuery bQuery) {
        BRepo bRepo = new BRepo();

        MutableLiveData<BQuery> areaLive = new MutableLiveData<>();

        areaLive.setValue(bQuery);

        resultLive = Transformations.map(areaLive, area -> bRepo.liveOfB(area, 30));

        pageLive = Transformations.switchMap(resultLive, listing -> listing.pageLive);

        refreshLive = Transformations.switchMap(resultLive, listing -> listing.refreshLive);
    }

    public void refresh() {
        resultLive.getValue().refresh.run();
    }
}
