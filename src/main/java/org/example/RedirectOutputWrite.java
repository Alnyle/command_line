package org.example;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;

// > Operator
public class RedirectOutputWrite {


    private final LSDF lsdf;

    private final PrintCurrentDirectory PWD;

    public RedirectOutputWrite(LSDF lsdf, PrintCurrentDirectory pwd) {
        this.lsdf = lsdf;
        PWD = pwd;
    }


    // special for pwd
    boolean redirectOutPut(String path, String op) throws IOException {


        Path absolutePath = Paths.get(path);
        if (!absolutePath.isAbsolute()) {
            path = lsdf.pathHandler(path, true);

            if (path.equals("Invalid path")) {
                return false;
            }

            absolutePath =  Paths.get(path);
        }

        // Check if the path is a directory
        if (Files.isDirectory(absolutePath)) {
            System.out.println("Invalid path: this is a directory path");
            return false;
        }

        String fileContent = PWD.pwd();
        if (Files.exists(absolutePath)) {

            if (Files.isRegularFile(absolutePath)) {


                // if op == '>' override file content
                if (op.equals(">")) {
                    return overrideFile(absolutePath, fileContent);
                } else {
                    File file = new File(String.valueOf(absolutePath));
                    return appendFile(file, fileContent);
                }
            }
        } else {

            File file = new File(String.valueOf(absolutePath));


            if (file.createNewFile()) {
                if (op.equals(">")) {
                    return overrideFile(absolutePath, fileContent);
                } else {
                   return appendFile(file, fileContent);
                }
            }

        }
        return true;

    }


    //
    boolean listRedirectOutPut(String fromPath,String TargetPath, String mode, boolean reverse, boolean allFiles) throws IOException {

        ArrayList<File> files =  getFiles(fromPath, allFiles);


        if (reverse && !files.isEmpty()) {
            Collections.reverse(files);
        }

        Path absolutePath = Paths.get(TargetPath);
        if (!absolutePath.isAbsolute()) {
            TargetPath = lsdf.pathHandler(TargetPath, true);
            if (TargetPath.equals("Invalid path")) {
                return false;
            }

            absolutePath =  Paths.get(TargetPath);
        }

//        System.out.println(String.valueOf(absolutePath));


        // Check if the path is a directory
        if (Files.isDirectory(absolutePath)) {
            System.out.println("Invalid path: this is a directory path");
            return false;
        }


        // if op == '>' override file content

        if (Files.exists(absolutePath)) {

            if (Files.isRegularFile(absolutePath)) {


                // if op == '>' override file content
                if (mode.equals(">")) {
                    return overrideFile(String.valueOf(absolutePath), files);

                } else {
                    File file = new File(String.valueOf(absolutePath));
                    return appendFile(file, files);
                }
            }
        } else {

            File file = new File(String.valueOf(absolutePath));
            if (file.createNewFile()) {
                if (mode.equals(">")) {
                    return overrideFile(String.valueOf(absolutePath), files);
                } else {
                    return appendFile(file, files);
                }
            }

        }
        return true;
    }


    // handle "cat file_Path > another_FileP_ath"
    public boolean catRedirect(String fromPath,String TargetPath, String mode) throws IOException {

        // check if the first file path absolute path (full path)
        Path absolutePath = Paths.get(fromPath);
        if (!absolutePath.isAbsolute()) {
            fromPath = lsdf.pathHandler(fromPath, true);
            absolutePath =  Paths.get(fromPath);
        }

        // check if the first file is exist before read from it
        if (Files.exists(absolutePath)) {
            if (Files.isRegularFile(absolutePath)) {

                // check if the second path absolute path (full path)
                Path absolutePath2 = Paths.get(TargetPath);
                if (!absolutePath2.isAbsolute()) {
                    TargetPath = lsdf.pathHandler(TargetPath, true);
                    if (TargetPath.equals("Invalid path")) {
                        return false;
                    }

                    absolutePath2 =  Paths.get(TargetPath);
                }

                // Check if the path is a directory
                if (Files.isDirectory(absolutePath2)) {
                    System.out.println("Invalid path: this is a directory path");
                    return false;
                }

                if (Files.exists(absolutePath2)) {

                    if (Files.isRegularFile(absolutePath2)) {


                        // if op == '>' override file content
                        if (mode.equals(">")) {
                            return catOverrideFile(absolutePath.toFile(), absolutePath2.toFile());

                        } else {
                            // append to file content
                            File file = new File(String.valueOf(absolutePath2));
                            return catAppendFile(absolutePath.toFile(), absolutePath2.toFile());
                        }
                    }
                } else {

                    File file = new File(String.valueOf(absolutePath2));
                    if (file.createNewFile()) {
                        if (mode.equals(">")) {
                            return catOverrideFile(absolutePath.toFile(), absolutePath2.toFile());
                        } else {
                            return catAppendFile(absolutePath.toFile(), absolutePath2.toFile());
                        }
                    }

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


    boolean catOverrideFile(File a, File b) throws IOException {

        FileInputStream in = new FileInputStream(a);
        FileOutputStream out = new FileOutputStream(b);

        try {
            int n;


            while ((n = in.read()) != -1) {

                out.write(n);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {


                in.close();
            }

            if (out != null) {
                out.close();
            }
        }

        return true;
    }


    private boolean catAppendFile(File a, File b) throws IOException {

        FileInputStream in = new FileInputStream(a);
        FileWriter out = new FileWriter(b, true);
        try {
            int n;

            while ((n = in.read()) != -1) {

                out.write(n);
            }
        } catch (IOException e) {
            System.out.println("unable to write to file");
            return false;
        } finally {
            if (in != null) {

                in.close();
            }
        }

        return true;
    }

    private boolean overrideFile(String file,ArrayList<File> fileContent) {


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
        {
            // print file then start new line
            for (File folder : fileContent) {
                writer.write(folder.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("unable to write to file");
            return false;
        }
        return true;
    }


    private boolean appendFile(File file,ArrayList<File> fileContent) throws IOException {

        FileWriter fr = new FileWriter(file, true);

        try {
            for (File folder : fileContent) {
                fr.write(folder.getName());
                fr.write("\n");
            }
            fr.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    private boolean overrideFile(Path file, String fileContent) {

        try {
            Files.writeString(file , fileContent);
            return true;
        } catch (IOException e) {
            System.out.println("Unable to write to file");
            return false;
        }

    }

    private boolean appendFile(File file, String fileContent) {

        try {

            FileWriter fr = new FileWriter(file, true);
            fr.write(fileContent);
            fr.close();
            return true;

        } catch (IOException e) {
            System.out.println("Unable to write to file");
            return false;
        }

    }

    public ArrayList<File> getFiles(String directory, boolean allFiles) {


        // get the path of the directory
        String path = lsdf.pathHandler(directory);


        // check if the path is valid or not
        boolean isValidPath = lsdf.checkPath(path);
        if (isValidPath) {
            ArrayList<File> folders = lsdf.getAllFileAndDirectories(path, allFiles);
            if (folders != null || !folders.isEmpty()) {
                return folders;
            } else {
                return new ArrayList<>();
            }
        } else {
            return null;
        }
    }



}
