package com.example.tr.myapplication.di;

import com.example.tr.myapplication.BuildConfig;
import com.example.tr.myapplication.domain.job.queue.LocalJobQueue;
import com.example.tr.myapplication.network.retrofit.RestFetchService;
import com.example.tr.myapplication.view.mvp.MainActivityPresenter;
import com.example.tr.myapplication.view.mvp.MainFragmentPresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    // Dagger will only look for methods annotated with @Provides
    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    static MainActivityPresenter providesMainActivityPresenter() {
        return new MainActivityPresenter();
    }

    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    static MainFragmentPresenter providesMainFragmentPresenter() {
        return new MainFragmentPresenter();
    }

    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    static LocalJobQueue providesLocalJobQueue() {
        return new LocalJobQueue();
    }


    @Provides
    @Singleton
    RestFetchService provideRestService() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BuildConfig.REST_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = retrofitBuilder.client(httpClient).build();
        return retrofit.create(RestFetchService.class);
    }
}
