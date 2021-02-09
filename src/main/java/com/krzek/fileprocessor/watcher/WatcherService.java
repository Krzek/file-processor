package com.krzek.fileprocessor.watcher;

import com.krzek.fileprocessor.helper.FileHelper;
import com.krzek.fileprocessor.services.InputService;
import com.krzek.fileprocessor.services.OutputService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

@Component
@Data
public class WatcherService implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatcherService.class);

    private WatchService watchService;

    @Value("${app.in}")
    private String in;
    @Value("${app.out}")
    private String out;
    @Value("#{'${file.extensions.in}'.split(',')}")
    private List<String> extensions;

    @Autowired
    private InputService inputService;

    @Autowired
    private OutputService outPutService;

    public WatcherService() throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
    }

    @Override
    public void run() {
        setup();
        for (; ; ) {
            LOGGER.info("Running ...");
            try {
                executeExistsFiles();
                Watching();
            } catch (InterruptedException | IOException e) {
                LOGGER.error("Error WatcherService.run()");
                throw new RuntimeException(e);
            }
        }
    }

    private void Watching() throws InterruptedException, IOException {
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                processEvent(event);
            }
            key.reset();
            report();
        }

    }

    private void processEvent(WatchEvent<?> event) throws IOException {
        Object context = event.context();
        process(context.toString());
    }

    private void process(String context) throws IOException {
        if (isValidExtension(context)) {
            LOGGER.info("File: " + context);
            Stream<String> file = FileHelper.readFile(in, context);
            file.forEach(x -> inputService.process(x));
        } else {
            LOGGER.info("Ignored File: " + context);
        }
    }

    private boolean isValidExtension(Object path) {
        return extensions.stream().filter(x -> path.toString().endsWith(x)).findAny().orElse(null) != null;
    }

    private void setup() {
        File dir = FileHelper.makeDirectory(in);
        FileHelper.makeDirectory(out);
        try {
            watchService = FileSystems.getDefault().newWatchService();
            register(watchService, dir);
        } catch (IOException e) {
            LOGGER.error("Setup problems");
        }
    }

    private void register(WatchService watcher, File dir) throws IOException {
        Path path = Paths.get(dir.getAbsolutePath());
        path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

    }

    private void executeExistsFiles() throws IOException {
        File file = new File(in);
        String[] files = file.list();
        if (files != null && files.length > 0) {
            for (String s : files) {
                process(s);
            }
        }
        report();
    }

    private void report() {
        outPutService.generateReport();
    }
}
