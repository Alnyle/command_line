package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateDirectory {

    private final LSDF lsdf;

    public CreateDirectory(LSDF lsdf) {
        this.lsdf = lsdf;
    }


    public boolean mkdir(String directory) {

        String path = directory;


        Path folderPath = Paths.get(directory);
        if (!folderPath.isAbsolute()) {
            path = lsdf.pathHandler(directory, true);
            folderPath =  Paths.get(path);
        }

        if (Files.exists(folderPath)) {
            if (Files.isDirectory(folderPath)) {
                System.out.println(STR."Directory already exists: \{folderPath}");
                return false;
            }
        }

        try {
            Files.createDirectories(folderPath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }







}
