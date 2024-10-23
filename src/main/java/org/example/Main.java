package org.example;


// to create unit class press (CTRL + Shift + T)


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Shell shell = new Shell();

        shell.start();
    }


//    public static void printCurrentPath() {
//        // get the path of the current directory
//        String path = System.getProperty("user.dir");
//
//        // print path of the current directory
//        System.out.println(path);
//
//        // print $ sin to indicate for user input here
//        System.out.print("$ ");
//    }
}