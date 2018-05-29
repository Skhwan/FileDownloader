package com.downloader.unitTest;

import com.downloader.loader.SftpLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by khwanchanok on 5/29/2018 AD.
 */
public class SftpLoaderTest {

    SftpLoader sftpLoader;

    @Before
    public void setUp(){
        sftpLoader = new SftpLoader();
    }

    @Test
    public void downloadSuccess(){
        String host = "test.rebex.net";
        int port = 22;
        String username = "demo";
        String password = "password";
        sftpLoader.configDownload(host, port, username, password);
        String url = "sftp://test.rebex.net/readme.txt";
        String outputName = "src/test/resources/output/readme.txt";

        boolean downloadStatus = sftpLoader.download(url, outputName);

        Assert.assertTrue(downloadStatus);

        File file = new File(outputName);
        file.delete();
    }

    @Test
    public void downloadFail(){
        String host = "test.rebex.net";
        int port = 22;
        String username = "aaa";
        String password = "aaa";
        sftpLoader.configDownload(host, port, username, password);
        String url = "sftp://test.rebex.net/readme.txt";
        String outputName = "src/test/resources/output/readme.txt";

        boolean downloadStatus = sftpLoader.download(url, outputName);

        Assert.assertFalse(downloadStatus);
    }

    @Test
    public void getSourceFilePathCorrect(){
        String url = "http://test/path/test.txt";

        String sourceFilePath = sftpLoader.getSourceFilePath(url);

        Assert.assertEquals("/path/test.txt", sourceFilePath);
    }
}
