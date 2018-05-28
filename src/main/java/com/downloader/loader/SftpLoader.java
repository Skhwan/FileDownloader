package com.downloader.loader;

import com.downloader.util.DownloadReporter;
import com.jcraft.jsch.*;
import org.springframework.stereotype.Component;

/**
 * Created by khwanchanok on 5/25/2018 AD.
 */
@Component
public class SftpLoader extends Loader {

    @Override
    public boolean download(String url, String outputName) {
        Session session = null;
        ChannelSftp sftpChannel = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;
            sftpChannel.get(url, outputName);
        }catch (JSchException e){
            DownloadReporter.reportFailedDownload(url, e.getMessage());
            return false;
        }catch (SftpException e){
            DownloadReporter.reportFailedDownload(url, e.getMessage());
            return false;
        }catch (Exception e){
            DownloadReporter.reportFailedDownload(url, e.getMessage());
            return false;
        }finally {
            if(sftpChannel!=null) {
                sftpChannel.exit();
            }
            if (session!=null) {
                session.disconnect();
            }
        }
        DownloadReporter.reportSuccessDownload(url);
        return true;
    }
}
