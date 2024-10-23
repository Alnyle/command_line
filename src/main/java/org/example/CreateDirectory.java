package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateDirectory {

    LSDF lsdf;

    public CreateDirectory() {
        lsdf = new LSDF();
    }

    public boolean mkdir(String directory) {


        String path = lsdf.pathHandler(directory, true);

        Path folderPath = Paths.get(path);

        try {
            Files.createDirectories(folderPath);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

}
