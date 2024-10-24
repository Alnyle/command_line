package org.example;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFile {

    private final LSDF lsdf;

    public CreateFile() {
        this.lsdf = new LSDF();
    }

    public boolean touch(String directory) throws IOException {


        String path = directory;
        Path filePath = Paths.get(directory);

        if (!filePath.isAbsolute()) {
            path = lsdf.pathHandler(directory, true);
            filePath =  Paths.get(path);
        }

        if (Files.exists(filePath)) {
            if (Files.isRegularFile(filePath)) {
                System.out.println(STR."File already exists: \{filePath}");
                return false;
            }
        }


//        Path path1 = Paths.get(directory);
//        Path path2 = Paths.get(path);
//
//        // Get the common part of the paths
//        Path relativePath = path1.relativize(path1.resolve(path2)).normalize();
//
//        Path mergedPath = path1.resolveSibling(relativePath);
//
//        System.out.println(String.valueOf(mergedPath) + "problem here");
//
        File file = new File(String.valueOf(filePath));

        if (file.createNewFile()) {
            System.out.println("File created");
            return true;
        } else {
            System.out.println("already exist");
            return false;
        }

//        try {
//            Files.createFile(filePath);
//            return true;
//        } catch (IOException e) {
//            return false;
//        }

//        return true;
    }


}
