package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Shell {

    static String currentPath;
    private static String osNaame = System.getProperty("os.name");

    private final String USERNAME = System.getProperty("user.name");


    private final String HOSTNAME
            = InetAddress.getLocalHost().getHostName();

    private final String USERUSER_HOSTNAME = USERNAME + '@' + HOSTNAME;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    static {
        currentPath = String.valueOf(new File(System.getProperty("user.dir")));
    }

    private final InputHandler inputHandler = new InputHandler();

    public Shell() throws UnknownHostException {
    }


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
        System.out.println(STR."\{ANSI_GREEN + USERUSER_HOSTNAME + ANSI_RESET} \{ANSI_PURPLE + currentPath + ANSI_RESET}");

        // print $ sin to indicate for user input here
        System.out.print("$ ");
    }

    public static boolean isWindows() {
        return (osNaame.startsWith("Windows"));
    }

    public static boolean isLinux() {
        return (osNaame.startsWith("Linux"));
    }

    public void handleInput(String command) throws IOException {
        inputHandler.handleInput(command);
    }

    public static void setPath(String newPath) {
        currentPath = newPath;
    }

}
