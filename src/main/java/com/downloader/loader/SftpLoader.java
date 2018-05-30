package com.downloader.loader;

import com.downloader.util.DownloadReporter;
import com.downloader.util.Protocol;
import com.jcraft.jsch.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by khwanchanok on 5/25/2018 AD.
 */
@Component
public class SftpLoader extends Loader {

    @Override
    public boolean download(String url, String outputName) {
        Session session = null;
        ChannelSftp sftpChannel = null;
        File file = new File(outputName);
        try {
            JSch jsch = new JSch();
            FileOutputStream fis = new FileOutputStream(file);
            session = jsch.getSession(username, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            Channel channel = session.openChannel(Protocol.SFTP.name().toLowerCase());
            channel.connect();
            sftpChannel = (ChannelSftp) channel;
            String sourceFile = getSourceFilePath(url);

            DownloadReporter.startProgressReport(url);
            sftpChannel.get(sourceFile, fis);
            DownloadReporter.stopProgressReport();
        }catch (Exception e){
            //delete incomplete file if got exception while downloading
            file.delete();
            DownloadReporter.stopProgressReport();
            DownloadReporter.reportFailedDownload(e.getMessage());
            return false;
        }finally {
            if(sftpChannel!=null) {
                sftpChannel.exit();
            }
            if (session!=null) {
                session.disconnect();
            }
        }
        DownloadReporter.reportSuccessDownload();
        return true;
    }

    /**
     * @param url
     * @return String path to target file on remote host
     */
    public String getSourceFilePath(String url){
        String sourceFile = url.substring(url.indexOf("/") + 2);
        sourceFile = sourceFile.substring(sourceFile.indexOf("/"));
        return sourceFile;
    }
}
