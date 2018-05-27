package com.downloader.controller;

import com.downloader.util.ConfigProperty;
import com.downloader.util.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

/**
 * Created by khwanchanok on 5/26/2018 AD.
 */
@Component
public class AppController {

    @Autowired
    LoaderController loaderController;
    private Scanner scanner;

    @Value(ConfigProperty.MANUAL_PROMPT) String manualPrompt;
    @Value(ConfigProperty.PROTOCOL_PROMPT) String protocolPrompt;
    @Value(ConfigProperty.SUPPORTED_PROTOCOL) List<String> supportedProtocol;
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
        promptDownloadResult();
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

    private void promptDownloadResult(){
        System.out.print(loaderController.generateDownloadReport());
    }

    private void promptFooter(){
        System.out.println(footer);
    }

    private String getUserProtocol(){
        System.out.print(protocolPrompt);
        String protocol = scanner.next();
        while(!supportedProtocol.contains(protocol.toUpperCase())){
            System.out.print(protocolPrompt);
            protocol = scanner.next();
        }
        return protocol;
    }

}
