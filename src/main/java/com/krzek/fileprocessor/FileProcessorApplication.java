package com.krzek.fileprocessor;

import com.krzek.fileprocessor.watcher.WatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileProcessorApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileProcessorApplication.class);

    @Autowired
    public WatcherService watcherService;

    public static void main(String[] args) {
        SpringApplication.run(FileProcessorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("watcherService Started");
        new Thread(watcherService, "watcher-service").start();
    }
}
