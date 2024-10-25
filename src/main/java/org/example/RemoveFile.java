package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RemoveFile {

    private final LSDF lsdf;

    public RemoveFile() {
        this.lsdf = new LSDF();
    }

    public boolean rm(String path) throws IOException {



        Path absolutePath = Paths.get(path);
        if (!absolutePath.isAbsolute()) {
            path = lsdf.pathHandler(path, true);
            absolutePath =  Paths.get(path);
        }

        if (Files.exists(absolutePath)) {
            if (Files.isRegularFile(absolutePath)) {
                Files.delete(absolutePath);
                return true;
            } else {
                System.out.println("This a directory not a file");
                return false;
            }
        } else {
            System.out.println("File does exist");
            return false;
        }


    }
}
