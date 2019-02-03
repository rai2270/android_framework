package com.example.tr.myapplication.view.mvp;

import com.example.tr.myapplication.MyApplication;
import com.example.tr.myapplication.domain.event.ReadyEvent;
import com.example.tr.myapplication.domain.job.local.DoCardTransactionJob;
import com.example.tr.myapplication.domain.job.queue.LocalJobQueue;
import com.example.tr.myapplication.view.mvp.view.IMainActivityView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;


public class MainActivityPresenter {

    @Inject
    LocalJobQueue _localJobQueue;

    private IMainActivityView view;

    public MainActivityPresenter() {
        MyApplication.getInstance().getAppComponent().inject(MainActivityPresenter.this);
    }

    public void setView(IMainActivityView view) {
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

    @Subscribe
    public void receiveReadyEvent(ReadyEvent event) {
        view.showResultsFromJob(event.getTime());
    }


}
