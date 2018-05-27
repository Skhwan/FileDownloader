package com.downloader.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
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

    public Map<String, String> read(String sourceFile) throws IOException {

        urls = new HashMap<>();
        fileNames = new HashMap<>();

        FileReader fr = new FileReader(sourceFile);
        BufferedReader br = new BufferedReader(fr);

        String url;
        String fileName;

        while ((url = br.readLine()) != null) {
            fileName = generateFileName(url);
            fileNames.put(url, fileName);
            urls.put(url, false);
        }

        br.close();
        fr.close();

        return fileNames;
    }

    public String generateFileName(String url){
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public Map<String, Boolean> getDownloadStatusMap(){
        return urls;
    }

}
