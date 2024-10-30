package org.example;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFile {

    private final LSDF lsdf;

    public CreateFile(LSDF lsdf) {
        this.lsdf = lsdf;
    }


    public boolean touch(String directory) throws IOException {


        String path = directory;
        Path filePath = Paths.get(directory);

        if (!filePath.isAbsolute()) {
            path = lsdf.pathHandler(directory, true);
            if (path.equals("Invalid path")) {
                System.out.println("Invalid path");
                return false;
            }
            filePath =  Paths.get(path);
        }

        if (Files.exists(filePath)) {
            if (Files.isRegularFile(filePath)) {
                System.out.println(STR."File already exists: \{filePath}");
                return true;
            }
        }


        File file = new File(String.valueOf(filePath));

        if (file.createNewFile()) {
            System.out.println("File created");
            return true;
        } else {
            System.out.println("already exist");
            return false;
        }

    }

}
