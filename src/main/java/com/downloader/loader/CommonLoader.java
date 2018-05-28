package com.downloader.loader;

import com.downloader.util.DownloadReporter;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by khwanchanok on 5/25/2018 AD.
 */
@Component
public class CommonLoader extends Loader {

    @Override
    public boolean download(String url, String outputName) {
        BufferedInputStream bis = null;
        FileOutputStream fis = null;
        try {
            URL targetUrl = new URL(url);
            bis = new BufferedInputStream(targetUrl.openStream());
            fis = new FileOutputStream(outputName);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fis.write(buffer, 0, count);
            }
        }catch (IOException e){
            DownloadReporter.reportFailedDownload(url, e.getMessage());
            return false;
        }catch (Exception e){
            DownloadReporter.reportFailedDownload(url, e.getMessage());
            return false;
        }finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (bis != null) {
                    bis.close();
                }
            }catch (IOException e){
                logger.error("Got error while closing output stream: {}", e.getMessage());
            }
        }
        DownloadReporter.reportSuccessDownload(url);
        return true;
    }

}
