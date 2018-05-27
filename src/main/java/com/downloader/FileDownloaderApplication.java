package com.downloader;

import com.downloader.controller.AppController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileDownloaderApplication implements CommandLineRunner {

	@Autowired
	AppController appController;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FileDownloaderApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		appController.doService();
	}
}
