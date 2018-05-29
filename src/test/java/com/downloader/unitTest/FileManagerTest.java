package com.downloader.unitTest;

import com.downloader.util.FileManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by khwanchanok on 5/29/2018 AD.
 */
public class FileManagerTest {

    FileManager fileManager;

    @Before
    public void setUp(){
        fileManager = new FileManager();
    }

    @Test
    public void generateFileNameCorrectly(){
        String url = "http://test/test.txt";
        String savedPath = "src/test/resources/output/";
        String fileName = fileManager.generateFileName(url, savedPath);

        String expectedFileName = "test.txt";
        Assert.assertEquals(expectedFileName, fileName);
    }

    @Test
    public void generateFileNameWithSequenceNumberCorrectly() throws IOException {
        String url = "http://test/test.txt";
        String savedPath = "src/test/resources/output/";
        File file = new File(savedPath + "test.txt");
        FileOutputStream fis = new FileOutputStream(file);
        fis.write(1024);

        String fileName = fileManager.generateFileName(url, savedPath);

        String expectedFileName = "test1.txt";
        Assert.assertEquals(expectedFileName, fileName);

        file.delete();
        fis.close();
    }

    @Test
    public void prepareFileSuccess(){
        String sourceFile = "src/test/resources/input/http_source.txt";
        String savedPath = "src/test/resources/output/";

        fileManager.prepareFiles(sourceFile, savedPath);

        Map<String, String> filenames = fileManager.getDownloadNames();
        Map<String, Boolean> downloadStatus = fileManager.getDownloadStatusMap();

        Assert.assertEquals(1, filenames.size());
        Assert.assertEquals(1, downloadStatus.size());

        String key = "https://download.applied-maths.com/sites/default/files/download/Antibiotics%20sample%20data.zip";
        Boolean status = downloadStatus.get(key);
        String filename = filenames.get(key);

        Assert.assertEquals(false, status);
        Assert.assertEquals("Antibiotics%20sample%20data.zip", filename);
    }

}
