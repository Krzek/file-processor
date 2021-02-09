package com.krzek.fileprocessor.helper;

import com.krzek.fileprocessor.enums.TypeEnum;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileHelper {
    public static File makeDirectory(String path) {
        File file = new File(path);
        file.mkdirs();
        return file;
    }

    public static boolean isValidLine(String linha, String separator) {
        if (linha == null || linha.trim().isEmpty()) {
            return false;
        }
        String[] split = linha.split(separator);

        if (split.length != 4) {
            return false;
        }

        return TypeEnum.isValid(split[0]);
    }

    public static Stream<String> readFile(String in, String file) throws IOException {
        String pathFile = in.concat("/").concat(file);
        Path path = Paths.get(pathFile);
        return Files.lines(path, StandardCharsets.UTF_8).filter(l -> !l.isEmpty());
    }

    public static void write(String pathOut, String name, String file) throws IOException {
        if (file.length() > 0) {
            makeDirectory(pathOut);
            String pathFile = pathOut.concat("/").concat(name);
            Path path = Paths.get(pathFile);
            Files.write(path, file.getBytes());
        }
    }
}
