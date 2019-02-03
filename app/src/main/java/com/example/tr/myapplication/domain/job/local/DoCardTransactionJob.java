package com.example.tr.myapplication.domain.job.local;

import com.example.tr.myapplication.domain.event.bus.MainThreadBus;
import com.example.tr.myapplication.domain.job.BaseJob;

import com.example.tr.myapplication.domain.event.ReadyEvent;
import com.example.tr.myapplication.utility.LumberJack;

public class DoCardTransactionJob extends BaseJob {

    private long time;

    public DoCardTransactionJob() {
        super();
    }

    @Override
    public void onRun() throws Throwable {

        // db operation, etc...

        LumberJack.logGeneric("DoCardTransactionJob 0");
        try {
            Thread.sleep(5000);
            LumberJack.logGeneric("DoCardTransactionJob 1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(5000);
            LumberJack.logGeneric("DoCardTransactionJob 2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(5000);
            LumberJack.logGeneric("DoCardTransactionJob 3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        time = System.currentTimeMillis();

        MainThreadBus.getInstance().post(new ReadyEvent(time));
        LumberJack.logGeneric("DoCardTransactionJob 4");

    }

}
