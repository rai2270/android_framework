package com.example.tr.myapplication.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tr.myapplication.MyApplication;
import com.example.tr.myapplication.R;
import com.example.tr.myapplication.domain.event.InfoJsonEvent;
import com.example.tr.myapplication.domain.event.bus.MainThreadBus;
import com.example.tr.myapplication.domain.job.network.GetPostsJob;
import com.example.tr.myapplication.domain.job.queue.LocalJobQueue;
import com.example.tr.myapplication.utility.BusUtils;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestFragment extends Fragment {
    @Inject
    LocalJobQueue _localJobQueue;

    public RestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rest, container, false);
        ButterKnife.bind(this, v);
        MyApplication.getInstance().getAppComponent().inject(RestFragment.this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BusUtils.registerBusIfNotRegistered(this);
    }

    @Override
    public void onDestroyView() {
        BusUtils.unregisterBusIfRegistered(this);
        super.onDestroyView();
    }

    @OnClick(R.id.button6)
    void testRest() {
        _localJobQueue.addJobInBackground(new GetPostsJob());
    }

    @Subscribe
    public void receiveReadyEvent(InfoJsonEvent event) {
        // do something with the event.data
    }

}
