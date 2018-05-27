package com.downloader.controller;

import com.downloader.loader.CommonLoader;
import com.downloader.loader.SftpLoader;
import com.downloader.util.ConfigProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by khwanchanok on 5/27/2018 AD.
 */
@Component
public class LoaderController {

    @Autowired
    CommonLoader commonLoader;

    @Autowired
    SftpLoader sftpLoader;

    @Value(ConfigProperty.SAVED_PATH) String savedPath;

    public void download(String url) throws Exception {
        commonLoader.download(url, savedPath);
    }

    public void secureDownload(String url, String host, int port, String username, String password) throws Exception {
        sftpLoader.setHost(host);
        sftpLoader.setPort(port);
        sftpLoader.setUsername(username);
        sftpLoader.setPassword(password);
        sftpLoader.download(url, savedPath);
    }
}
