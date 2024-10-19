package org.example;

public class InputHandler {

    public void handleInput(String Input) {

        String[] commands = Input.split(" ");

        // responsible for change changing directory
        ChangeDirectory changeDirectory = new ChangeDirectory();

        if (commands[0].equals("cd")) {
            changeDirectory.cd(commands[1]);
        }

//        System.out.print("result = ");
//        for (String str : commands) {
//            System.out.print(str + ", ");
//        }

    }
}
