package com.downloader.unitTest;

import com.downloader.controller.LoaderController;
import com.downloader.util.Protocol;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by khwanchanok on 5/29/2018 AD.
 */
public class LoaderControllerTest {

    LoaderController loaderController;
    Map<String, String> supportedProtocol;
    String savedPath;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loaderController = new LoaderController();
        savedPath = "src/test/resources/output/";
        supportedProtocol = new HashMap<>();
        supportedProtocol.put(Protocol.HTTP.name(), Protocol.HTTP.name());
        supportedProtocol.put(Protocol.HTTPS.name(), Protocol.HTTPS.name());
        supportedProtocol.put(Protocol.FTP.name(), Protocol.FTP.name());
        supportedProtocol.put(Protocol.SFTP.name(), Protocol.SFTP.name());
        loaderController.setSupportedProtocol(supportedProtocol);
        loaderController.setSavedPath(savedPath);

    }

    @Test
    public void downloadSuccess(){
        String sourcePath = "src/test/resources/input/http_source.txt";

        loaderController.download(sourcePath);

        File file = new File(savedPath + "Antibiotics%20sample%20data.zip");
        int[]report = loaderController.generateDownloadStat();
        Assert.assertEquals(1, report[0]);
        Assert.assertEquals(0, report[1]);
        Assert.assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void secureDownloadSuccess(){
        String sourcePath = "src/test/resources/input/sftp_source.txt";
        String host = "test.rebex.net";
        int port = 22;
        String username = "demo";
        String password = "password";

        loaderController.secureDownload(sourcePath, host, port, username, password);

        File file = new File(savedPath + "readme.txt");
        int[]report = loaderController.generateDownloadStat();
        Assert.assertEquals(1, report[0]);
        Assert.assertEquals(0, report[1]);
        Assert.assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void checkIsSftpTrue(){
        String url = "sftp://test/test.txt";
        boolean isSftp = loaderController.isSftp(url);
        Assert.assertTrue(isSftp);
    }

    @Test
    public void checkIsSftpFalse(){
        String url = "http://test/test.txt";
        boolean isSftp = loaderController.isSftp(url);
        Assert.assertFalse(isSftp);
    }

    @Test
    public void checkIsCommonProtocolTrue(){
        String url = "http://test/test.txt";
        boolean isCommonProtocol = loaderController.isCommonProtocol(url);
        Assert.assertTrue(isCommonProtocol);
    }

    @Test
    public void checkIsCommonProtocolFalse(){
        String url = "sftp://test/test.txt";
        boolean isCommonProtocol = loaderController.isCommonProtocol(url);
        Assert.assertFalse(isCommonProtocol);
    }

    @Test
    public void generateReportCorrectly(){
        Map<String, Boolean> downloadMap = new HashMap<>();
        downloadMap.put("a", false);
        downloadMap.put("b", false);
        downloadMap.put("c", true);
        loaderController.setDownloadMap(downloadMap);

        int[] report = loaderController.generateDownloadStat();

        Assert.assertEquals(1, report[0]);
        Assert.assertEquals(2, report[1]);
    }

}
