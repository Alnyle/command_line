package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {

    static String currentPath;

    static {
        currentPath = String.valueOf(new File(System.getProperty("user.dir")));
    }

    private final InputHandler inputHandler = new InputHandler();




    // start running shell
    public void start() throws IOException {


        printCurrentPath();

        // take input from console as byte data and convert in characters
        InputStreamReader isr = new InputStreamReader (System.in);

        // because String class is immutable because of that I used BufferReader instead because it's mutable
        // String class: mutable
        // BufferRead: mutable

        BufferedReader br = new BufferedReader(isr);
        String s;



        try {
            while ((s = br.readLine()) != null) {
                handleInput(s);
                printCurrentPath();
            }
        } catch ( IOException ioe) {
            throw new IOException("Enter a valid input");
        }
    }

    public void printCurrentPath() {

        // print path of the current directory
        System.out.println(currentPath);

        // print $ sin to indicate for user input here
        System.out.print("$ ");
    }


    public void handleInput(String command) throws IOException {
        inputHandler.handleInput(command);
    }

    public static void setPath(String newPath) {
        currentPath = newPath;
    }

}
