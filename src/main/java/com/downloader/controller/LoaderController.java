package com.downloader.controller;

import com.downloader.loader.CommonLoader;
import com.downloader.loader.SftpLoader;
import com.downloader.util.ConfigProperty;
import com.downloader.util.DownloadReporter;
import com.downloader.util.FileManager;
import com.downloader.util.Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by khwanchanok on 5/27/2018 AD.
 */
@Component
public class LoaderController {

    @Value(ConfigProperty.SAVED_PATH)
    private String savedPath;

    @Value(ConfigProperty.SUPPORTED_PROTOCOL)
    private List<String> supportedProtocol;

    private FileManager fileManager;
    private ExecutorService executorService;
    private Map<String, Boolean> downloadMap;
    private CommonLoader commonLoader;
    private SftpLoader sftpLoader;

    public LoaderController(){
        fileManager = new FileManager();
        executorService = Executors.newFixedThreadPool(50);
        commonLoader =  new CommonLoader();
        sftpLoader = new SftpLoader();
    }

    public void download(String sourcePath) {
        fileManager.prepareFiles(sourcePath, savedPath);
        Map<String, String> fileNames = fileManager.getDownloadNames();
        Map<String, Boolean> urls = fileManager.getDownloadStatusMap();

        Future<Boolean> downloadResult;
        for (String url:urls.keySet()) {
            if(isCommonProtocol(url)) {
                final String outputName = savedPath + fileNames.get(url);
                downloadResult = executorService.submit(() -> commonLoader.download(url, outputName));
                try {
                    urls.put(url, downloadResult.get());
                } catch (Exception e) {
                    DownloadReporter.reportFailedDownload(url.concat(" ").concat(e.getMessage()));
                }
            }else{
                DownloadReporter.reportFailedDownload(url.concat(" Unsupported protocol"));
            }
        }
        executorService.shutdown();
        downloadMap = fileManager.getDownloadStatusMap();
    }

    public void secureDownload(String sourcePath, String host, int port, String username, String password) {
        fileManager.prepareFiles(sourcePath, savedPath);
        Map<String, String> fileNames = fileManager.getDownloadNames();
        Map<String, Boolean> urls = fileManager.getDownloadStatusMap();

        sftpLoader.configDownload(host, port, username, password);

        Future<Boolean> downloadResult;
        for (String url:urls.keySet()) {
            if(isSftp(url)) {
                String outputName = savedPath + fileNames.get(url);
                downloadResult = executorService.submit(() -> sftpLoader.download(url, outputName));
                try {
                    urls.put(url, downloadResult.get());
                } catch (Exception e) {
                    DownloadReporter.reportFailedDownload(url.concat(" ").concat(e.getMessage()));
                }
            }else{
                DownloadReporter.reportFailedDownload(url.concat(" Unsupported protocol"));
            }
        }
        executorService.shutdown();
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

    public void setSavedPath(String savedPath){
        this.savedPath = savedPath;
    }

    public String getSavedPath(){
        return savedPath;
    }

    public void setSupportedProtocol(List<String> supportedProtocol){
        this.supportedProtocol = supportedProtocol;
    }

    public List<String> getSupportedProtocol(){
        return supportedProtocol;
    }
}
