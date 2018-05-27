package com.downloader.integrationTest;

import com.downloader.loader.CommonLoader;
import org.junit.Test;

/**
 * Created by khwanchanok on 5/25/2018 AD.
 */
public class LoaderTest {

    @Test
    public void downloadHttpFileSuccess() throws Exception {
        CommonLoader httpCommonLoader = new CommonLoader();
        String targetUrl = "https://download.applied-maths.com/sites/default/files/download/Antibiotics%20sample%20data.zip";
        String savedPath = "./output.zip";
        httpCommonLoader.download(targetUrl, savedPath);
    }

    @Test
    public void downloadFtpFileSuccess() throws Exception {
        CommonLoader ftpCommonLoader = new CommonLoader();
        String targetUrl = "ftp://ftp.1000genomes.ebi.ac.uk/vol1/ftp/data/README_data_has_moved.md";
        String savedPath = "./output.md";
        ftpCommonLoader.download(targetUrl, savedPath);
    }

    @Test
    public void downloadSftpFileSuccess() throws Exception {

    }

}
