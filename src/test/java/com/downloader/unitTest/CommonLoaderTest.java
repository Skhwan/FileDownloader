package com.downloader.unitTest;

import com.downloader.loader.CommonLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by khwanchanok on 5/29/2018 AD.
 */
public class CommonLoaderTest {

    CommonLoader commonLoader;

    @Before
    public void setUp(){
        commonLoader = new CommonLoader();
    }

    @Test
    public void downloadSuccess(){
        String url = "https://download.applied-maths.com/sites/default/files/download/mecA%20reference%20sequence.zip";
        String outputName = "src/test/resources/output/mecA%20reference%20sequence.zip";

        boolean downloadStatus = commonLoader.download(url, outputName);

        Assert.assertTrue(downloadStatus);

        File file = new File(outputName);
        file.delete();
    }

    @Test
    public void downloadFail(){
        String url = "http://test/test.txt";
        String outputName = "src/test/resources/output/test.txt";

        boolean downloadStatus = commonLoader.download(url, outputName);

        Assert.assertFalse(downloadStatus);
    }

}
