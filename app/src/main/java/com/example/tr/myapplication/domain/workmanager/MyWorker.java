package com.example.tr.myapplication.domain.workmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.example.tr.myapplication.R;
import com.example.tr.myapplication.utility.LumberJack;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_TEXT = "text";
    public static final String EXTRA_OUTPUT_MESSAGE = "output_message";

    int counter = 5; // test if workmanager keep values in case we force close the app

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        //Executed on different thread

        for (int i = 0; i < 5; i++) {
            LumberJack.logGeneric("MyWorker - " + counter++);
            sendToFTP();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String title = getInputData().getString(EXTRA_TITLE);
        String text = getInputData().getString(EXTRA_TEXT);

        //sendNotification("Simple Work Manager", "I have been send by WorkManager!");
        sendNotification(title, text);

        Data output = new Data.Builder()
                .putString(EXTRA_OUTPUT_MESSAGE, "I have come from MyWorker!")
                .build();

        return Result.success(output);
    }

    public void sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
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

