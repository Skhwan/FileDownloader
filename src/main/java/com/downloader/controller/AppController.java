package com.downloader.controller;

import com.downloader.util.ConfigProperty;
import com.downloader.util.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Created by khwanchanok on 5/26/2018 AD.
 */
@Component
public class AppController {

    @Autowired
    LoaderController loaderController;
    Scanner scanner;

    @Value(ConfigProperty.MANUAL_PROMPT) String manualPrompt;
    @Value(ConfigProperty.PROTOCOL_PROMPT) String protocolPrompt;
    @Value(ConfigProperty.SOURCE_FILE_PROMPT) String sourceFilePrompt;
    @Value(ConfigProperty.HOST_PROMPT) String hostPrompt;
    @Value(ConfigProperty.PORT_PROMPT) String portPrompt;
    @Value(ConfigProperty.USERNAME_PROMPT) String usernamePrompt;
    @Value(ConfigProperty.PASSWORD_PROMPT) String passwordPrompt;
    @Value(ConfigProperty.FOOTER) String footer;

    public AppController(){
        scanner = new Scanner(System.in);
    }

    public void doService() throws Exception {
        promptManual();
        promptForInput(getUserProtocol());
        promptFooter();
    }

    private void promptManual(){
        System.out.print(manualPrompt);
        boolean isReturn;
        do{
            isReturn = scanner.hasNextLine();
        }while(!isReturn);
    }

    private void promptForInput(String protocol) throws Exception {
        if(Protocol.SFTP.name().equalsIgnoreCase(protocol)){
            promptForSecureLoader();
        }else{
            promptForCommonLoader();
        }
    }

    private void promptForCommonLoader() throws Exception {
        System.out.print(sourceFilePrompt);
        String sourcePath = scanner.next();

        loaderController.download(sourcePath);
    }

    private void promptForSecureLoader() throws Exception {
        System.out.print(sourceFilePrompt);
        String sourcePath = scanner.next();

        System.out.print(hostPrompt);
        String host = scanner.next();

        System.out.print(portPrompt);
        int port = scanner.nextInt();

        System.out.print(usernamePrompt);
        String username = scanner.next();

        System.out.print(passwordPrompt);
        String password = scanner.next();

        loaderController.secureDownload(sourcePath, host, port, username, password);
    }

    private void promptFooter(){
        int[] stat = loaderController.generateDownloadStat();
        int totalDownloads = stat[0] + stat[1];
        System.out.printf(footer, stat[0], totalDownloads, loaderController.getSavedPath());
    }

    private String getUserProtocol(){
        System.out.print(protocolPrompt);
        String protocol = scanner.next();
        while(loaderController.getSupportedProtocol().get(protocol.toUpperCase()) == null){
            System.out.print(protocolPrompt);
            protocol = scanner.next();
        }
        return protocol;
    }

}
