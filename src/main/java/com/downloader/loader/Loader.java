package com.downloader.loader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by khwanchanok on 5/27/2018 AD.
 */
public abstract class Loader {

    Logger logger = LogManager.getLogger(Loader.class);

    String host;
    int port;
    String username;
    String password;

    public void configDownload(String host, int port, String username, String password){
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public abstract boolean download(String url, String outputName);
}
