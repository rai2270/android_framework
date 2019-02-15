package com.example.tr.myapplication.domain.job.local;

import android.os.Build;

import com.birbit.android.jobqueue.Params;
import com.example.tr.myapplication.domain.event.MessageEvent;
import com.example.tr.myapplication.domain.job.BaseJob;

import com.example.tr.myapplication.domain.event.ReadyEvent;
import com.example.tr.myapplication.utility.LumberJack;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DoCardTransactionJob extends BaseJob {

    private long time;
    private int counter;

    public DoCardTransactionJob(int counter) {
        // Set this job to be persistent:
        // Even if app is closed, job will run again when reopen.
        super(new Params(1).setPersistent(true));
        // members also saved in case app is closed and rerun (on persistent)
        this.counter = counter;
    }

    @Override
    public void onRun() throws Throwable {

        // db operation, etc...

        LumberJack.logGeneric("DoCardTransactionJob " + counter++);
        sendToFTP();
        try {
            Thread.sleep(5000);
            LumberJack.logGeneric("DoCardTransactionJob " + counter++);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendToFTP();
        try {
            Thread.sleep(5000);
            LumberJack.logGeneric("DoCardTransactionJob " + counter++);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendToFTP();
        try {
            Thread.sleep(5000);
            LumberJack.logGeneric("DoCardTransactionJob " + counter++);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendToFTP();
        time = System.currentTimeMillis();

        EventBus.getDefault().postSticky(new ReadyEvent(time));
        EventBus.getDefault().post(new MessageEvent("" + time));
        LumberJack.logGeneric("DoCardTransactionJob " + counter++);

    }

    private void sendToFTP() {
        // https://dlptest.com/ftp-test/

        FTPClient con = null;
        try {
            con = new FTPClient();
            con.connect("ftp.dlptest.com");

            if (con.login("dlpuser@dlptest.com", "puTeT3Yei1IJ4UYT7q0r")) {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);

                String fileName = getDeviceName() + "-" + System.currentTimeMillis();
                InputStream stream = new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8));
                boolean result = con.storeFile("/" + fileName, stream);
                stream.close();
                if (result) LumberJack.logGeneric("upload result", "succeeded");
                con.logout();
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + "-" + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

}
