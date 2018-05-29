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
 * Created by khwanchanok on 5/29/2018 AD.
 */
public class SecureDownloadTest {

    private LoaderController loaderController;
    private String sourceFile;
    private String savedPath;
    private List<String> supportedProtocol;

    @Before
    public void setUp(){
        loaderController = new LoaderController();
        savedPath = "src/test/resources/output/";
        sourceFile = "src/test/resources/input/sftp_source.txt";
        supportedProtocol = new ArrayList<>();
        supportedProtocol.add("SFTP");
    }

    @Test
    public void downloadSftpFileSuccess() throws Exception {

        String host = "test.rebex.net";
        int port = 22;
        String username = "demo";
        String password = "password";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);

        loaderController.secureDownload(sourceFile, host, port, username, password);

        File file = new File(savedPath + "readme.txt");
        boolean fileExists = file.exists();
        Assert.assertTrue(fileExists);

    }

    @Test
    public void downloadFailWithUnknownHost(){
        String host = "aaa.aaa.aaa";
        int port = 22;
        String username = "demo";
        String password = "password";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);

        loaderController.secureDownload(sourceFile, host, port, username, password);

        File file = new File(savedPath + "readme.txt");
        boolean fileExists = file.exists();
        Assert.assertFalse(fileExists);
    }

    @Test
    public void downloadFailWithIncorrectAuthentication(){
        String host = "test.rebex.net";
        int port = 22;
        String username = "aaa";
        String password = "";
        loaderController.setSavedPath(savedPath);
        loaderController.setSupportedProtocol(supportedProtocol);

        loaderController.secureDownload(sourceFile, host, port, username, password);

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