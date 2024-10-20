package org.example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChangeDirectory {


    public void cd(String directory) {

        // get the path of the current directory
        String path = System.getProperty("user.dir");



        // path of the current directory

        // getRoot() => change here
        String[] directories = path.split("\\\\");


        String newCurrentPath = null;


        if (directory.equals("..")) {



            if (directories.length > 0) {
                // Use StringBuilder to reassemble the path minus the last directory
                StringBuilder newPath = new StringBuilder();

                //remove last directory to change
                for (int i = 0; i < directories.length - 1; i++) {
                    newPath.append(directories[i]).append("\\");
                }
                // Remove the trailing separator
                if (!newPath.isEmpty()) {
                    newCurrentPath = newPath.toString();


                    boolean exists = Files.exists(Path.of(newCurrentPath));
                    if (exists) {
                        System.out.println(STR."New path: \{newCurrentPath}");
                        boolean isChanged = changeDirectory(newCurrentPath);

                        if (!isChanged) {
                            System.out.println("Path Invalid");
                        }

                    }
                } else {
                    // if path not valid
                    System.out.println("Path Invalid");
                }

            }

            System.out.println(" ");
        } else if (directory.startsWith("../..")) {

            // if there is more than `..` in your path
//            String[] directories = path.split("\\\\");


            String[] newDirectory = directory.split("/");

            if (newDirectory.length > 0) {

                // count number of times have to back
                int backwords = 0;

                int i = 0;

                while (true) {
                    if (newDirectory[i].equals("..")) {
                        backwords++;
                        i++;
                    } else {
                        break;
                    }
                }

                System.out.println(backwords);
                // Use StringBuilder store path of new path
                StringBuilder newPath = new StringBuilder();


                for (int p = 0; p < directories.length - backwords; p++) {
                    newPath.append(directories[p]).append("\\");
                }

                for (int p = i; p < newDirectory.length; p++) {
                    newPath.append(newDirectory[p]).append("\\");
                }

                // Remove the trailing separator
                if (!newPath.isEmpty()) {
                    newCurrentPath = newPath.toString();
//                    for (int p = 0; p < directories.length - backwords; p++) {
//                        newCurrentPath.append(directories[i]).append("\\");
//                    }

                    boolean exists = Files.exists(Path.of(newCurrentPath));
                    if (exists) {
                        System.out.println(STR."New path: \{newCurrentPath}");
                        boolean isChanged = changeDirectory(newCurrentPath);

                        if (!isChanged) {
                            System.out.println("Path Invalid");
                        }

                    } else {
                        System.out.println("Something wrong");
                    }

                    System.out.println("new path: " + newCurrentPath + " " + backwords);
                }

            }

        }

    }

    private boolean changeDirectory(String directory_name) {

        boolean isDirectoryChanged = false;

        File newDirectory = new File(directory_name).getAbsoluteFile();


        // change directory and check if changed directory
        isDirectoryChanged = (System.setProperty("user.dir", newDirectory.getAbsolutePath()) != null);
        return isDirectoryChanged;
    }

}
