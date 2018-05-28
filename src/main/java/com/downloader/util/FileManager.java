package com.downloader.util;

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

    private Map<String, Boolean> urls;
    private Map<String, String> fileNames;

    public void prepareFiles(String sourceFile, String savedPath) throws IOException {

        urls = new HashMap<>();
        fileNames = new HashMap<>();

        FileReader fr = new FileReader(sourceFile);
        BufferedReader br = new BufferedReader(fr);

        String url;
        String fileName;

        while ((url = br.readLine()) != null) {
            fileName = generateFileName(url, savedPath);
            fileNames.put(url, fileName);
            urls.put(url, false);
        }

        br.close();
        fr.close();
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
