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
            }

            listDirectoriesFiles.ls(commands[1]);
        }

//        System.out.print("result = ");
//        for (String str : commands) {
//            System.out.print(str + ", ");
//        }

    }
}
