package com.downloader.loader;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * Created by khwanchanok on 5/25/2018 AD.
 */
public class Loader {

    public void download(String url, String savePath) throws Exception {
        URL targetUrl = new URL(url);
        BufferedInputStream bis = new BufferedInputStream(targetUrl.openStream());
        FileOutputStream fis = new FileOutputStream(savePath);
        byte[] buffer = new byte[1024];
        int count;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

}
