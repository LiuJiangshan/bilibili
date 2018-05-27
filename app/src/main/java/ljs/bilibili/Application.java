package ljs.bilibili;

import com.squareup.picasso.Picasso;

import io.reactivex.plugins.RxJavaPlugins;
import ljs.android.BaseApplication;

public class Application extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        RxJavaPlugins.setErrorHandler(throwable -> toast(throwable.getMessage()));

        Picasso picasso = new Picasso.Builder(this)
                .listener((picasso1, uri, exception) -> {
                    toast(exception.getMessage());
                    System.out.println(exception);
                }).build();

        picasso.setLoggingEnabled(isDebug());

        picasso.setIndicatorsEnabled(isDebug());

        Picasso.setSingletonInstance(picasso);
    }

    @Override
    public boolean isMultiDex() {
        return true;
    }
}
