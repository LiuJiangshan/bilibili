package ljs.bilibili.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ljs.android.activity.BaseActivity;
import ljs.bilibili.R;
import ljs.bilibili.util.HttpUtils;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);
    }

    public void test(View view) {
        HttpUtils.bService.playUrl("617484")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        re -> System.out.println(re),
                        e -> System.out.println(e));
    }
}
