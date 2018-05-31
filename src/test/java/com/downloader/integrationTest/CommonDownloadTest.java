package com.downloader.integrationTest;

import com.downloader.controller.LoaderController;
import com.downloader.util.Protocol;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by khwanchanok on 5/25/2018 AD.
 */
public class CommonDownloadTest {

    private LoaderController loaderController;
    private String sourceFile;
    private String savedPath;
    private Map<String, String> supportedProtocol;

    @Before
    public void setUp(){
        loaderController = new LoaderController();
        savedPath = "src/test/resources/output/";
        supportedProtocol = new HashMap<>();
        supportedProtocol.put(Protocol.HTTP.name(), Protocol.HTTP.name());
        supportedProtocol.put(Protocol.HTTPS.name(), Protocol.HTTPS.name());
        supportedProtocol.put(Protocol.FTP.name(), Protocol.FTP.name());
    }

    @Test
    public void downloadHttpFileSuccess() {

        sourceFile = "src/test/resources/input/http_source.txt";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);

        loaderController.download(sourceFile);

        File file = new File(savedPath + "Antibiotics%20sample%20data.zip");
        boolean fileExists = file.exists();
        Assert.assertTrue(fileExists);

        file.delete();
    }

    @Test
    public void downloadFtpFileSuccess() {

        sourceFile = "src/test/resources/input/ftp_source.txt";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);

        loaderController.download(sourceFile);

        File file = new File(savedPath + "README_data_has_moved.md");
        boolean fileExists = file.exists();
        Assert.assertTrue(fileExists);

        file.delete();

    }

    @Test
    public void downloadWithExistingFileNameShouldBeCorrect(){
        sourceFile = "src/test/resources/input/http_source.txt";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);

        loaderController.download(sourceFile);

        File file1 = new File(savedPath + "Antibiotics%20sample%20data.zip");
        boolean fileExists = file1.exists();
        Assert.assertTrue(fileExists);

        setUp();
        sourceFile = "src/test/resources/input/http_source.txt";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);
        loaderController.download(sourceFile);

        File file2 = new File(savedPath + "Antibiotics%20sample%20data1.zip");
        fileExists = file2.exists();
        Assert.assertTrue(fileExists);

        file1.delete();
        file2.delete();
    }

    @Test
    public void downloadFailWithUnsupportedProtocol(){
        sourceFile = "src/test/resources/input/unsupported_source.txt";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);

        loaderController.download(sourceFile);

        File file = new File(savedPath + "readme.txt");
        boolean fileExists = file.exists();
        Assert.assertFalse(fileExists);

        file.delete();
    }

    @Test
    public void downloadFailWithNonExistSource(){
        sourceFile = "src/test/resources/input/nonexist_source.txt";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);

        loaderController.download(sourceFile);

        File file = new File(savedPath + "readme.txt");
        boolean fileExists = file.exists();
        Assert.assertFalse(fileExists);

        file.delete();
    }

}
