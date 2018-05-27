package ljs.bilibili;

import org.junit.Test;

import ljs.bilibili.entity.BQuery;
import ljs.bilibili.util.HttpUtils;

public class BServiceTest {
    @Test
    public void listLiveTest() {
        BQuery bQuery = new BQuery();
        bQuery.setArea("all");
        bQuery.setPage(1);
        HttpUtils.bService.liveList(bQuery).subscribe(response -> {
            System.out.println(response);
            System.exit(0);
        });
    }

    @Test
    public void playUrlTest() {
        HttpUtils.bService.playUrl("617484").subscribe(response -> {
            System.out.println(response);
            System.exit(0);
        });
    }
}
