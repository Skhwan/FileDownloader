package com.downloader.integrationTest;

import com.downloader.controller.LoaderController;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khwanchanok on 5/25/2018 AD.
 */
public class CommonDownloadTest {

    private LoaderController loaderController;
    private String sourceFile;
    private String savedPath;
    private List<String> supportedProtocol;

    @Before
    public void setUp(){
        loaderController = new LoaderController();
        savedPath = "src/test/resources/output/";
        supportedProtocol = new ArrayList<>();
        supportedProtocol.add("HTTP");
        supportedProtocol.add("HTTPS");
        supportedProtocol.add("FTP");
    }

    @Test
    public void downloadHttpFileSuccess() throws Exception {

        sourceFile = "src/test/resources/input/http_source.txt";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);

        loaderController.download(sourceFile);

        File file = new File(savedPath + "Antibiotics%20sample%20data.zip");
        boolean fileExists = file.exists();
        Assert.assertTrue(fileExists);

    }

    @Test
    public void downloadFtpFileSuccess() throws Exception {

        sourceFile = "src/test/resources/input/ftp_source.txt";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);

        loaderController.download(sourceFile);

        File file = new File(savedPath + "README_data_has_moved.md");
        boolean fileExists = file.exists();
        Assert.assertTrue(fileExists);

    }

    @Test
    public void downloadWithExistingFileNameShouldBeCorrect(){
        sourceFile = "src/test/resources/input/http_source.txt";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);

        loaderController.download(sourceFile);

        File file = new File(savedPath + "Antibiotics%20sample%20data.zip");
        boolean fileExists = file.exists();
        Assert.assertTrue(fileExists);

        setUp();
        sourceFile = "src/test/resources/input/http_source.txt";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);
        loaderController.download(sourceFile);

        file = new File(savedPath + "Antibiotics%20sample%20data1.zip");
        fileExists = file.exists();
        Assert.assertTrue(fileExists);
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
    }

    @After
    public void cleanUpOutputFolder() throws IOException {
        File file = new File(savedPath);
        FileUtils.cleanDirectory(file);
    }

}
