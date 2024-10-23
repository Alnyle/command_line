package org.example;

import java.io.IOException;

public class InputHandler {

    public void handleInput(String Input) throws IOException {

        String[] commands = Input.split(" ");

        // responsible for change changing directory
        ChangeDirectory changeDirectory = new ChangeDirectory();
        LSDF listDirectoriesFiles = new LSDF();

        if (commands[0].equals("cd")) {
            changeDirectory.cd(commands[1]);
        } else if (commands[0].equals("ls")) {

            if (commands.length == 1) {
                listDirectoriesFiles.ls("");
                return;
            } else if (commands.length == 2) {

                if (commands[1].equals("-a")) {
                    listDirectoriesFiles.ls("", 'a');
                } else if (commands[1].equals("-r")) {
                    listDirectoriesFiles.ls("", 'r');
                } else {
                    listDirectoriesFiles.ls(commands[1]);
                }

            } else if (commands.length == 3) {


                if ((commands[1].equals("-a") && commands[2].equals("-r")) || (commands[1].equals("-r") && commands[2].equals("-a"))) {
                    listDirectoriesFiles.ls("", 'a', 'r');
                }
                // check on char
                else if (commands[1].equals("-a")) {
                    listDirectoriesFiles.ls(commands[2], 'a');
                } else if (commands[1].equals("-r")) {
                    listDirectoriesFiles.ls(commands[2], 'r');
                }
            } else if (commands.length == 4) {
                listDirectoriesFiles.ls(commands[3], 'r', 'a');
            }


        }

//        System.out.print("result = ");
//        for (String str : commands) {
//            System.out.print(str + ", ");
//        }

    }
}
