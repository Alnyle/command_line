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

                while (true) {
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

                // Remove the trailing separator
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

            // Remove the trailing separator
            if (!newPath.isEmpty()) {
                newCurrentPath = newPath.toString();
                checkAndChangeDirectory(newCurrentPath);
                // System.out.println("new path: " + newCurrentPath + " " + backwords);
            }

        } else if (!directory.startsWith("./")) {

            String[] newDirectory = directory.split("/");

            if (newDirectory.length == 1) {
                newPath.append(newDirectory[0]).append("\\");
            }

            if (!newPath.isEmpty()) {
                newCurrentPath = newPath.toString();
                checkAndChangeDirectory(newCurrentPath);
                // System.out.println("new path: " + newCurrentPath + " " + backwords);
            }

        }




    }

    private boolean changeDirectory(String directory_name) {

        boolean isDirectoryChanged = false;

        File newDirectory = new File(directory_name).getAbsoluteFile();


        // change directory and check if directory changed
        isDirectoryChanged = (System.setProperty("user.dir", newDirectory.getAbsolutePath()) != null);
        return isDirectoryChanged;
    }

    private void checkAndChangeDirectory(String newCurrentPath) {

        boolean exists = Files.exists(Path.of(newCurrentPath));
        if (exists) {
            // System.out.println(STR."New path: \{newCurrentPath}");
            boolean isChanged = changeDirectory(newCurrentPath);

            if (!isChanged) {
                System.out.println("Path Invalid");
            }

        } else {
            System.out.println("Path Invalid");
        }
    }

}
