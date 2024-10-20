package org.example;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.FilenameFilter;




public class LSDF {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";

    char unix = '\\';

    public void ls(String directory) {

        // get the path of the current directory
        String path = System.getProperty("user.dir");

        // list of directories to current path
        String[] directories = path.split("\\\\");

        // getRoot() => change here


        String newCurrentPath = null;

        // Use StringBuilder store path of new path
        StringBuilder newPath = new StringBuilder();

        if (directory.equals("..") || directory.equals("../")) {

            //remove last directory to change
            for (int i = 0; i < directories.length - 1; i++) {
                newPath.append(directories[i]).append("\\");
            }
            newCurrentPath = newPath.toString();
            printAllDirectories(newCurrentPath);

        } else if (directory.startsWith("../")) {


            String[] newDirectory = directory.split("/");

            if (newDirectory.length > 0) {

                // count number of times have to back
                int backwords = 0;

                int i = 0;

                while (true && i < newDirectory.length) {
                    if (newDirectory[i].equals("..")) {
                        backwords++;
                        i++;
                    } else {
                        break;
                    }
                }

                // subtract from old path directories based on number of `..`
                for (int p = 0; p < directories.length - backwords; p++) {
                    newPath.append(directories[p]).append("\\");
                }

                // then add the new path
                for (int p = i; p < newDirectory.length; p++) {
                    newPath.append(newDirectory[p]).append("\\");
                }


                newCurrentPath = newPath.toString();
                printAllDirectories(newCurrentPath);
            }


        } else if (directory.startsWith("./")) {

            String[] newDirectory = directory.split("/");

            // start new path after './'
            for (int p = 1; p < newDirectory.length; p++) {
                newPath.append(newDirectory[p]).append("\\");
            }
            newCurrentPath = addNewPath(newPath.toString());
            printAllDirectories(newCurrentPath);

        } else if (!directory.startsWith("./") && !directory.isEmpty()) {

            String[] newDirectory = directory.split("/");

            if (newDirectory.length == 1) {
                newPath.append(newDirectory[0]).append("\\");
                newCurrentPath = addNewPath(newPath.toString());
                printAllDirectories(newCurrentPath);
            } else {
                System.out.println("Invalid Path");
            }

        } else if (directory.isEmpty()) {
            newCurrentPath = addNewPath(path);
            printAllDirectories(path);
        }

    }


    public void lsHandler(String directory) {
        // get list from here from ls
    }

    public void lsHandler(String directory, char op) {
        // get list from here ls
    }

    public void lsHandler(String directory, char op1, char op2) {
        // get list from here ls
    }


    private File[] getAllFileAndDirectories(String path) {

        // Creating A file object for directory
        File directoryPath = new File(path);
        return directoryPath.listFiles(folder -> folder.isDirectory() && !folder.getName().startsWith("."));
    }

    private boolean checkPath(String path) {
        return Files.exists(Path.of(path));
    }

    // print directories name in color on console blue
    private void printAllDirectories(String path) {

        boolean isValidPath = checkPath(path);
        if (isValidPath) {

            File[] directoryPath = getAllFileAndDirectories(path);
            printFolders(directoryPath);
            System.out.println();
            return;
        }

        System.out.println("Path Invalid");
    }

    private void printFolders(File[] Folders) {
        for (File folder : Folders) {
            System.out.print(STR."\{ANSI_BLUE + folder.getName() + ANSI_RESET}/     ");
        }
    }

    // use when you want to add new path to current path like => ./path
    private String addNewPath(String directory_name) {

        boolean isDirectoryChanged = false;

        File newDirectory = new File(directory_name).getAbsoluteFile();

        // the new path to the current path
        return System.setProperty("user.dir", newDirectory.getAbsolutePath()) + unix  + directory_name;
    }


//    // Filter hidden Folders based on `.`  using filter function
//    FilenameFilter hiddenFilesFilter = new FilenameFilter() {
//        @Override
//        public boolean accept(File dir, String name) {
//            return name.startsWith(".");
//        }
//    };
//    private String[] getAllFileAndDirectories(String path) {
//
//        // Creating A file object for directory
//        File directoryPath = new File(path);
//
//        // get the all directories and files in current path
//
//        return directoryPath.list();
//    }
}
