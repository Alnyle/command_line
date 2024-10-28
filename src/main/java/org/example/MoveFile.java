package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MoveFile {
    private final LSDF lsdf;

    private final PrintCurrentDirectory PWD;

    public MoveFile(LSDF lsdf, PrintCurrentDirectory pwd) {
        this.lsdf = lsdf;
        PWD = pwd;
    }

    boolean mv(String from_Path, String to_Path) {


        Path absolutePath1 = Paths.get(from_Path);
        if (!absolutePath1.isAbsolute()) {
            from_Path = lsdf.pathHandler(from_Path, true);

            if (from_Path.equals("Invalid path")) {
                return false;
            }

            absolutePath1 =  Paths.get(from_Path);
        }

        // check if the directory or file is exist
        if (Files.exists(absolutePath1)) {

            // check if the second path absolute path (full path)
            Path absolutePath2 = Paths.get(to_Path);
            if (!absolutePath2.isAbsolute()) {
                to_Path = lsdf.pathHandler(to_Path, true);
                if (to_Path.equals("Invalid path")) {
                    return false;
                }

                absolutePath2 =  Paths.get(to_Path);
            }

            // check if both path for directories
            boolean areDirectories = (Files.isDirectory(absolutePath1) && Files.isDirectory(absolutePath2));

            // check if both path for directories
            boolean areFiles = (Files.isRegularFile(absolutePath1) && Files.isRegularFile(absolutePath2));

            // check if the first one path for file and second for directory
            boolean isFileDir = (Files.isRegularFile(absolutePath1) && Files.isDirectory(absolutePath2));

            System.out.println(absolutePath1);
            System.out.println(absolutePath2);

            System.out.println(areFiles);
            System.out.println(areDirectories);
            System.out.println(isFileDir);
            if (areDirectories || areFiles || isFileDir) {
                // check if both path for directories or files
                try {
                    Files.move(absolutePath1, absolutePath2);
                } catch (IOException e) {
                    System.out.println(STR."Move filed\{e.getMessage()}");
                    return false;
                }
            } else {
                System.out.println("can not move a directory into a file");
                return false;
            }

            return false;

        } else {
            System.out.println(STR."Invalid path \{absolutePath1}");
            return false;
        }

    }
}
