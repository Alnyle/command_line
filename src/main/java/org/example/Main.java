package org.example;


// to create unit class press (CTRL + Shift + T)


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        printCurrentPath();

        // take input from console as byte data and convert in characters
        InputStreamReader isr = new InputStreamReader (System.in);

        // because String class is immutable because of that I used BufferReader instead because it's mutable
        // String class: mutable
        // BufferRead: mutable

        BufferedReader br = new BufferedReader(isr);
        String s = null;

        // class to handle Input
        InputHandler handler = new InputHandler();




        try {
            while ((s = br.readLine()) != null) {
                handler.handleInput(s);
                printCurrentPath();
            }
        } catch ( IOException ioe) {
            throw new IOException("Enter a valid input");
        }

    }


    public static void printCurrentPath() {
        // get the path of the current directory
        String path = System.getProperty("user.dir");

        // print path of the current directory
        System.out.println(path);

        // print $ sin to indicate for user input here
        System.out.print("$ ");
    }
}