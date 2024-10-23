package org.example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChangeDirectory {



    public void cd(String directory) {

        // get the path of the current directory
        String path = Shell.currentPath;

        // list of directories to current path
        String[] directories = path.split("\\\\");


        // getRoot() => change here


        String newCurrentPath = null;

        // Use StringBuilder store path of new path
        StringBuilder newPath = new StringBuilder();


        if (directory.equals("..")) {



            if (directories.length > 0) {
                // Use StringBuilder to reassemble the path minus the last directory


                //remove last directory to change
                for (int i = 0; i < directories.length - 1; i++) {
                    newPath.append(directories[i]).append("\\");
                }
                // Remove the trailing separator
                if (!newPath.isEmpty()) {
                    newCurrentPath = newPath.toString();
                    checkAndChangeDirectory(newCurrentPath);

                } else {
                    // if path not valid
                    System.out.println("Path Invalid");
                }

            }

            System.out.println(" ");
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

                // System.out.println(backwords);

                // subtract from old path directories based on number of `..`
                for (int p = 0; p < directories.length - backwords; p++) {
                    newPath.append(directories[p]).append("\\");
                }

                // then add the new path
                for (int p = i; p < newDirectory.length; p++) {
                    newPath.append(newDirectory[p]).append("\\");
                }


                if (!newPath.isEmpty()) {
                    newCurrentPath = newPath.toString();
                    checkAndChangeDirectory(newCurrentPath);
                    // System.out.println("new path: " + newCurrentPath + " " + backwords);
                }

            }

        } else if (directory.startsWith("./")) {

            String[] newDirectory = directory.split("/");


            // start new path after './'
            for (int p = 1; p < newDirectory.length; p++) {
                newPath.append(newDirectory[p]).append("\\");
            }

            if (!newPath.isEmpty()) {
                newCurrentPath = newPath.toString();
                checkAndChangeDirectory(newCurrentPath);
                // System.out.println("new path: " + newCurrentPath + " " + backwords);
            }

        } else if (!directory.startsWith("./")) {



            String[] newDirectory = directory.split("/");

            if (newDirectory.length == 1) {


                newPath.append(Shell.currentPath).append("\\");
                newPath.append(newDirectory[0]).append("\\");



                if (!newPath.isEmpty()) {
                    newCurrentPath = newPath.toString();
                    checkAndChangeDirectory(newCurrentPath);
                    // System.out.println("new path: " + newCurrentPath + " " + backwords);
                }
            } else {
                System.out.println("Invalid Path");
            }

        }




    }

    private boolean changeDirectory(String directory_name) {

        boolean isDirectoryChanged = false;

        File newDirectory = new File(directory_name).getAbsoluteFile();

        String currentPath = newDirectory.getAbsolutePath();

        System.out.println(Shell.currentPath);

        // change directory and check if directory changed
        System.setProperty(Shell.currentPath, newDirectory.getAbsolutePath());
        Shell.setPath(String.valueOf(newDirectory));
//        return isDirectoryChanged;
        return true;
    }

    private void checkAndChangeDirectory(String newCurrentPath) {


//        ProcessBuilder processBuilder = new ProcessBuilder();

        boolean exists = Files.exists(Path.of(newCurrentPath));
        System.out.println(STR."New path note here: \{newCurrentPath}");
        if (exists) {
             System.out.println(STR."New path: \{newCurrentPath}");
            boolean isChanged = changeDirectory(newCurrentPath);

            if (!isChanged) {
                System.out.println("Path Invalid");
            }

        } else {
            System.out.println("Path Invalid");
        }
    }

}
