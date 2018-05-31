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
 * Created by khwanchanok on 5/29/2018 AD.
 */
public class SecureDownloadTest {

    private LoaderController loaderController;
    private String sourceFile;
    private String savedPath;
    private Map<String, String> supportedProtocol;

    @Before
    public void setUp(){
        loaderController = new LoaderController();
        savedPath = "src/test/resources/output/";
        sourceFile = "src/test/resources/input/sftp_source.txt";
        supportedProtocol = new HashMap<>();
        supportedProtocol.put(Protocol.SFTP.name(), Protocol.SFTP.name());
    }

    @Test
    public void downloadSftpFileSuccess() {

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

        file.delete();

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

        file.delete();
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

        file.delete();
    }

}
