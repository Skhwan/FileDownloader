package com.downloader.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by khwanchanok on 5/28/2018 AD.
 */
public final class DownloadReporter {
    private static ScheduledExecutorService service;

    public static void reportSuccessDownload(){
        System.out.println("Done");
    }

    public static void reportFailedDownload(String cause){
        System.out.printf("Failed...%s\n", cause);
    }

    public static void reportException(String exception){
        System.out.println(exception);
    }

    public static void startProgressReport(String url){
        System.out.printf("Downloading [%s]\n", url);
        Runnable runnable = () -> System.out.print(".");
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
    }

    public static void stopProgressReport(){
        service.shutdown();
    }
}
