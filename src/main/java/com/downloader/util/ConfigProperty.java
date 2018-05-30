package com.downloader.util;

/**
 * Created by khwanchanok on 5/27/2018 AD.
 */
public final class ConfigProperty {

    public static final String MANUAL_PROMPT = "${app.prompt.manual}";
    public static final String PROTOCOL_PROMPT = "${app.prompt.protocol}";
    public static final String SUPPORTED_PROTOCOL = "#{${app.supportedProtocol}}";
    public static final String SOURCE_FILE_PROMPT = "${app.prompt.sourceFile}";
    public static final String HOST_PROMPT = "${app.prompt.secure.host}";
    public static final String PORT_PROMPT = "${app.prompt.secure.port}";
    public static final String USERNAME_PROMPT = "${app.prompt.secure.username}";
    public static final String PASSWORD_PROMPT = "${app.prompt.secure.password}";
    public static final String FOOTER = "${app.prompt.footer}";
    public static final String SAVED_PATH = "${app.savedPath}";

}
