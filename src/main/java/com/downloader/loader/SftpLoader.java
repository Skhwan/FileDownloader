package com.downloader.loader;

import com.jcraft.jsch.*;

/**
 * Created by khwanchanok on 5/25/2018 AD.
 */
public class SftpLoader extends Loader {

    private String host;
    private int port;
    private String username;
    private String password;

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

    @Override
    public void download(String url, String savePath) throws Exception {
        JSch jsch = new JSch();
        Session session;
        session = jsch.getSession(username, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        sftpChannel.get(url, savePath);
        sftpChannel.exit();
        session.disconnect();
    }
}
