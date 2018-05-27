package com.downloader.loader;

/**
 * Created by khwanchanok on 5/27/2018 AD.
 */
public abstract class Loader {

    String host;
    int port;
    String username;
    String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    abstract void download(String url, String savedPath) throws Exception;
}
