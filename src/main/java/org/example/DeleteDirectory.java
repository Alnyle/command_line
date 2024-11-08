package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteDirectory {

    private final LSDF lsdf;

    public DeleteDirectory(LSDF lsdf) {
        this.lsdf = lsdf;
    }


    public boolean rmdir(String directory) {


        String path = directory;



        Path folderPath = Paths.get(directory);
        if (!folderPath.isAbsolute()) {
            path = lsdf.pathHandler(directory, true);
            if (path.equals("Invalid path")) {
                System.out.println("Invalid path");
                return false;
            }
            folderPath =  Paths.get(path);
        }


        if (!Files.exists(folderPath)) {
            if (Files.isDirectory(folderPath)) {
                System.out.println(STR."Directory does not exists: \{folderPath}");
                return false;
            }
        }

        boolean isDelete = deleteDirectory(folderPath.toFile());


        if (!isDelete) {
            System.out.println("Invalid path");
        }
        return isDelete;
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

}
