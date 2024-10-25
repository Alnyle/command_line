package org.example;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// > Operator
public class RedirectOutputWrite {


    private final LSDF lsdf;

    private final PrintCurrentDirectory PWD;

    public RedirectOutputWrite(LSDF lsdf, PrintCurrentDirectory pwd) {
        this.lsdf = lsdf;
        PWD = pwd;
    }


    // special for pwd
    boolean redirectOutPut(String path, char op) throws IOException {


        Path absolutePath = Paths.get(path);
        if (!absolutePath.isAbsolute()) {
            path = lsdf.pathHandler(path, true);

            if (path.equals("Invalid path")) {
                return false;
            }

            absolutePath =  Paths.get(path);
        }

        // Step 1: Check if the path is a directory
        if (Files.isDirectory(absolutePath)) {
            System.out.println("Invalid path: this is a directory path");
            return false;
        }

        // if op == '>' override file content
        String fileContent = PWD.pwd();
        if (Files.exists(absolutePath)) {

            if (Files.isRegularFile(absolutePath)) {


                // if op == '>' override file content
                if (op == '>') {
                    return overrideFile(absolutePath, fileContent);
                } else {
                    File file = new File(String.valueOf(absolutePath));
                    return appendFile(file, fileContent);
                }
            }
        } else {

            File file = new File(String.valueOf(absolutePath));


            if (file.createNewFile()) {
                if (op == '>') {
                    return overrideFile(absolutePath, fileContent);
                } else {
                   return appendFile(file, fileContent);
                }
            }

        }
        return true;

    }



    boolean overrideFile(Path file, String fileContent) {

        try {
            Files.writeString(file , fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    boolean appendFile(File file, String fileContent) {

        try {

            FileWriter fr = new FileWriter(file, true);
            fr.write(fileContent);
            fr.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}


//            else if (Files.isDirectory(absolutePath)) {
//                System.out.println("Invalid path this a directory path");
//                return false;
//            }