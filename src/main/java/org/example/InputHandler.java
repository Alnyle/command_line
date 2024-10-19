package org.example;

public class InputHandler {

    public void handleInput(String Input) {

        String[] commands = Input.split(" ");
        System.out.print("result = ");
        for (String str : commands) {
            System.out.print(str + ", ");
        }

    }
}
