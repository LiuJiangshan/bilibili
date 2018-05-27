package ljs.bilibili.service;

import java.util.List;

import io.reactivex.Observable;
import ljs.bilibili.entity.BLive;
import ljs.bilibili.entity.BQuery;
import ljs.bilibili.entity.BResponse;
import ljs.bilibili.entity.PlaySource;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface BService {
    @GET("area/liveList")
    Observable<BResponse<List<BLive>>> liveList(@QueryMap BQuery query);

    //room/v1/Room/playUrl?cid=617484&quality=0&platform=web
    @GET("room/v1/Room/playUrl")
    Observable<BResponse<PlaySource>> playUrl(@Query("cid") String roomId);
}
