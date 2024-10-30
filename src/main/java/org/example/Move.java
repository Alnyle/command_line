package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Move {
    private final LSDF lsdf;


    public Move(LSDF lsdf) {
        this.lsdf = lsdf;
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
                // not absolute path handle path and make absolute
                to_Path = lsdf.pathHandler(to_Path, true);
                if (to_Path.equals("Invalid path")) {
                    return false;
                }

                absolutePath2 =  Paths.get(to_Path);
            }

            try {
                Files.move(absolutePath1, absolutePath2);
                return true;
            } catch (IOException e) {
                    System.out.println(STR."can not move \{e.getMessage()}");
                    return false;
            }

        } else {
            System.out.println(STR."Invalid path \{absolutePath1}");
            return false;
        }

    }
}
