package com.example.tr.myapplication.domain.event;

import com.example.tr.myapplication.model.data.serverjson.InfoJson;

import java.util.List;

public class InfoJsonEvent {
    private List<InfoJson> data;

    public InfoJsonEvent(List<InfoJson> data) {
        this.data = data;
    }

    public List<InfoJson> getData() {
        return this.data;
    }
}
