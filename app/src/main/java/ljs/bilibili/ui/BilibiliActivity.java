package ljs.bilibili.ui;

import android.os.Bundle;

import butterknife.ButterKnife;
import ljs.bilibili.base.BaseBActivity;
import ljs.bilibili.ui.live.LiveFragment;

public class BilibiliActivity extends BaseBActivity {
    private LiveFragment liveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        if (liveFragment == null)
            liveFragment = new LiveFragment();

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, liveFragment).commit();
    }
}
