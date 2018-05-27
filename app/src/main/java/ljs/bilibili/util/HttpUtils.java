package ljs.bilibili.util;

import java.io.IOException;

import ljs.bilibili.service.BService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtils {
    static class MyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
//            String body = response.body().string();
            return response;
        }
    }

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new MyInterceptor()).build();
    private static final Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.live.bilibili.com/")
            .client(okHttpClient)
            .build();
    public static final BService bService = retrofit.create(BService.class);
}
