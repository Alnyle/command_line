package org.example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChangeDirectory {



    public void cd(String directory) {

        // get the path of the current directory
        String path = Shell.currentPath;

        // list of directories to current path
        String[] directories = Shell.isWindows() ? path.split("\\\\") : path.split("/");

        String splitOperator = Shell.isWindows() ? "\\" :  "/" ;
        // getRoot() => change here


        String newCurrentPath = null;

        // Use StringBuilder store path of new path
        StringBuilder newPath = new StringBuilder();


        if (directory.equals("..")) {



            if (directories.length > 0) {
                // Use StringBuilder to reassemble the path minus the last directory


                //remove last directory to change
                for (int i = 0; i < directories.length - 1; i++) {
                    newPath.append(directories[i]).append(splitOperator);
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


                // subtract from old path directories based on number of `..`
                for (int p = 0; p < directories.length - backwords; p++) {
                    newPath.append(directories[p]).append(splitOperator);
                }

                // then add the new path
                for (int p = i; p < newDirectory.length; p++) {
                    newPath.append(newDirectory[p]).append(splitOperator);
                }


                if (!newPath.isEmpty()) {
                    newCurrentPath = newPath.toString();
                    checkAndChangeDirectory(newCurrentPath);
                }

            }

        } else if (directory.startsWith("./")) {

            String[] newDirectory = directory.split("/");


            // start new path after './'
            for (int p = 1; p < newDirectory.length; p++) {
                newPath.append(newDirectory[p]).append(splitOperator);
            }

            if (!newPath.isEmpty()) {
                newCurrentPath = newPath.toString();
                checkAndChangeDirectory(newCurrentPath);
            }

        } else if (!directory.startsWith("./")) {



            String[] newDirectory = directory.split("/");

            if (newDirectory.length == 1 && !directory.contains(splitOperator)) {

                newPath.append(Shell.currentPath).append(splitOperator);
                newPath.append(newDirectory[0]).append(splitOperator);

                if (!newPath.isEmpty()) {
                    newCurrentPath = newPath.toString();
                    checkAndChangeDirectory(newCurrentPath);
                }
            }
            else if (checkPath(directory)) {

                File newDirectorys = new File(directory).getAbsoluteFile();

                String currentPath = newDirectorys.getAbsolutePath();

                Shell.setPath(String.valueOf(newDirectorys));
                System.out.println(Shell.currentPath);
                System.setProperty(Shell.currentPath, newDirectorys.getAbsolutePath());


            }
            else {
                System.out.println("Invalid Path");
            }

        }




    }

    private boolean changeDirectory(String directory_name) {

        boolean isDirectoryChanged = false;

        File newDirectory = new File(directory_name).getAbsoluteFile();

        String currentPath = newDirectory.getAbsolutePath();



        // change directory and check if directory changed
        System.setProperty(Shell.currentPath, newDirectory.getAbsolutePath());
        Shell.setPath(String.valueOf(newDirectory));
        System.out.println(Shell.currentPath);
        return true;
    }

    private boolean checkPath(String path) {
        return Files.exists(Path.of(path));
    }

    private void checkAndChangeDirectory(String newCurrentPath) {



        boolean exists = Files.exists(Path.of(newCurrentPath));
        if (exists) {
            boolean isChanged = changeDirectory(newCurrentPath);

            if (!isChanged) {
                System.out.println("Path Invalid");
            }

        } else {
            System.out.println("Path Invalid");
        }
    }

}
