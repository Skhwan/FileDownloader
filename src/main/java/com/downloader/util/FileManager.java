package com.downloader.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by khwanchanok on 5/27/2018 AD.
 */
@Component
public class FileManager {

    Logger logger = LogManager.getLogger(FileManager.class);

    private Map<String, Boolean> urls;
    private Map<String, String> fileNames;

    public void prepareFiles(String sourceFile, String savedPath) {

        urls = new HashMap<>();
        fileNames = new HashMap<>();

        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(sourceFile);
            br = new BufferedReader(fr);
            String url;
            String fileName;

            while ((url = br.readLine()) != null) {
                fileName = generateFileName(url, savedPath);
                fileNames.put(url, fileName);
                urls.put(url, false);
            }
        }catch (Exception e){
            DownloadReporter.reportException(e.getMessage());
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            }catch (IOException e){
                logger.error("Got error while closing reader: {}", e.getMessage());
            }
        }
    }

    public String generateFileName(String url, String savedPath){
        String fileName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        String fileType = url.substring(url.lastIndexOf("."));
        String tmpName = fileName.concat(fileType);
        File file = new File(savedPath, tmpName);
        int sequence = 0;
        while(file.exists()){
            sequence++;
            tmpName = fileName.concat(Integer.toString(sequence)).concat(fileType);
            file = new File(savedPath, tmpName);
        }
        if(sequence>0) {
            fileName = fileName.concat(Integer.toString(sequence));
        }
        return fileName.concat(fileType);
    }

    public Map<String, Boolean> getDownloadStatusMap(){
        return urls;
    }

    public Map<String, String> getDownloadNames(){
        return fileNames;
    }

}
