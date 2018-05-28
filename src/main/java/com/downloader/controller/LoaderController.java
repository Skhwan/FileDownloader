package com.downloader.controller;

import com.downloader.loader.CommonLoader;
import com.downloader.loader.SftpLoader;
import com.downloader.util.ConfigProperty;
import com.downloader.util.DownloadReporter;
import com.downloader.util.FileManager;
import com.downloader.util.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by khwanchanok on 5/27/2018 AD.
 */
@Component
public class LoaderController {

    @Autowired
    CommonLoader commonLoader;

    @Autowired
    SftpLoader sftpLoader;

    @Autowired
    FileManager fileManager;

    @Value(ConfigProperty.SAVED_PATH) private String savedPath;
    @Value(ConfigProperty.SUPPORTED_PROTOCOL) private List<String> supportedProtocol;
    private Map<String, Boolean> downloadMap;

    public void download(String sourcePath) {
        fileManager.prepareFiles(sourcePath, savedPath);
        Map<String, String> fileNames = fileManager.getDownloadNames();
        Map<String, Boolean> urls = fileManager.getDownloadStatusMap();

        boolean downloadResult;
        String outputName;
        for (String url:urls.keySet()) {
            if(isCommonProtocol(url)) {
                outputName = savedPath + fileNames.get(url);
                downloadResult = commonLoader.download(url, outputName);
                urls.put(url, downloadResult);
            }else{
                DownloadReporter.reportFailedDownload(url, "Unsupported protocol");
            }
        }
        downloadMap = fileManager.getDownloadStatusMap();
    }

    public void secureDownload(String sourcePath, String host, int port, String username, String password) {
        fileManager.prepareFiles(sourcePath, savedPath);
        Map<String, String> fileNames = fileManager.getDownloadNames();
        Map<String, Boolean> urls = fileManager.getDownloadStatusMap();

        sftpLoader.configDownload(host, port, username, password);

        boolean downloadResult;
        String outputName;
        for (String url:urls.keySet()) {
            if(isSftp(url)) {
                outputName = savedPath + fileNames.get(url);
                downloadResult = sftpLoader.download(url, outputName);
                urls.put(url, downloadResult);
            }else{
                DownloadReporter.reportFailedDownload(url, "Unsupported protocol");
            }
        }
        downloadMap = fileManager.getDownloadStatusMap();
    }

    public int[] generateDownloadStat(){
        int[] report = new int[2];

        for(String url:downloadMap.keySet()){
            if(downloadMap.get(url)){
                //success downloads
                report[0]++;
            }else{
                //failed downloads
                report[1]++;
            }
        }
        return report;
    }

    private boolean isCommonProtocol(String url){
        String protocol = url.substring(0, url.indexOf(":"));
        if(supportedProtocol.contains(protocol.toUpperCase()) && !Protocol.SFTP.name().equalsIgnoreCase(protocol)){
            return true;
        }
        return false;
    }

    private boolean isSftp(String url){
        String protocol = url.substring(0, url.indexOf(":"));
        if(Protocol.SFTP.name().equalsIgnoreCase(protocol)){
            return true;
        }
        return false;
    }

    public String getSavedPath(){
        return savedPath;
    }

    public List<String> getSupportedProtocol(){
        return supportedProtocol;
    }
}
