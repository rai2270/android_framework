package com.example.tr.myapplication.view.mvp;

import com.example.tr.myapplication.MyApplication;
import com.example.tr.myapplication.domain.event.ReadyEvent;
import com.example.tr.myapplication.domain.job.local.DoCardTransactionJob;
import com.example.tr.myapplication.domain.job.queue.LocalJobQueue;
import com.example.tr.myapplication.utility.LumberJack;
import com.example.tr.myapplication.view.mvp.view.IMainFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

public class MainFragmentPresenter {
    @Inject
    LocalJobQueue _localJobQueue;

    private IMainFragmentView view;

    public MainFragmentPresenter() {
        MyApplication.getInstance().getAppComponent().inject(MainFragmentPresenter.this);
    }

    public void setView(IMainFragmentView view) {
        this.view = view;
    }

    public void start() {
        EventBus.getDefault().register(this);
    }

    public void resume() {
    }

    public void pause() {
    }

    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    public void doPriQ() {
        _localJobQueue.addJobInBackground(new DoCardTransactionJob());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveReadyEvent(ReadyEvent event) {
        LumberJack.logGeneric("MainFragmentPresenter: receiveReadyEvent");
        view.showResultsFromJob(event.getTime());
    }
}
