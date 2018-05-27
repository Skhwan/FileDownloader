package com.downloader.loader;

import com.jcraft.jsch.*;
import org.springframework.stereotype.Component;

/**
 * Created by khwanchanok on 5/25/2018 AD.
 */
@Component
public class SftpLoader extends Loader {

    @Override
    public void download(String url, String savedPath) throws Exception {
        JSch jsch = new JSch();
        Session session;
        session = jsch.getSession(username, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        sftpChannel.get(url, savedPath);
        sftpChannel.exit();
        session.disconnect();
    }
}
