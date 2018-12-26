package com.example.tr.myapplication.di;

import com.example.tr.myapplication.domain.job.network.GetPostsJob;
import com.example.tr.myapplication.view.MainActivity;
import com.example.tr.myapplication.view.RestFragment;
import com.example.tr.myapplication.view.ThreadFragment;
import com.example.tr.myapplication.view.mvp.MainActivityPresenter;
import com.example.tr.myapplication.view.mvp.MainFragmentPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(MainActivityPresenter mainActivityPresenter);

    void inject(ThreadFragment threadFragment);

    void inject(MainFragmentPresenter mainFragmentPresenter);

    void inject(GetPostsJob getPostsJob);

    void inject(RestFragment restFragment);


}
