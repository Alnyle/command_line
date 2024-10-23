package org.example;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.io.FilenameFilter;




public class LSDF {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";

    char unix = '\\';


    // main ls
    public String pathHandler(String directory) {

        // get the path of the current directory
        String path = Shell.currentPath;

        // list of directories to current path
        String[] directories = path.split("\\\\");

        // getRoot() => change here


        String newCurrentPath = null;

        // Use StringBuilder store path of new path
        StringBuilder newPath = new StringBuilder();

        if (checkPath(directory)) {
            File folder = new File(directory);


            return folder.getAbsolutePath();
        }

        if (directory.equals("..") || directory.equals("../")) {

            //remove last directory to change
            for (int i = 0; i < directories.length - 1; i++) {
                newPath.append(directories[i]).append("\\");
            }
            newCurrentPath = newPath.toString();
            return newCurrentPath;

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
                return newCurrentPath;

            }


        } else if (directory.startsWith("./")) {

            String[] newDirectory = directory.split("/");

            // start new path after './'
            for (int p = 1; p < newDirectory.length; p++) {
                newPath.append(newDirectory[p]).append("\\");
            }
            newCurrentPath = addNewPath(newPath.toString());
            return newCurrentPath;

        } else if (!directory.startsWith("./") && !directory.isEmpty()) {

            String[] newDirectory = directory.split("/");

            if (newDirectory.length == 1) {
                newPath.append(newDirectory[0]).append("\\");
                newCurrentPath = addNewPath(newPath.toString());
                return newCurrentPath;
            } else {
                return "Invalid path";
            }

        } else if (directory.isEmpty()) {
            newCurrentPath = addNewPath("");
            return newCurrentPath;
        }

        return "Invalid Path";
    }

    // ls path
    public ArrayList<File> LS(String directory, boolean allFiles) {


        System.out.println(directory);

        // get the path of the directory
        String path = pathHandler(directory);


//        System.out.println(path);
        // check if the path is valid or not
        boolean isValidPath = checkPath(path);
        if (isValidPath) {
            ArrayList<File> folders = getAllFileAndDirectories(path, allFiles);
            if (folders != null || !folders.isEmpty()) {
                return folders;
            } else {
                return new ArrayList<>();
            }
        } else {
            return null;
        }
    }





    public ArrayList<File> ls(String directory) {

        if (directory.isEmpty()){
            System.out.println("it's empty");
        }

        ArrayList<File> folders = LS(directory, false);
        
        if (folders != null && !folders.isEmpty()) {
            printFolders(folders);
            return folders;
        } else if (folders != null) {
            return new ArrayList<>();
        } else {
            return null;
        }

    }

    public ArrayList<File> ls(String directory, char op) {

        boolean allFiles = op == 'a';

        ArrayList<File> folders = LS(directory, allFiles);



        if (folders != null && !folders.isEmpty()) {

            if (op == 'r') {
                Collections.reverse(folders);
            }

            printFolders(folders);
            return folders;
        } else if (folders != null) {
            return new ArrayList<>();
        } else {
            return null;
        }

    }


    // `ls -a -r path` or `ls -r -a path`
    public ArrayList<File> ls(String directory, char op1, char op2) {
        // get list from here ls
        boolean allFiles = (op1 == 'a' || op2 == 'a') ;

        ArrayList<File> folders = LS(directory, true);


        if (folders != null && !folders.isEmpty()) {

            Collections.reverse(folders);

            printFolders(folders);
            return folders;
        } else if (folders != null) {
            return new ArrayList<>();
        } else {
            return null;
        }
    }


    private boolean checkPath(String path) {
        return Files.exists(Path.of(path));
    }

    // print directories name in color on console blue
    private ArrayList<File> getAllFileAndDirectories(String path, boolean allFiles) {

        // Creating A file object for directory
        File directoryPath = new File(path);

        // filter every folder starts with `.`
        File[] folders = directoryPath.listFiles();

        if (!allFiles) {
            folders = directoryPath.listFiles(folder -> folder.isDirectory() && !folder.getName().startsWith("."));
        }

        if (folders != null)
            return new ArrayList<>(Arrays.asList(folders));
        else
            return new ArrayList<>();


    }

    private void printFolders(ArrayList<File> Folders) {
        for (File folder : Folders) {
            if (folder.isDirectory()) {
                System.out.print(STR."\{ANSI_BLUE + folder.getName() + ANSI_RESET}/     ");
            } else {
                System.out.print(STR."\{folder.getName()}/     ");

            }
        }
        System.out.println();
    }

    // use when you want to add new path to current path like => ./path
    private String addNewPath(String directory_name) {
        boolean isDirectoryChanged = false;


        String currentPath = Shell.currentPath;

        File newPath = new File(currentPath,"user.dir").getAbsoluteFile();
        return newPath.getAbsolutePath();


        // the new path to the current path
//        String newPath = System.setProperty("user.dir", newDirectory.getAbsolutePath()) + unix  + directory_name;

//        return newPath;
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
