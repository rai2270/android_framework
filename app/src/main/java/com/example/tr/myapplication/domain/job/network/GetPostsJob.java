package com.example.tr.myapplication.domain.job.network;

import com.example.tr.myapplication.MyApplication;
import com.example.tr.myapplication.domain.event.InfoJsonEvent;
import com.example.tr.myapplication.domain.job.BaseJob;
import com.example.tr.myapplication.model.data.serverjson.InfoJson;
import com.example.tr.myapplication.network.retrofit.RestFetchService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class GetPostsJob extends BaseJob {
    @Inject
    RestFetchService _restService;

    public GetPostsJob() {
        super();
        MyApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void onRun() throws Throwable {
        Call<List<InfoJson>> call = _restService.getPosts();
        Response<List<InfoJson>> response = call.execute();
        if (response.isSuccessful()) {
            List<InfoJson> data = response.body();

            // do something with the data...

            EventBus.getDefault().post(new InfoJsonEvent(data));
        } else {

        }
    }

}

