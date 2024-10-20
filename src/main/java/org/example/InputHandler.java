package org.example;

public class InputHandler {

    public void handleInput(String Input) {

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
                listDirectoriesFiles.ls(commands[1]);
            } else if (commands.length == 3) {
                if (commands[1].equals("-a")) {
                    // check on char
                    listDirectoriesFiles.lsHandler(commands[2], commands[1].charAt(0));
                }
            }


        }

//        System.out.print("result = ");
//        for (String str : commands) {
//            System.out.print(str + ", ");
//        }

    }
}
