package org.example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChangeDirectory {


    public void cd(String directory) {



        if (directory.equals("..")) {

            // get the path of the current directory
            String path = System.getProperty("user.dir");

            // path of the current directory
            String[] directories = path.split("\\\\");

            String newCurrentPath = null;
            if (directories != null && directories.length > 0) {
                // Use StringBuilder to reassemble the path minus the last directory
                StringBuilder newPath = new StringBuilder();

                //remove last directory to change
                for (int i = 0; i < directories.length - 1; i++) {
                    newPath.append(directories[i]).append("\\");
                }

                // Remove the trailing separator
                if (!newPath.isEmpty()) {
                    newCurrentPath = newPath.toString();
                }

                // but check if that path exists before change directory
                boolean exists = Files.exists(Path.of(newCurrentPath));

                if (exists) {
                    System.out.println(STR."New path: \{newCurrentPath}");

//                    System.setProperties("user.dir", newCurrentPath);
                }
            }


            // check if there is no path before it


////            System.out.print("result = ");
//            for (String str : directories) {
//                System.out.println(str + ", ");
//            }

            System.out.println(" ");

//            String newCurrentPath = "";
//
//            // loop on all directories expect last one
//            for (int i = 0; i < directories.length() - 1; i++) {
//
//            }
        }

    }

    private void changeDirectory(String directory_name) {


    }

}
