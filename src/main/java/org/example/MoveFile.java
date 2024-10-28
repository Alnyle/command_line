package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MoveFile {
    private final LSDF lsdf;

    private final PrintCurrentDirectory PWD;

    public MoveFile(LSDF lsdf, PrintCurrentDirectory pwd) {
        this.lsdf = lsdf;
        PWD = pwd;
    }

    boolean mv(String from_Path, String to_Path) throws IOException {


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


            File source = new File(String.valueOf(absolutePath1));
            File destination = new File(String.valueOf(absolutePath2));


                        // check if both path for directories
            boolean areDirectories = (source.isDirectory() && destination.isDirectory());

            // check if both path for directories
            boolean areFiles = (source.isFile() && destination.isFile());

            // check if the first one path for file and second for directory
            boolean isFileDir = (source.isFile() && destination.isDirectory());


            System.out.println(areFiles);
            System.out.println(areDirectories);
            System.out.println(isFileDir);

            if (isFileDir) {
                // Resolve the file path within the destination directory
                Path targetPath = absolutePath2.resolve(absolutePath1.getFileName());

                // Ensure the destination directory exists
                if (!Files.exists(absolutePath2)) {
                    Files.createDirectories(absolutePath2);
                }

                // Move the file into the directory
                Files.move(absolutePath1, targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File moved successfully into the directory.");
            } else {
                try {
                    Files.move(absolutePath1, absolutePath2);
                } catch (IOException e) {
                    System.out.println(STR."Move filed \{e.getMessage()}");
                    return false;
                }
            }





//            if (areDirectories || areFiles || isFileDir) {
//
//            } else {
//                System.out.println("can not move a directory into a file");
//                return false;
//            }

            return false;

        } else {
            System.out.println(STR."Invalid path \{absolutePath1}");
            return false;
        }

    }
}
