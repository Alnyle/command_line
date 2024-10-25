package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cat {

    private final LSDF lsdf;

    public Cat(LSDF lsdf) {
        this.lsdf = lsdf;
    }


    public boolean cat(String path) throws IOException {


        Path absolutePath = Paths.get(path);
        if (!absolutePath.isAbsolute()) {
            path = lsdf.pathHandler(path, true);
            absolutePath =  Paths.get(path);
        }

        if (Files.exists(absolutePath)) {
            if (Files.isRegularFile(absolutePath)) {

                // wrap a file in file object so can read it
                File file = new File(String.valueOf(absolutePath));

                //  read file using FileReader class and store file content in BufferReader
                BufferedReader br = new BufferedReader(new FileReader(file));

                String str;
                System.out.println();
                while ((str = br.readLine()) != null) {
                    System.out.println(str);
                }

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
