package com.downloader.util;

/**
 * Created by khwanchanok on 5/28/2018 AD.
 */
public final class DownloadReporter {
    public static void reportSuccessDownload(String url){
        System.out.printf("Done.....%s\n", url);
    }

    public static void reportFailedDownload(String url, String cause){
        System.out.printf(
                "Failed...%s\n" +
                ".........%s\n", url, cause);
    }

    public static void reportException(String exception){
        System.out.println(exception);
    }
}
