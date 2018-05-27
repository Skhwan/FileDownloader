package com.downloader.loader;

import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * Created by khwanchanok on 5/25/2018 AD.
 */
@Component
public class CommonLoader extends Loader {

    @Override
    public void download(String url, String savedPath) throws Exception {
        URL targetUrl = new URL(url);
        BufferedInputStream bis = new BufferedInputStream(targetUrl.openStream());
        FileOutputStream fis = new FileOutputStream(savedPath);
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
