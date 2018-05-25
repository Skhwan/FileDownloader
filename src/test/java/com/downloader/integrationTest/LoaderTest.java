package com.downloader.integrationTest;

import com.downloader.loader.Loader;
import org.junit.Test;

/**
 * Created by khwanchanok on 5/25/2018 AD.
 */
public class LoaderTest {

    @Test
    public void downloadHttpFileSuccess() throws Exception{
        Loader httpLoader = new Loader();
        String targetUrl = "https://download.applied-maths.com/sites/default/files/download/Antibiotics%20sample%20data.zip";
        String savePath = "/output.zip";
        httpLoader.download(targetUrl, savePath);
    }

    @Test
    public void downloadFtpFileSuccess() throws Exception {
        Loader ftpLoader = new Loader();
        String targetUrl = "ftp://ftp.1000genomes.ebi.ac.uk/vol1/ftp/data/README_data_has_moved.md";
        String savePath = "/output.md";
        ftpLoader.download(targetUrl, savePath);
    }

}
